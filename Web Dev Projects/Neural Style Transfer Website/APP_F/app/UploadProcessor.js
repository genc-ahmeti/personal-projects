// dependencies
const UserManager = require('./UserManager');

const crypto = require('crypto');
const fs = require('fs');
const path = require('path');

// Path Converter
function _toFrontendPathConverter(path){
    return path.replace("./public", ".");
}

// singleton handling file uploads in parallel
const UploadProcessor = {

    // maximum allowed duration of a single upload (in ms)
    _MAX_DURATION: 1000 * 60,

    // the chunk size to use for uploads (in bytes)
    _CHUNK_SIZE: 100000,

    // current uploads indexed by their ID, each entry is of
    // the form {targetFolder, fileName, fileSize, chunkSize, chunkNr, data, timeout}
    _uploads: {},

    // start a new upload accepts
    start: function(targetFilePath, username, fileSize) {
        return new Promise((resolve, reject) => {

            // generate a new, unique ID
            let ID;
            do {
                ID = crypto.randomBytes(16).toString('base64');
            } while (this._uploads[ID]);

            // create new upload object
            this._uploads[ID] = {
                username: username,
                fileSize: fileSize,
                targetFilePath: targetFilePath,
                chunkSize: this._CHUNK_SIZE,
                chunkNr: 0,
                data: [],
                timeout: setTimeout(() => this._remove(ID), this._MAX_DURATION)
            }

            resolve({accepted: true, ID: ID, chunkSize: this._CHUNK_SIZE});

        })

    },

    // receive a chunk of a file
    receiveChunk: function(ID, chunkNr, chunk) {

        return new Promise((resolve, reject) => {

            // check if the upload exists
            const upload = this._uploads[ID];
            if (!upload) {
                resolve({accepted: false, reason: `Upload "${ID}" does not exist.`});
                return;
            }

            // check that no chunks were dropped
            if (upload.chunkNr++ !== chunkNr) {
                this._remove(ID);
                resolve({accepted: false, reason: `Upload out of sync. Aborted.`});
                return;
            }

            // convert into node-friendly Buffer format
            chunk = new Buffer(new Uint8Array(chunk));

            // add data
            upload.data.push(chunk);

            // check if the upload is complete
            if (upload.chunkNr * upload.chunkSize >= upload.fileSize) {
                // upload complete, try to save file and delete the upload
                this._save(upload)
                    .then(() => {
                        resolve({
                            accepted: true,
                            completed: true
                        });
                        this._remove(ID);
                    })
                    .catch(reason => {
                        // do not tell the client about the exact reason
                        // but rather log it
                        resolve({
                            accepted: false,
                            reason: `The uploaded file could not be saved.`
                        });
                        console.log(`Could not save an uploaded file: ${reason}`);
                        this._remove(ID);
                    });
            } else {
                // there are chunks missing
                resolve({accepted: true, completed: false});
            }

        });

    },

    // save an upload
    _save: function(upload) {
        return new Promise((resolve, reject) => {

            // combine all chunks and try to store the file
            const fileBuffer = Buffer.concat(upload.data);
            fs.writeFile(
                path.resolve(upload.targetFilePath),
                fileBuffer,
                (error) => {
                    if (error) {
                        reject(error.message);
                    } else {
                        resolve();
                    }
                });
            UserManager._saveRawImage(upload.username, _toFrontendPathConverter(upload.targetFilePath));
        });
    },

    // remove an upload
    _remove: function(ID) {

        if (this._uploads[ID]) {
            clearTimeout(this._uploads[ID].timeout);
            delete this._uploads[ID];
        }
    }

}

module.exports = UploadProcessor;

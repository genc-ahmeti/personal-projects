/**
 * UTILITIES
 */
const path = require('path');
const fs = require('fs');

/**
 * WEB SERVER
 *
 * npm install express --save
 */
const express = require('express');
const app = express();
const http_server = require('http').createServer(app);

// serve all the files from the public folder
app.use(express.static(path.resolve(__dirname, 'public')));

// start the server
http_server.listen(1337, () => {
    console.log('HTTP server listening on port 1337');
});

/**
 * FILE UPLOADER
 */
const UploadProcessor = require('./app/UploadProcessor');
/**
 * USER MANAGEMENT
 */
const UserManager = require('./app/UserManager');


/**
 * WEBSOCKET COMMUNICATION
 *
 * npm install socket.io --save
 */
const io = require('socket.io')(http_server);// listen to websocket requests
io.on('connection', socket => {

    // tell the socket.io package what to do when a socket connects
    console.log('someone connected');

    socket.on('request', (IN, callback) => {

        // make sure that there is a callback function
        if (typeof callback !== 'function') {
            callback = () => {};
        }


        // forward any request to the user manager
        UserManager.processRequest(IN.type, IN.data, IN.username, IN.sessionID)
            .then(result => {
                callback({
                    success: true,
                    data: result
                });
            })
            .catch(error => {
                callback({
                    success: false,
                    messagUsere: error
                });
            });
    });

    // IN:  {fileName: string, fileSize: number}
    // OUT: {accepted: true, ID: string, chunkSize: number} | {accepted: false, reason: string}
    socket.on('requestFileUpload', (IN, callback) => {
        // start new upload
        console.log(path.resolve(IN.filepath));
        UploadProcessor.start(IN.filepath, IN.username, IN.fileSize)
            .then(callback)
            .catch(() => {});

    });

    // IN:  {ID: string, chunkNr: number, chunk: ArrayBuffer}
    // OUT: {accepted: true, completed: bool} | {accepted: false, reason: string}
    socket.on('sendChunk', (IN, callback) => {

        // receive a chunk
        UploadProcessor.receiveChunk(IN.ID, IN.chunkNr, IN.chunk)
            .then(callback)
            .catch(() => {})

    });
});

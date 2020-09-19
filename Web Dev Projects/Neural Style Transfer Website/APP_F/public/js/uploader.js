// class managing file uploads
const Uploader = function() {

    // the websocket connection to use for upload
    this._socket = io();

    // the fileReader object to use for uploads
    this._fileReader = new FileReader();
    this._fileReader.onload = this._sendChunk.bind(this);

    // the file input DOM element
    this._input = undefined;

    // the file that is currently being uploaded
    this._file = undefined;

    // the size of the file that is currently being uploaded
    this._fileSize = undefined;

    // the ID of the current upload
    this._ID = undefined;

    // the chunk size to use for the current upload
    this._chunkSize = undefined;

    // the chunk counter for the current upload
    this._chunkNr = undefined;

    // progress callback (is passed a number between 0 and 1 indicating
    // the progress of the current upload, 1 means completed), is called
    // every time the value changes
    this.onProgress = () => {};

    // failure callback (is passed an error message, if this function is
    // called, the upload was aborted)
    this.onFail = () => {};

}

// listen to the change event of the uploader
Uploader.prototype.listen = function(fileInput) {
    this._input = fileInput;
}

// upload the selected file
Uploader.prototype.upload = function() {

    let F;
    if (this._input && (F = this._input.files) && F.length) {

        // a file has been selected
        this._file = F[0];
        console.log(`File: ${F[0].name} has been selected`)

        // request a new file upload
        this._requestFileUpload(this._file.name, this._file.size)
            .then(response => {
                if (response.accepted) {
                    this._start(response.ID, response.chunkSize);
                } else {
                    this.onFail(`Upload rejected: ${response.reason}`);
                }
            })
            .catch(reason => {
                this.onFail(`Could not request a file upload: ${reason}`)
            });

    } else {

        // no file is available
        this.onFail(`No file has been selected.`);

    }

}

// request a new file upload
Uploader.prototype._requestFileUpload = function(fileName, fileSize) {
    let username = Connection._username;
    let filepath = User._generateFilePath(username, 'rawImgs');
    return new Promise((resolve, reject) => {
        this._socket.emit(
            'requestFileUpload',
            {
                filepath: filepath,
                username: username,
                fileSize: fileSize,
            },
            resolve
        );
    });

}

// start the upload process
Uploader.prototype._start = function(ID, chunkSize) {
    this._fileSize = this._file.size;
    this._chunkNr = 0;
    this._ID = ID;
    this._chunkSize = chunkSize;
    this.onProgress(0);
    this._readChunk();
}

// read the next chunk into the file reader
Uploader.prototype._readChunk = function() {

    // compute start of the current chunk
    const offset = this._chunkNr * this._chunkSize;
    if (offset > this._fileSize) return;

    // read the next chunk (but not past the end of the file)
    // the fileReader's onload callback calls _sendChunk
    this._fileReader.readAsArrayBuffer(this._file.slice(
        offset, offset + Math.min(this._chunkSize, this._fileSize - offset)
    ));

}

// send the current file chunk (triggered by _readChunk)
Uploader.prototype._sendChunk = function() {

    this._socket.emit(
        'sendChunk',
        {
            ID: this._ID,
            chunkNr: this._chunkNr,
            chunk: this._fileReader.result
        },
        response => {
            if (response.accepted) {
                ++this._chunkNr;
                if (response.completed) {
                    this.onProgress(1);
                } else {
                    this.onProgress(Math.min(1, this._chunkNr * this._chunkSize / this._fileSize));
                    this._readChunk();
                }
            } else {
                this.onFail(response.reason);
            }
        }
    )

}
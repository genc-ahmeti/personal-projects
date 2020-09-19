/**
 * user management
 */

// ===== DEPENDENCIES =====

const IDatabaseCommunication = require('./IDatabaseCommunication');

const NST = require('./INeuralStyleTransfer');

const User = require('./User');

const crypto = require("crypto");


// ===== FRONTEND BACKEND TRANSLATOR =====
/** Converts filepath into relation to public
 * @param path (in relation to root)
 * @returns path (in relation to public)
 */
function _toFrontendPathConverter(path){
    return path.replace("./public", ".");
}

/** Converts filepath into relation to root
 * @param path (in relation to public)
 * @returns path (in relation to root)
 */
function _fromFrontendPathConverter(path){
    path = path.substr(1, path.length);
    return ("./public" + path);
}


// ===== HASHING & RANDOMS =====

const generateSessionId = function() {
    return crypto.randomBytes(16).toString("base64");
};







// ===== USER MANAGEMENT =====

// the user manager object (singleton)
const UserManager = {
    // ===== DATA MEMBERS =====

    // dictionary of currently active users indexed by username
    _users: {},

    // ===== PUBLIC METHODS =====

    /** entry point for request processing
     * @param type
     * @param data
     * @param username
     * @param sessionID
     * @returns promise (resolves with various response data)
     */

    processRequest: function(type, data, username, sessionID) {
        switch (type) {
            case "login":
                return this._processLoginRequest(data.username, data.password);
            case "logout":
                return this._logoutRequest(username, sessionID);
            case "style_transfer":
                return this._styleTransferRequest(username, data, sessionID);
            case "get_styles":
                return NST._getStylesRequest();
            case "update_user":
                return this._updateUserRequest(username, sessionID);
            default:
                return Promise.reject("invalid request type");
        }
    },

    // ===== INTERNAL HELPER METHODS =====

    /** check whether session credentials are valid
     * @param username
     * @param sessionID
     * @returns boolean
     */

    _authenticate: function(username, sessionID) {
        console.log("authentication started");
        return (
            this._users[username]._username == username &&
            this._users[username]._sessionID === sessionID
        );
    },
    // hash user password
    _hashPw : function(pw){
    const hashedPw = crypto.createHmac('sha256', pw)
        .digest('hex');
    return hashedPw;
        
    },


    // =====   PRIVATE METHODS   =====

    // ====== Frontend Interface =====
    // --- Methods for processing requests ---

    /** process a login request
     * @param username
     * @param password
     * @returns promise (resolves with {success, username, sessionID, rawImgs, stylizedImgs, styles})
     */

    _processLoginRequest: function(username, password) {
        const hashPw = this._hashPw(password);
        return new Promise((resolve, reject) => {
            this._loadUser(username, hashPw)
                .then(db_response => {
                    // access success ,username via db_response['success']/db_response['username'];
                    if (db_response['success']) {
                        Promise.all(
                            [this._getRawImages(username),
                            this._getStylizedImages(username),
                                NST._getStylesRequest()]
                        ).then((values) => {
                            const UserInfo = {
                                success: true,
                                username: username,
                                sessionID: generateSessionId(),
                                rawImgs: values[0],
                                stylizedImgs: values[1],
                                styles: values[2]
                            };

                            // hier habe ich jetzt mal das mit dem User gemacht
                            const U = new User();
                            U._username = UserInfo.username;
                            U._sessionID = UserInfo.sessionID;
                            this._users[username] = U;
                            resolve(UserInfo);
                        });
                    } else {
                        resolve(db_response);
                    }
                })
                .catch(error => reject(error));
        });
    },

    /** process a logout request
     * @param username
     * @param sessionID
     * @returns promise (resolves with {success, (error message})
     */

    _logoutRequest: function(username, sessionID) {
        if (this._authenticate(username, sessionID)) {
            delete this._users[username];
            console.log("Logged out.")
            return Promise.resolve({ success: true });
        } else {
            return Promise.reject({
                success: false,
                message: "logout rejected: invalid session credentials"
            });
        }
    },

    /** process a style transfer request
     * @param username
     * @param data
     * @param sessionID
     * @returns promise (resolves with {stylizedImg[]}
     */

    _styleTransferRequest(username, data, sessionID) {
        return new Promise((resolve, reject) => {
            console.log("Start Style Transfer Request");
            if (this._authenticate(username, sessionID)) {
                this._saveRawImage(username, data.rawImg)
                    .then(response => console.log("rawImagePath saved!"))
                    .catch(err => console.log(err));
                console.log("Start Style Transfer!");
                this._styleTransfer(username, data)
                    .then(styleTransfer_response => {
                        console.log("Style Transfer Done.");
                        this._saveStylizedImage(
                            username,
                            data.rawImg,
                            data.style,
                            styleTransfer_response.stylizedImg
                        );
                        console.log("Stylized Image saved in database.");
                        const stylizedImgTriplet = {
                            rawImg: data.rawImg,
                            style: data.style,
                            stylizedImg: styleTransfer_response.stylizedImg
                        };
                        resolve(stylizedImgTriplet);
                    })
                    .catch(error => console.log("Style Transfer failed: ",error));
            } else {
                console.log("Authentication failed!");
                reject({
                    success: false,
                    message: "Style Transfer failed: invalid session credentials"
                });
            }
        });
    },

    /** process a update-User-request
     * @param username
     * @param sessionID
     * @returns promise (resolves with {rawImgs[], stylizedImgs[][],styles}
     */

    _updateUserRequest(username, sessionID) {
        return new Promise((resolve, reject) => {
            if (this._authenticate(username, sessionID)) {
                Promise.all(
                    [this._getRawImages(username),
                    this._getStylizedImages(username),
                        NST._getStylesRequest()]
                )
                    .then(values => {
                        const updatedImgs = {
                            rawImgs: values[0],
                            stylizedImgs: values[1],
                            styles: values[2]
                        };
                        console.log("Updated User.");
                        resolve(updatedImgs);
                    })
                    .catch(err => reject(err));
            } else {
                reject("Authentication failed!");
            }
        });
    },

    /**
     * NeuralStyleTransfer  Functions
     */

    _styleTransfer: function(username, data) {
        return new Promise((resolve, reject) => {
            NST._styleTransfer(username, data).then(result => resolve(result)).catch(error => reject(error));
        });
    },

    /**
     * Database Functions
     */

    _loadUser: function(username, password) {
        return new Promise((resolve, reject) => {
            IDatabaseCommunication.loadUser(username,password).then(result => resolve(result)).catch(error => reject(error));
        });
    },
    _getRawImages: function(username) {
        return new Promise((resolve, reject) => {
            IDatabaseCommunication.getRawImages(username).then(result => resolve(result)).catch(error => reject(error));
        });

    },
    _getStylizedImages: function(username) {
        return new Promise((resolve, reject) => {
            IDatabaseCommunication.getStylizedImages(username).then(result => resolve(result)).catch(error => reject(error));
        });    },
    _saveRawImage: function(username, rawPath) {
        return new Promise((resolve, reject) => {
            /*console.f(rawPath);*/
            IDatabaseCommunication.saveRawImage(username,rawPath).then(result => resolve(result)).catch(error => reject(error));
        });},
    _saveStylizedImage: function(username, rawPath,stylePath,stylizedPath) {
        return new Promise((resolve, reject) => {
            IDatabaseCommunication.saveStylizedImage(username,rawPath,stylePath,stylizedPath).then(result => resolve(result)).catch(error => reject(error));
        });}

};

// ===== UNIT TESTING =====

// Basic example for unit test
/* @TODO write tests for some methods of backend.


const {describe, it} = require('mocha');

const {expect} = require('chai');

const {sinon} = require('sinon');

describe('Testing if generaterandomFileName function returns a string with length of 16', function(){
	it('Function returns a string', function(){
		expect(generateRandomFileName()).to.be.a('string');
	});
	it('The returned string has a length of 16', function(){
		expect(generateRandomFileName()).to.have.lengthOf(16);
	});
});
*/


// ===== EXPORT =====
exports._toFrontendPathConverter = _toFrontendPathConverter;
exports._fromFrontendPathConverter = _fromFrontendPathConverter;
module.exports = UserManager;

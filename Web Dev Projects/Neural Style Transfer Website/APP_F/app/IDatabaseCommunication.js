/**
 * Database Interface
 *
 */

/**
 * Configuration
 */




// prerequisites for database connection
const mysql = require('mysql');
const Config = require('./Config');
const DB = mysql.createConnection(Config.DATABASE_CREDENTIALS);

// connect to the database
DB.connect(err => {
    if (err) throw err;
    console.log("connected to MySQL database");
});

const IDatabaseCommunication = {

    /**
     *
     * loads a user from the database by name and password
     *
     * @param username
     * @param password
     * @returns a promise (resolves with {success, username}, rejects for invalid credentials)
     */

    loadUser: function(username, password) {

    return new Promise((resolve, reject) => {
        DB.query("CALL loadUser(?,?);", [username, password], (err, result) => {
            if (err) {
                // database error
                reject(err);
            } else {
                var res = result[0][0]
                // Was the request successful?
                if (res['success'] === 0) {
                    reject('username/password combination does not exist');
                } else {
                    // access success ,username via res['success']/res['username'];
                    return resolve(res);
                }

            }
        });
    });
},

/**
 *
 * receives all raw images a user has uploaded to the database by its name
 *
 * @param username
 * @returns a promise (resolves with list of paths, rejects for database error)
 */

    getRawImages: function(username) {

        return new Promise((resolve, reject) => {
            DB.query("CALL getRawImages(?)", [username], (err, result) => {
                if (err) {
                    // database error
                    reject(err);
                } else {
                    res = result[0]
                    // access n-th path via res[n]['rawImagePath']
                    return resolve(res);
                }
            });
        });
    },

/**
 *
 * receives all stylized images a user has uploaded to the database by its name
 *
 * @param username
 * @returns a promise (resolves with list of requests, rejects for database error)
 */

    getStylizedImages: function(username) {

        return new Promise((resolve, reject) => {
            DB.query("CALL getStylizedImages(?)", [username], (err, result) => {
                if (err) {
                    // database error
                    reject(err);
                } else {
                    // access the path of raw , style, or stylized Image from n-th request
                    // via res[n]['rawImagePath']/res[n]['styleImagePath']/res[n]['stylizedImagePath']
                    var res = result[0];
                    return resolve(res);
                }
            });
        });
    },

/**
 *
 * uploads a raw image of a user to the database by username and the image path
 *
 * @param username
 * @param rawImage image path on the server
 * @returns a promise (resolves with success, rejects for upload failure)
 */

    saveRawImage: function(username, rawImage) {

        return new Promise((resolve, reject) => {
            DB.query("CALL saveRawImage(?,?);", [username, rawImage], (err, result) => {
                if (err) {
                    // database error
                    reject(err);
                } else {
                    var res = result[0][0];
                    // Was the image successfully stored?
                    if (res['success'] === 0) {
                        reject('raw image could not be saved (maybe already saved?)');
                    } else {
                        // access success via res['success'];
                        return resolve(res);
                    }

                }
            });
        });
    },

/**
 *
 * uploads a request of a user to the database by username and the image paths of the style transfer
 *
 * @param username
 * @param stylizedImage list of three image paths [raw,style,stylized] on the server
 * @returns a promise (resolves with {success: true, stylizedImage: imgpath}, rejects for upload failure)
 */

    saveStylizedImage: function(username, rawImage, styleImage, stylizedImage) {

        return new Promise((resolve, reject) => {
            DB.query("CALL saveStylizedImage(?,?,?,?);", [username, rawImage, styleImage, stylizedImage], (err, result) => {
                if (err) {
                    // database error
                    reject(err);
                } else {
                    var res = result[0][0];
                    // Was the image successfully stored?
                    if (res['success'] === 0) {
                        reject('stylized image could not be saved (path of stylized image already used)');
                    } else {
                        // access success via res['success'];
                        return resolve(res);
                    }

                }
            });
        });
    }

}

/* example usages

IDatabaseCommunication.loadUser("a","c").then(result => console.log(result)).catch(error => console.log(error));

IDatabaseCommunication.getRawImages("a").then(result => console.log(result)).catch(error => console.log("fehler"));

IDatabaseCommunication.getStylizedImages("a").then(result => console.log(result)).catch(error => console.log("fehler"));

IDatabaseCommunication.saveRawImage("a","d").then(result => console.log(result)).catch(error => console.log(error));

IDatabaseCommunication.saveStylizedImage("b","/a","/b","/c").then(result => console.log(result)).catch(error => console.log(error));


 */

// ===== IMPORT/EXPORT =====

// export {loadUser, getRawImages,getStylizedImages,saveRawImage,saveStylizedImage};

// import via import {loadUser, getRawImages,getStylizedImages,saveRawImage,saveStylizedImage} from path_name

// ===== EXPORT =====

module.exports = IDatabaseCommunication;

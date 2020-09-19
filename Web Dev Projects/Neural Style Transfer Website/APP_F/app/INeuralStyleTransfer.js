/**
 * Neural style transfer interface
 *
 */

// ===== DEPENDENCIES =====

const UserManager = require('./UserManager');

const util = require("util");

const exec = util.promisify(require("child_process").exec);


/** starts Style Transfer
 * @param username
 * @param {rawImg, style, stylizedPath}
 * @param sessionID
 * @returns (resolves with stylizedImg)
 * */

function _styleTransfer(username, data) {

    const rawImg = UserManager._fromFrontendPathConverter(data.rawImg);
    console.log(rawImg);
    const ou = _generateFilePath(username, "stylizedImgs");
    console.log(ou);
    const style = UserManager._fromFrontendPathConverter(_imgToWeights(data.style));
    console.log(style);

    return new Promise((resolve, reject) => {
        exec(
            `python ./fast_neural_style/neural_style/neural_style.py eval -im ${rawImg} -st ${style} -ou ${ou}`,
            (err, stdout) => {
                // result is available in stylizedImg
                if (err) {
                    reject("could not execute the style transfer script");
                } else {
                    resolve({success: stdout, stylizedImg: UserManager._toFrontendPathConverter(ou)});
                }
            }
        );
    });
}

/** converts Image path to weight path, gives path in relation to root (-> public folder)
 * @param ImgPath
 * @returns path to weights
 * */

function _imgToWeights(style) {
    //style.replace("./saved_models", "./public/saved_models"); #might not work
    return style.replace(".jpg", ".pth");
}

/** converts Image path to weight path, gives path in relation to root (-> public folder)
 * @param
 * @returns (resolves with style paths object)
 * */

function _getStylesRequest() {
    return new Promise((resolve, reject) => {
        const styles = {
            style1: "./saved_models/candy.jpg",
            style2: "./saved_models/mosaic.jpg",
            style3: "./saved_models/udnie.jpg",
            style4: "./saved_models/rain_princess.jpg",
            style5: "./saved_models/abstract_carving.jpg",
            style6: "./saved_models/classy.jpeg",
            style7: "./saved_models/edtaonisl.jpg",
            style8: "./saved_models/patterned_leaves.jpg",
            style9: "./saved_models/space.jpg",
            style10: "./saved_models/objects.jpg"};
        resolve(styles);
    });
}
// ====== File System Manager =====
/** generates filepath in relation to root (-> public folder)
 * @param username
 * @param type (rawImgs, stylizedImgs)
 * @returns filepath (to raw/stylized image)
 * */
function _generateFilePath(username, type) {
    const filePath = `./public/images/${username}/${type}/${generateRandomFileName()}.jpg`;
    return filePath;
}

// generates random file names, without using special character.
// Important for naming, ex "/" would be confusing as it initiates a new subfolder.
const generateRandomFileName = function() {
    var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
    var string_length = 16;
    var randomstring = '';
    for (var i=0; i<string_length; i++) {
        var rnum = chars[Math.floor(Math.random() * chars.length)];
        randomstring = randomstring + rnum;
    }
    return randomstring;
}

exports._styleTransfer = _styleTransfer;
exports._getStylesRequest = _getStylesRequest;



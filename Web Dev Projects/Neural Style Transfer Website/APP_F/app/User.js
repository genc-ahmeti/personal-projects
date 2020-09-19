/**
 * user class
 *
 * npm install crypto --save
 * npm install mysql --save
 */

// ===== CONSTRUCTOR / DATA MEMBERS =====


// default constructor

const User = function() {

    // the user's credentials
    this._username = undefined;
    this._sessionID = undefined;
};


// ===== PUBLIC METHODS =====

// get the sessionID
User.prototype.getSessionID = function() {
    return this._sessionID;
};

module.exports = User;

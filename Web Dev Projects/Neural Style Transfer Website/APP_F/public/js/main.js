/**
 * Connection
 * Communication class with the backend via sockets and the request method
 */
const Connection = {

    // ============================= DATA MEMBERS =============================

    // the actual websocket
    _socket: undefined,

    // credentials
    _username: undefined,
    _sessionID: undefined,

    // ============================= PUBLIC METHODS ============================

    /**
     * establish the websocket connection at startup
     */
    initialize: function() {
        this._socket = io();
    },

    /**
     * add/remove the session credentials that are added to all requests
     * @param username
     * @param sessionID
     */
    setSessionCredentials: function(username, sessionID) {
        this._username = username;
        this._sessionID = sessionID;
    },

    /**
     * send a request to the server
     * @param type is one of 'login', 'logout', 'updateUser', 'styleTransfer'
     * @param data
     */
    request: function(type, data) {
        console.log(`"${type}"-request sent to server: data =`, data);
        return new Promise((resolve, reject) => {
            this._socket.emit(
                'request',
                {
                    type: type,
                    data: data,
                    username: this._username,
                    sessionID: this._sessionID
                },
                response => {
                    if (response.success) {
                        console.log(`"${type}"-request succeeded: response =`, response.data);
                        resolve(response.data);
                    } else {
                        console.log(`"${type}"-request failed: error =`, response.message);
                        reject(response.message);
                    }
                }
            )
        })
    }
};

/**
 * User
 * Class with all the functional methods
*/
const User = {

    // ============================= DATA MEMBERS =============================

    // the name of the user
    _username: undefined,

    // the user images: his stylized Images[][], his raw Images[], his style Images[]
    _stylizedImages: undefined,
    _rawImages: undefined,
    _styleImages: undefined,

    // name and statistics containers
    //_nameContainer: undefined,

    // ============================= PUBLIC METHODS ============================
    /**
     * Try to login using stored credentials
     */
    resume: function() {
        let credentials = this._loadCredentials();
        if (credentials === undefined) {
            ScreenManager.displayMessage('No credentials are saved. Please log in.');
            ScreenManager.toLoginScreen()
        } else {
            this.loginRequest(credentials.username, credentials.password);
        }
    },

    /**
     * try to login using given credentials
     * @param username
     * @param password
     */
    loginRequest: function(username, password) {
        Connection.request('login', {username: username, password: password})
            .then(userInfo => {
                this._storeCredentials(username, password);
                this._setImages(userInfo.stylizedImgs, userInfo.rawImgs, userInfo.styles);
                Connection.setSessionCredentials(userInfo.username, userInfo.sessionID);
                console.log(`Welcome ${username}!`)
                ScreenManager.displayMessage(`Hello ${username}! Have fun styling images.`);
                ScreenManager._showLogoutButton();
                ScreenManager._showProfileButton();
                this._setUsername(userInfo.username);
                ScreenManager._hideLoginButton();
                ScreenManager.toHomeScreen();
            })
            .catch(error => {
                this._deleteCredentials();
                console.log(`Failed login attempt.`)
                Connection.setSessionCredentials(undefined, undefined);
                ScreenManager.rejectLoginFormInput();
                ScreenManager.displayMessage("Username/password combination does not exist!");
                ScreenManager.toLoginScreen();
            });
    },

    /**
     * try to logout
     * @return TRUE if successful, FALSE if failed
     */
    logoutRequest: function() {
        this._deleteCredentials();
        Connection.request('logout')
            .then(() => {
                ScreenManager.displayMessage('Good-bye.')
                ScreenManager._hideLogoutButton();
                ScreenManager._hideProfileButton();
                ScreenManager._showLoginButton();
            })
            .catch(error => ScreenManager.displayMessage("The logout procedure did not work. Please write a complain message to the adress that you can find in the tab contact."));
        Connection.setSessionCredentials(undefined, undefined);
        ScreenManager.toLoginScreen();
    },

    /**
     * The Style transfer request, which displays all results in the result page
     * @param styleImagePath
     * @param rawImagePath
     */
    styleTransferRequest: function(styleImagePath, rawImagePath){
        Connection.request('style_transfer', {rawImg: rawImagePath , style:styleImagePath})
            .then(resultInfo => {
                ScreenManager._displayAllResult(resultInfo.rawImg,resultInfo.style,resultInfo.stylizedImg)
            })
            .catch(error => {
                ScreenManager.rejectUploadFormInput();
                ScreenManager.displayMessage("StyleTransfer failed!");
                ScreenManager.toCreateScreen();
            });
    },
    /**
     * User asks for the recent versions of all images the user has
     * @param username
     * @param sessionID
     */
    updateUserRequest: function (username, sessionID) {
        Connection.request('update_user', {username:username, sessionID:sessionID})
            .then(userInfo => {
                this._setImages(userInfo.stylizedImgs
                    , userInfo.rawImgs, userInfo.styles);
                ScreenManager.displayUserImages();
            })
            .catch(error => {
                ScreenManager.displayMessage("updateUserRequest failed");
            });
    },

    /**
     * Asks for the images of all available modells
     */
    getStylesRequest: function () {
        Connection.request('get_styles',{})
            .then(styles => {
                ScreenManager._displayStyles(styles);
            })
            .catch(error => {
                ScreenManager.displayMessage(error);
            });
    },

    // ======================== INTERNAL HELPER METHODS =======================
    // store & delete credentials
    // load, store, delete login information
    _loadCredentials: function() {
        const cookie = Cookies.get('login');
        if (cookie) {
            return JSON.parse(cookie);
        } else {
            return undefined;
        }
    },
    _storeCredentials: function(username, password) {
        Cookies.set('login', JSON.stringify({username: username, password: password}), { sameSite: 'strict' });
    },
    _deleteCredentials: function() {
        Cookies.remove('login');
    },

    // set the username & replaces the profile button with the name
    _setUsername: function(username) {
        ScreenManager._DOM.profileButton.innerHTML = username;
    },

    // set Images
    _setImages: function (stylizedImages, rawImages, styleImages) {
        this._stylizedImages = stylizedImages;
        this._rawImages = rawImages;
        this._styleImages = styleImages;
    },
    _generateFilePath(username, type) {
        return `./public/images/${username}/${type}/${this.generateRandomFileName()}.jpg`;
    },
    generateRandomFileName: function() {
        let chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
        let string_length = 16;
        let randomstring = '';
        for (let i=0; i<string_length; i++) {
            let rnum = chars[Math.floor(Math.random() * chars.length)];
            randomstring = randomstring + rnum;
        }
        return randomstring;
    }

}

/**
 * Screenmanager
 * Manages all HTML elements on the screen
 */
const ScreenManager = {

    // ============================= DATA MEMBERS =============================
    _uploadedImage: undefined,
    /**
     * Document Object Model
     */
    _DOM: {
        // Navbar Buttons
        homeButton: undefined,
        createButton: undefined,
        theTeamButton: undefined,
        aboutButton: undefined,
        profileButton: undefined,

        // the login/logout button
        logoutButton: undefined,
        loginButton: undefined,

        // the message area
        messageContainer: undefined,
        closebtn: undefined,

        // the upload form
        uploadFileForm: undefined,
        uploadFileInput: undefined,
        styleSelectionInput: undefined,
        uploadAllForm: undefined,
        uploadFileSubmit: undefined,
        progress: undefined,

        // the login form
        loginForm: undefined,
        usernameInput: undefined,
        passwordInput: undefined,

        // the main Screens
        background: undefined,
        homeScreen: undefined,
        createScreen: undefined,
        theTeamScreen: undefined,
        aboutScreen: undefined,
        profileScreen: undefined,
        resultScreen: undefined,
        loginScreen: undefined,
        navbar: undefined,

        // result containers in result Screen
        loader: undefined,
        downloadResult: undefined,
        resultContainer: undefined,
        usedStyleContainer: undefined,
        usedRawImgContainer: undefined,

        // profile containers
        profileName: undefined,
        profileRawImages: undefined,
        profileStyles: undefined,
        profileStylizedImages: undefined,

        // available styles in Home Screen
        availableStylesContainer: undefined,
        showImagesContainer: undefined,

        // other buttons
        homeToCreate: undefined,
        aboutToCreate: undefined,
    },

    // ============================= PUBLIC METHODS ============================
    /**
     * initialize the screen manager at startup
     */
    initialize: function() {

        const DOM = this._DOM;
        // noinspection SpellCheckingInspection
        DOM.background = document.getElementById('background');
        DOM.navbar = document.getElementById('navbar');
        DOM.loginScreen = document.getElementById('loginScreen');
        DOM.loginForm = document.getElementById('loginForm');
        DOM.usernameInput = document.getElementById('username');
        DOM.passwordInput = document.getElementById('password');
        DOM.logoutButton = document.getElementById('logoutButton');
        DOM.loginButton = document.getElementById('loginButton');
        DOM.uploadFileForm = document.getElementById('uploadFileForm');
        DOM.uploadFileInput = document.getElementById('uploadFileInput');
        DOM.uploadAllForm = document.getElementById('uploadAllForm');
        DOM.uploadFileSubmit = document.getElementById('uploadFileSubmit');
        DOM.progress = document.getElementById('progress');
        DOM.styleSelectionInput = document.getElementsByName('styleSelection');


        //message box
        DOM.message = document.getElementById('message');
        DOM.messageContainer = document.getElementById('messageContainer');
        DOM.closebtn = document.getElementById('closebtn');

        // Navbar Buttons
        DOM.homeButton = document.getElementById('home');
        DOM.homeScreen = document.getElementById('homeScreen');
        DOM.createButton = document.getElementById('create');
        DOM.createScreen = document.getElementById('createScreen');
        DOM.theTeamButton = document.getElementById('theTeam');
        DOM.theTeamScreen = document.getElementById('theTeamScreen');
        DOM.aboutButton = document.getElementById('about');
        DOM.aboutScreen = document.getElementById('aboutScreen');
        DOM.profileButton = document.getElementById('profile');
        DOM.profileScreen = document.getElementById('profileScreen');
        DOM.resultScreen = document.getElementById('resultScreen');

        // Other Buttons
        DOM.homeToCreate = document.getElementById('homeToCreateButton');
        DOM.aboutToCreate = document.getElementById('aboutToCreateButton');

        // result Screen Container
        DOM.resultContainer = document.getElementById('resultContainer');
        DOM.usedStyleContainer = document.getElementById('usedStyleContainer');
        DOM.usedRawImgContainer = document.getElementById('usedRawImgContainer');
        DOM.usedRawImgContainer = document.getElementById('usedRawImgContainer');
        DOM.loader = document.getElementById('loader');
        DOM.downloadResult = document.getElementById('downloadResult');

        // profile Screen Containers
        DOM.profileName = document.getElementById('profileName');
        DOM.profileRawImages = document.getElementById('profileRawImages');
        DOM.profileStyles = document.getElementById('profileStyles');
        DOM.profileStylizedImages = document.getElementById('profileStylizedImages');

        // Create dynamic style Container
        DOM.availableStylesContainer = document.getElementById('availableStylesContainer');
        DOM.showImagesContainer = document.getElementById('showImagesContainer');

        // Form Listeners
        DOM.loginForm.addEventListener('submit', event => this._processLoginSubmit(event));
        DOM.logoutButton.addEventListener('click', () => this._processLogoutClick());
        DOM.uploadAllForm.addEventListener('submit', event => this._processUploadSubmit(event));
        DOM.closebtn.addEventListener('click', () => this._hideMessageContainer());

        // Navbar Button Click
        DOM.homeButton.addEventListener('click', () => this.toHomeScreen());
        DOM.createButton.addEventListener('click', () => this.toCreateScreen());
        DOM.theTeamButton.addEventListener('click', () => this.toTheTeamScreen());
        DOM.aboutButton.addEventListener('click', () => this.toAboutScreen());
        DOM.profileButton.addEventListener("click", () => this.toProfileScreen());
        DOM.loginButton.addEventListener('click', () => this.toLoginScreen());

        // Other buttons listeners
        DOM.homeToCreate.addEventListener('click', () => this._checkLogin());
        DOM.aboutToCreate.addEventListener('click', () => this._checkLogin())

    },

    /**
     * display a message
     * @param message
     */
    displayMessage: function(message) {
        this._showMessageContainer();
        this._DOM.message.innerHTML = message;
    },

    // display images

    /**
     *  Displays all the result images after the style Transfer
     * @param rawImagePath
     * @param styleImagePath
     * @param resultImagePath
     * @private
     */
    _displayAllResult: function(rawImagePath, styleImagePath, resultImagePath){
        this.toResultScreen();
        this._DOM.usedRawImgContainer.innerHTML = `<img alt="rawImg" src="${rawImagePath}">`;
        this._DOM.usedStyleContainer.innerHTML = `<img alt="styleImage" src="${styleImagePath}">`;
        this._DOM.resultContainer.innerHTML = `<img alt="resultImage" src="${resultImagePath}">`;
        this._DOM.resultContainer.innerHTML = `<img alt="resultImage" src="${resultImagePath}">`;
        this._hideLoader();
    },


    displayUserImages: function() {
        let images = User._rawImages;
        let imagesDisplay = '';
        for(const key in images) {
            if (images.hasOwnProperty(key)) {
                imagesDisplay += `<input type="radio" name="userImagesSelection"
                alt="${key}" id="${key}" value="${images[key].rawImagePath}" form="uploadAllInput">
                <label id="userImagesLabel" class="imagesLabel" style="background-image: 
                url(${images[key].rawImagePath}); background-size:cover" for="${key}"></label>`;
            }
        }
        this._DOM.showImagesContainer.innerHTML = imagesDisplay;
    },

    /**
     * Displays the Styles in the Home Screen
     * @param images
     * @private
     */
    _displayStyles: function(images){
        let imagesDisplay = '';
        for(const key in images) {
            if (images.hasOwnProperty(key)) {
                imagesDisplay += `<input type="radio" name="styleSelection"
                alt="${key}" id="${key}" value="${images[key]}" form="uploadAllInput">
                <label id="styleImagesLabel" class="imagesLabel" style="background-image: 
                url(${images[key]}); background-size:cover" for="${key}"></label>`;
                }
            }
        this._DOM.availableStylesContainer.innerHTML = imagesDisplay;
    },

    /**
     *  Displays all pictures needed for the profile page
     * @private
     */
    _displayProfile: function(){
        let imagesDisplay1 ='';
        let imagesDisplay3 ='';
        const username = Connection._username;
        const rawImages = User._rawImages;
        console.log(rawImages);
        const stylizedImages = User._stylizedImages;

        // Profile Name
        this._DOM.profileName.innerHTML = username;

        // Raw Images
        for(const key in rawImages) {
            if (rawImages.hasOwnProperty(key)) {
                imagesDisplay1 += `<div class="profileImages" 
                style="background-image: url(${rawImages[key].rawImagePath}); background-size:cover"></div>`;
            }
        }
        this._DOM.profileRawImages.innerHTML = imagesDisplay1;

        // StylizedImages
        for(const key in stylizedImages) {
            if (stylizedImages.hasOwnProperty(key)) {
                imagesDisplay3 += `<div class="profileImages" 
                style="background-image: url(${stylizedImages[key].stylizedImagePath}); background-size:cover"></div>`;
            }
        }
        this._DOM.profileStylizedImages.innerHTML = imagesDisplay3;
    },

    // Changing backgrounds on create
    _changeToHomeBackground: function(){
        this._DOM.background.style.backgroundImage = 'url(../img/NST-home-background.png';
    },
    _changeToDefaultBackground: function(){
        this._DOM.background.style.backgroundImage = 'url(../img/NST-default-background.png';
    },

    /**
     * switch to the login screen
     */
    toLoginScreen: function() {
        this._hideAllScreens();
        this._showLoginScreen();
    },

    /**
     * switch to the Home screen and display the styles available
     */
    toHomeScreen: function() {
        this._hideAllScreens();
        this._changeToHomeBackground();
        this._showHomeScreen();
    },

    /**
     * switch to the Create screen
     */
    toCreateScreen: function() {
        if(Connection._username === undefined){
            this.toLoginScreen();
            this.displayMessage('Please Login to access all your Images!')
        }else{
            this._hideAllScreens();
            this._showCreateScreen();
            User.getStylesRequest();
            User.updateUserRequest(Connection._username,Connection._sessionID);
            this.displayUserImages();
        }
    },

    /**
     * switch to the theTeam screen
     */
    toTheTeamScreen: function() {
        this._hideAllScreens();
        this._showTheTeamScreen();
    },

    /**
     * switch to the About screen
     */
    toAboutScreen: function() {
        this._hideAllScreens();
        this._showAboutScreen();
    },

    /**
     * switch to the profile screen
     */
    toProfileScreen: function() {
            this._hideAllScreens();
            this._showProfileScreen();
            User.updateUserRequest(Connection._username,Connection._sessionID);
            this._displayProfile();
    },

    /**
     * switch to the result screen
     */
    toResultScreen: function() {
        this._hideAllScreens();
        this._showResultScreen();
    },

    /**
     * hide all the screens
     * @private
     */
    _hideAllScreens: function(){
        this._hideLoginScreen();
        this._hideHomeScreen();
        this._hideCreateScreen();
        this._hideTheTeamScreen();
        this._hideAboutScreen();
        this._hideProfileScreen();
        this._hideResultScreen();

        this._changeToDefaultBackground();
    },

    // ======================== INTERNAL HELPER METHODS =======================

    // show/hide the logout button
    _showLogoutButton: function() {
        this._DOM.logoutButton.classList.remove('invisible');
    },
    _hideLogoutButton: function() {
        this._DOM.logoutButton.classList.add('invisible');
    },

    // show/hide the login button
    _showLoginButton: function() {
        this._DOM.loginButton.classList.remove('invisible');
    },
    _hideLoginButton: function() {
        this._DOM.loginButton.classList.add('invisible');
    },

    // show/hide the profile button
    _showProfileButton: function() {
        this._DOM.profileButton.classList.remove('invisible');
    },
    _hideProfileButton: function() {
        this._DOM.profileButton.classList.add('invisible');
    },

    // show/hide the login screen
    _showLoginScreen: function() {
        this._DOM.loginScreen.classList.remove('invisible');
        this._DOM.loginScreen.classList.add('animate__animated', 'animate__fadeIn');
        this._changeToHomeBackground();
    },
    _hideLoginScreen: function() {
        this._DOM.loginScreen.classList.remove('animate__animated', 'animate__fadeIn');
        this._DOM.loginScreen.classList.add('invisible');
    },

    // show/hide the home screen
    _showHomeScreen: function () {
        this._DOM.homeScreen.classList.remove('invisible');
        this._DOM.homeScreen.classList.add('animate__animated', 'animate__fadeIn');
    },
    _hideHomeScreen: function() {
        this._DOM.homeScreen.classList.remove('animate__animated', 'animate__fadeIn');
        this._DOM.homeScreen.classList.add('invisible');
    },

    // show/hide the create screen
    _showCreateScreen: function () {
        this._DOM.createScreen.classList.remove('invisible');
        this._DOM.createScreen.classList.add('animate__animated', 'animate__fadeIn');
    },
    _hideCreateScreen: function() {
        this._DOM.createScreen.classList.remove('animate__animated', 'animate__fadeIn');
        this._DOM.createScreen.classList.add('invisible');
    },

    // show/hide the the team screen
    _showTheTeamScreen: function () {
        this._DOM.theTeamScreen.classList.remove('invisible');
        this._DOM.theTeamScreen.classList.add('animate__animated', 'animate__fadeIn');
    },
    _hideTheTeamScreen: function() {
        this._DOM.theTeamScreen.classList.remove('animate__animated', 'animate__fadeIn');
        this._DOM.theTeamScreen.classList.add('invisible');
    },

    // show/hide the about screen
    _showAboutScreen: function () {
        this._DOM.aboutScreen.classList.remove('invisible');
        this._DOM.aboutScreen.classList.add('animate__animated', 'animate__fadeIn');
    },
    _hideAboutScreen: function() {
        this._DOM.aboutScreen.classList.remove('animate__animated', 'animate__fadeIn');
        this._DOM.aboutScreen.classList.add('invisible');
    },

    // show/hide the profile screen
    _showProfileScreen: function () {
        this._DOM.profileScreen.classList.remove('invisible');
        this._DOM.profileScreen.classList.add('animate__animated', 'animate__fadeIn');
    },
    _hideProfileScreen: function() {
        this._DOM.profileScreen.classList.remove('animate__animated', 'animate__fadeIn');
        this._DOM.profileScreen.classList.add('invisible');
    },

    // show/hide the result screen
    _showResultScreen: function () {
        this._DOM.resultScreen.classList.remove('invisible');
        this._DOM.resultScreen.classList.add('animate__animated', 'animate__fadeIn');
    },
    _hideResultScreen: function() {
        this._DOM.resultScreen.classList.remove('animate__animated', 'animate__fadeIn');
        this._DOM.resultScreen.classList.add('invisible');
    },

    // show/hide the message container
    _showMessageContainer: function() {
        this._DOM.messageContainer.classList.remove('invisible');
        this._DOM.messageContainer.classList.remove('animate__animated', 'animate__fadeOut');
        this._DOM.messageContainer.classList.add('animate__animated', 'animate__fadeIn');
    },
    _hideMessageContainer: function() {
        this._DOM.messageContainer.classList.remove('animate__animated', 'animate__fadeIn');
        this._DOM.messageContainer.classList.add('animate__animated', 'animate__fadeOut');
        // To ensure smooth fade out (see messageContainer animation-duration)
        setTimeout(function(){
            ScreenManager._DOM.messageContainer.classList.add('invisible');
            }, 500);

    },

    // show/hide Loader
    _showLoader: function(){
        this._hideAllScreens();
        this._DOM.loader.classList.remove('invisible');
        this._DOM.loader.classList.add('animate__animated', 'animate__fadeIn');
    },
    _hideLoader: function(){
        this._DOM.loader.classList.remove('animate__animated', 'animate__fadeIn');
        this._DOM.loader.classList.add('invisible')
    },

    /**
     * process submission of the login form
     * @private
     */
    _processLoginSubmit: function(event) {
        event.preventDefault();
        const username = this._DOM.usernameInput.value;
        const password = this._DOM.passwordInput.value;
        if (username.length === 0 || password.length === 0) {
            this.rejectLoginFormInput();
            this.displayMessage('Please enter username and password.');
        } else {
            User.loginRequest(username, password);
        }
    },

    // reject login credentials from the login form
    rejectLoginFormInput: function() {
        this._DOM.passwordInput.value = '';
        this._DOM.usernameInput.select();
    },

    /**
     * process submission of the upload form
     * @param event
     * @private
     */
    _processUploadSubmit: function (event) {
        event.preventDefault();
        const stylePath = this._lookupCheckedRadio(event, 'styleSelection');
        const userImagePath = this._lookupCheckedRadio(event, 'userImagesSelection');

        if(userImagePath === undefined){
            this.displayMessage('Please select a Image to be stylized!');
        }else if(stylePath === undefined){
            this.displayMessage('Please select a style!');
        }else{
            User.styleTransferRequest(stylePath, userImagePath);
            ScreenManager._showLoader();
        }
    },

    // process click of the logout button
    _processLogoutClick: function() {
        User.logoutRequest();
    },

    // reject upload inputs from the login form
    rejectUploadFormInput: function() {
        this._DOM.uploadFileInput.value = '';
        this._DOM.styleSelectionInput.value = '';
    },


    // Radio check
    _lookupCheckedRadio: function(event, name) {
        event.preventDefault();
        let images;
        if(name === 'styleSelection'){
            images = document.getElementsByName('styleSelection');
        }else{
            images = document.getElementsByName('userImagesSelection')
        }
        for (let i = 0, selected = ""; i < images.length; i++) {
            if (images[i].checked) {
                selected = images[i].value;
                return selected;
            }
        }
        return undefined;
    },

    // Login Check when on home -> create
    _checkLogin: function () {
        if(Connection._username === undefined){
            this.displayMessage('Please Login to use the style transfer!');
            this.toLoginScreen();
        }else{
            ScreenManager.toCreateScreen();
        }
    },

    reset: function() {
        setTimeout(() => {
            ScreenManager.setProgress(0);
            ScreenManager.enableUpload();
            ScreenManager.clearFile();
        }, 2000);
    },
    setProgress: function(progress) {
        this._DOM.progress.style.width = `${Math.round(100*progress)}%`;
    },
    enableUpload: function() {
        this._DOM.uploadFileSubmit.disabled= false;
    },
    disableUpload: function() {
        this._DOM.uploadFileSubmit.disabled = true;
    },
    clearFile: function() {
        this._DOM.uploadFileInput.value = null;
    }
}
// handle upload progress (= number between 0 and 1)
const progressHandler = function(progress) {
    ScreenManager.setProgress(progress);
    if (progress === 1) {
        ScreenManager.displayMessage('Upload successful');
        ScreenManager.reset();
        User.updateUserRequest(Connection._username,Connection._sessionID);
    }
    if (progress === 0) {
        ScreenManager.displayMessage('');
    }
}

// handle upload errors
const failHandler = function(reason) {
    ScreenManager.displayMessage(reason);
    ScreenManager.reset();
}

// start the application once the HTML has loaded
document.addEventListener('DOMContentLoaded', () => {

    // initialization tasks
    Connection.initialize();
    ScreenManager.initialize();

    // try to resume with stored credentials
    User.resume();

    // setup new uploader instance for the (only) file input
    const U = new Uploader();
    U.listen(ScreenManager._DOM.uploadFileInput);
    U.onFail = failHandler;
    U.onProgress = progressHandler;

    // trigger an upload when the form is submitted
    ScreenManager._DOM.uploadFileForm.addEventListener('submit', event => {
        event.preventDefault();
        ScreenManager.disableUpload();
        U.upload();
    })
});


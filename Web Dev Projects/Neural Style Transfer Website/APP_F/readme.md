# Setup

# IMPORTANT: Before testing stuff, load a user first. You can use this one {name: testuser, password: password123} 

## Database (see wiki tutorial?)

1. Follow the tutorial in the wiki to install mySql and our latest database.

2. Database connection credentials are stored in ```./app/Config.js```, change them if your password is not 1234.

## Node

3. The node dependencies have to be installed, e.g. via ```npm install```.

4. The project should be ready. Run ```node server.js``` to start the server. 
   The minimal testing html page can be accessed locally via
   ```http://localhost:1337/minimal.html```. (Inspect to see frontend console logs)
   

## Style Transfer 
   
5. If you use a VENV to install all the packages, you have to activate in the WebStorm Terminal, e.g. like this 
 ```C:\Users\Sam\.virtualenvs\fast_neural_style-_fZwbJfS\Scripts\activate```

(6. In case you want to work with new users: Style transfer requires rawImgs and stylizedImgs folders inside of a folder with the user's name.) 
   
   
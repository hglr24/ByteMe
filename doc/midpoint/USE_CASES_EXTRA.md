USE_CASES_EXTRA
===
# Launcher
### The user creates a new account
* Validate that the username password are adequate (i.e. no inappropriate symbols, not a username that is already in the database, adequate password length/complexity, etc.)
* Will need to invoke the database to handle storing this username and password pair so that the launcher recognizes it as a valid account and allows them to either enter the authoring environment or the game center
* Switches scene to either allow the user to enter the game center and play a default game or open up the authoring environment to start creating games
* When opening up the authoring environment, point out a button that provides a splash page of information on how to use the authoring environment and some tips/tricks (this will not occur in future openings)

### The user wants to change their password
* Open up a new page that offers four prompts - one for the user to enter their username and current password, a new password, and a retype of their new password
* If the username and password are given as valid by data, and the new password and its retype are adequate in complexity/length and match one another, notify the database to overwrite the current authentication mechanism

### User selects that they wish to load in a previous game they worked on into authoring
* Retrieve game displays associated with the user from the database and prompt the user with available options of their game listings
# Game Center
### User clicks read more on a game
* This opens up a new window
* New window populates based on GameCenterData object
* New window contains longer description, image, rating, a rate button, a play button (everything in card plus some)
* From here they will be able to return to center
### User rates a game
* User clicks rate button in read more window
* a dialog box opens up with options for giving a rating (1-5) and option to add comment
* They click add rating or cancel
* GameCenterData corresponding to this game is then updated with a new rating and commentlist
* new GameCenterData is then saved (xml file updates)
### User closes the runner
* When the user closes the runner, this should stop the runner's back-end from running as well 
* They should then be able to re-launch the runner for the same game
* If time permits, rating dialogue box automatically pops up to say "would you like to rate?"
### User chooses to upload game
* Dialog box comes up asking for a filepath
* Pass on this filepath to data to check if has valid game center data and valid game object info 
* If these are valid, then the game is added to the list of displayed games.
* Note: to make this work, this may need to be copied into the created_games folder
### User sorts the game list
* User clicks sort
* User chooses from a drop-down list of ways to sort the gamelist
* Depending on what they select, the list of available games is sorted using comparator for GameCenterData object
* After selecting and sorting, the display updates to show the newly sorted information

# Data
### User clicks save on a newly created game in authoring environment
* The data manager will send an insert statement to the database to create a new row in the Games table for the newly created game
* The data manager serializes the passed Game object into a string of xml
* The data manager serializes the passed GameCenterData object into a string of xml
* The data manager saves the two raw xml strings into the corresponding row of the database
* Data manager saves the username of the person who created this game

### User clicks load on authoring environment
* Authoring environment asks data manager for the available games for that user to edit
* Data manager checks the available games authored by that user and returns a list of their titles

### User chooses a game to load into the authoring environment
* Authoring environment displays this list of titles
* Title is sent to data manager which returns deserialized xml of the Game object corresponding to that game to be cast by the authroing environment and loaded into the authoring environment

### User enters valid username and password
* Database checks the users table and hashes the password to make sure it is the same as the saved hashed password
* Return a true to the launcher to allow the user to sign in and load in the options for that user

### User enters invalid username and password
* Database checks the users table and hashes the password to make sure it is the same as the saved hashed password
* Returns a false to the launcher to prompt the user to reenter the username and password and inform them that it doesn't match a valid username and password

### User clicks on the playing history for a game
* Sends request to data manager with the username and gamename
* DataManager processes results to produce a graph of the playing history for the game

### User clicks to view leaderboard for a game
* Sends a request to the datamanager with the gamename
* Displays a list of the users with the top scores and the scores they got on the game

### User saves a game from the runner and it is saved as a checkpoint
* Runner creates a DataManager and passes the current level to the DataManager
* DataManager serializes the Level to xml
* DataManager stores the xml as a string in the checkpoints table, associated with the game name and username

### User clicks to view the rating of a game
* GameCenter sends request to the database with the game name asking for the rating associated with the game
* DataManager runs a query to determine the average rating of the game and returns the value to the GameCenter

### User saves a game that they have already created and are just editing
* Authoring sends a request to data to save a game
* Data checks if the game has already been created and if so will update the row corresponding to that game and username with the updated serialized xml game

### Two users edit the same game at the same time and save the game
* Data independently saves the game in the row for that game corresponding to the user that was editing the game

### User tries to name a game the same name as another game that is created
* If the other game is created by the same user, don't let them.  Prompt the user asking if they wish to overwrite their other game of the same name
* If the other game is by a different user, allow this game to be created (gameName + userName should be unique)

### User adds a profile picture
* Save the picture to container of available images
* Update the reference to their profile picture in the users table

# Engine

### Two entities collide and react accordingly
* The CollisionSystem will register that the two entities are colliding and attach a CollidedComponent holding the other entity as its value, corresponding to the direction of collision 
* The EventHandlerSystem will pass in all Entities when calling `Event.execute()`. A CollisionEvent looks for Entities that have a CollidedComponent and execute an action for each if the orientation AND the entity collided with match the CollisionEvent specified by Authoring

### User passes a level and engine alerts the runner that the level is over
* The CollisionSystem will possibly detect a collision with a dummy entity placed at the end of the level, and the EventHandlerSystem will trigger an event altering the component value of that entity that signals the runner to change levels

### The runner tells the engine to load a new level
* The Runner initializes a new Engine with the new level as the parameter

### User makes it to a new checkpoint
* Engine stores current collection of Entities and Events into a new Level object as the checkpoint. 
### User saves the game in the middle of a level
* Engine stores current collection of Entities and Events into a new Level object, and exports the Level to Runner for saving the game data. 
### User saves the game in between levels

### User pauses game and engine stops updating
* The Runner detects a key press or some other user input and stops its own update loop, which stops calling updateState of its engine object, freezing all entities until updateState is called again

### User pauses game and engine sends necessary information to Runner to display on the pause screen
* Once the Runner stops calling updateState on the Engine, it can call other methods on the engine asking for the specific states of some dummy entities that hold state on the level/game.

### An event occurs leading to a user defined variable having to be updated
* 

### User loses a life and gets reset to the last checkpoint
* Engine gets the collection of Entities from the checkpoint Level object, and replaces its current Entity collection data with the Entities retrieved from checkpoint.

### Player moves and camera follows player's position
* The camera object will display the desired portion of the level, centered around the player's current position. The cameria will be updated accordingly by engine on the call to update(). Runner will use the information in the camera object to display only the desired part of the level. 
# Runner
### User pauses the game and pause screen is displayed
* Stop game loop and level animation
* Add pause menu to stage
* Resume level animation on click of resume

### User's score increases and the score field gets updated correctly


### User loses a life and the number of lives display gets updated appropriately

### User exits current game and returns to the game center
* Level status is saved as a level object
* Animation stops
* Game runner closes

### User gets close to the edge of the screen and the view changes
* Camera Object moves
* Set translate x to Camera object
* View changes

### User jumps and the camera follows their character
* Camera Object moves
* Set translate y to Camera object
* View changes

### User clicks a button and the animation stops
* Animation pauses
* Pause screen opens

### User controlled entity jumps and their image changes to the jumping image
* Engine updates status of entity to jumping
* ImageViewComponent updates to jumping image

### User loses last life and a 'You lose' screen is displayed
* Life object in runner decrements to zero
* Animation stops
* Game over screen is played

### User defeats the last level and a 'You Win' screen is displayed
* Engine alerts runner that last level is completed 
* Score is saved to high scores
* Animation stops
* 'You win' screen is displayed


# Authoring
### User tries to enter invalid options for a particular event's actions 
* Display pop-up error box informing them of why their input was invalid
* Provide them with the opportuniity to re-enter valid paramters of an action
### User removes an event from an entity
* Need to remove the event from the Authoring's version of entity
* Need to remove the associated actions of that event from the entity - since actions are automatically associated with an event, it should be sufficient to remove the event
### User modifies an event to stack actions on top of one another
* Allow user with option to have one action under the constraint of one condition (some variable is less than or equal to a particular value) and allow for another action under the other circumstance
### Allow user to define a general event that relies upon the value of a variable
* Prompt the user with a drop down of a particular object's possible values (such as its current xposition), along with a comparator ( less than, equal to, or greater than) and a box to enter a particular value in order to create a conditional
* Following this part, prompt the user in the same area with a list of actions they may stack when the current condition holds 
### User Right-Clicks an Entity: Delete is selected
* The EntityWithImage will be removed from the Viewer by removing it from the StackPane's children
* It will also have a method to be called on either the AuthoringEntity or the ObjectManager that will remove it from the list of AuthoringEntities on the Level
### User Right-Clicks and Entity: Bring to Front is Selected
* The StackPane will have the toFront() method called on the EntityWithImage selected
### Adding a new default type
* First the DefaultTypesFactory will need to be re-coded to take in XML files
* Each file will have a <Category> tag that will determine which of the titledPanes to be displayed in
* Each file will then have a <Components> tag where all of the subtags will be Class names associated with each component
* Each defaultType will have its own file and all of them will live in a single folder
* The DefaultTypesFactory will then be able to read every file and use reflection to create each default entity
### Better Snap to Grid
* The snap to grid will no longer be to just the upper left corner but will be any corner
* The snapToGrid() method in Viewer will now check the four corners of the Image and calculate which is closer to a grid point
* The ImageWithEntity will then snap to that grid point
### Audio Files
* The current AssetManager will be generalized such that the structure can be adjusted to accomodate audio files as well
* The user, in the Level properties, will be able to add audio to the level
# Miscellaneous
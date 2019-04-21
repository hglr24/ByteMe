USE_CASES
======
# Data

## 1. Author tests a game while creating it
* A button in the authoring environment (say "test" button) is clicked. Authoring Environment then invokes Data to save what has been created into a temporary file, and calls Game Runner to load the temporary file and play a game in a new window until the virtual player dies, after which it returns to the window of Authoring Environment.

## 2. Publish a game from the authoring environment to be loaded later on
* Uses xstream to write the GameObjects to a file of GameObjects, the background to a file containing the background
* Saves the files to a directory in the main project under the name of the game

## 3. Load a game in the game center
* Find the game file that contains "summary" information of a game (for now, this may be number of players, highest score, and whether or not the user has a saved state of the game at a current checkpoint)
* This will contain limited access to information about a game because there is no need for the game center to necessarily know about the rules or any of the other logic pertaining to the game

## 4. Write end game result to XML file
* When engine sends signal that the game is over, game runner will package the important statistics such as final score, lives remaining, time, etc and write it to an XML file that gets saved in the past games folder
* If the high score exceeds the current highest score, the XML file sent to the game center to display the game will need to be updated in order to reflect this. The GameState xml file will also need to be updated so, if the user chooses to play this game again, the engine and runner will be playing a new game

## 5. Load a checkpoint from an XML file
* Locate the checkpoint XML file from checkpoints file within the folder for that game
* Load the game objects into a list of game objects
* Create a new engine to use for the game that is being reloaded
* Send the list of game objects to the engine

## 6. Update high scores associated with a game
* Read that game's high scores file into an ordered list of high scores
* Add the most recent score to the list and the game in which the score was achieved
* Write back to an XML file that game center will be able to access and display in a Scores menu

## 7. Load a published game into authoring environment
* Read in the files associated with that game folder into objects in the authoring environment so that they are displayed properly within the scope of the scrollable page and mutable in the edittable tabs 
* Be able to overrwrite virutally any information that was previously stored in the game files, whether it be for the display, engine, or runner

## 8. User uploads new image file to be used in the game
* Add the image to the global directory storing images so that it can be accessed in later uses of the game
* This directory will need to be global in order for runner, authoring, and potentially engine to access. Engine may need to access this resources file in the future if certain aesthetic options (such as the music) change according to particular logic/engine.external.events (timer is running low, so music may speed up to motivate the user to move faster)

## 9. An attempt is made to access a faulty resource
* Throw an error that the resource was faulty that and display an error message to the user that the file for game "game name" was corrupted. 
* Ask if they wish to overwrite the files, delete the files or keep them as is and access a new game

# Engine

## 10. User presses 'A' to make player 1 jump
* The GameRunner translates 'A' pressed to 'button 1' and passes 'button 1 pressed' to engine
* engine interprets button 1 as the assocaited action 'jump'
* engine modifies gameobject's (in this case player 1) state components: in this case velocity and position
* engine.notifyObservers()
* GameRunner displays changes

## 11. Player wins game
* GameRunner sends movement (key press as in 13) to engine
* engine detects overlap with finish
* engine changes gameState.gameOverState to true
* engine notifies observers
* frontend queries gameState model
* realizes gameOver
* GameRunner.stop() (or equivalent)

## 12. Character stands on a platform (objects O1 and O2 touch)
* Every component has its designated index that is mapped to a bit index in a GameObject's entityID
* Every GameObject maintains an array of component that it is equipped with
* TouchManager maintains a list of pairs of GameObjects that have been touching each other up to the last frame
* In each `Engine.updateState(input)` call (made by GameRunner), `TouchManager.update()` is called for iterating through the list of already touching objects and check if they are still touching (i.e. intersecting)
    * If still touching
        * call O1.getcomponent(COLLISION_COMPONENT_INDEX).touch(O2) and O2.getcomponent(COLLISION_COMPONENT_INDEX).touch(O1)
        * CollisionComponent concatenates the UniqueIDs of this component's GameObject and the other collided GameObject to generate a key, and uses the key to look up in a properties file for engine.external.actions to be triggerred by the certain touching event.
        * CollisionComponent retrieves an array of integers indicating engine.external.actions to be triggerred, and looks up the other properties file to find methods mapped to these engine.external.actions inside ActionManager, which has information about the CollisionComponent and its tied GameObject
        * ActionManager performs the action (reduce the number of lives of Mario if it touches Spiny)
        * TouchManager calls Engine.addUpdatedGameObject(O1) and Engine.addUpdatedGameObject(O2), so that O1 and O2 will eventually be returned to GameRunner at the end of `Engine.updateState(input)`.
    * Else, call O1.untouch(O2) and O2.untouch(O1), and remove the pair from its list of touching objects.


## 13. Character collides with an object (objects O1 and O2 collide)
* In each `Engine.updateState(input)` call, MovementManager updates position parameters of GameObjects that should move, and calls CollisionManager.addMovedObject(Object o) such that CollisionManager can maintain a list of GameObjects that have moved since the last frame, and will check on collision engine.external.events for these objects.
* The `CollisionManager.update()` call, made inside `Engine.updateState(input)`, checks intersections between every pair of moved and collidable objects
* If two collidable objects seem to be intersecting, CollisionManager calls `TouchManager.areTouching(O1,O2)` 
    * If false:
        * a new collision is detected and CollisionManager calls O1.getcomponent(COLLISION_COMPONENT_INDEX).collide(O2),O2.getcomponent(COLLISION_COMPONENT_INDEX).collide(O1) to perform the collision action
        * The rest of the steps are similar to handling touching, except that CollisionComponents of O1 and O2 now looks up a different properties file to figure out engine.external.actions to perform on a collision event
    * If true: 
        * This is a touching event instead of collision event. Nothing needs to be performed by CollisionManager

## 14. User presses a button to quit the currently running game
* GameRunner detects button being pressed and pass the user input by `Engine.getUpdateState(input)`
* Engine has one GameObject (say "dummy") which implements the value component and is able to manage the game-wise global variables (level, lives, flag variable indicating game ending, ... etc.). Engine calls dummy.setValue(gameEnding, true) and puts dummy to the list of changed GameObjects to be returned to GameRunner.
* GameRunner reads through the list of GameObjects whose properties have changed returned by the last updateState call, and notices that the gameEnding variable stored by dummy has changed.
* GameRunner calls corresponding function to end the game and shut down Engine.
## 15. An enemy enters the view and starts firing towards the game character
* Engine updates the x and y positions of movable GameObjects (character, camera, enemies, etc.) and returns the updated GameObjects to GameRunner
* GameRunner implements its logic to determine which GameObjects are visible in the game view, and displays visualization for those GameObjects.

## 16. The game character dies (loses a life)

* After the `Engine.updateState(input)` call, some type of collision or other event is detected by a manager class like the `TouchManager`, which changes the state of the game character (like subtracting a life). 
* The state of the game character is then packaged into the `ViewState` or whatever object is returned on the `updateState` call, and the front-end realizes that a life has been lost, decrementing the number of lives in the visual display accordingly.

## 17. The game character starts a new life from a checkpoint

## 18. The game character dies and runs out of lives -- game ends

## 19. The game character stands on a collapsible block for too long and it begins to crumble

* On each call of `Engine.updateState(input)`, a manager class like the `CollisionManager` updates objects who continue to collide. The collapsible block would have some sort of health state that is decremented every update cycle that the game character is detected colliding with the block. 
* This health state would be communicated back through the updating of `ViewState` each cycle, and the front-end can animate the block according to how much health it has.

## 20. The crumbling block fully crumbles and disappears

* Once the health state of the block has depleted to 0 after the corresponding number of update cycles, the model will realize that it should be removed and therefore will not include it in the `ViewState` object that is returned by `updateState`. As such, the front-end will not render the object, because it doesn't exist anymore.

## 21. The game character falls now that the crumbling block is gone

* Now that the block is gone, the `CollisionManager` after the call to `Engine.updateState(input)` will no longer detect a collision between the character and the block. The character will therefore fall according to the rules of gravity in the next update cycle until it again collides with an object.

## 22. The game character breaks 5 platforms and earns a life

* The user defines a variable in the authoring environment called 'platformCount' and an associated event that when the player breaks a platform, platformCount increments, and when platformCount reaches 5, the count resets and the player gets another life. 
* When the game is run by engine, the platformCount variable is initialized to 0. 
* Each time the player breaks a platform (after a collision) the count is incremented. The platformCount is not displayed visually.
* On the next call to update, the player collides with a platform and the breaks it, and the platformCount reaches 5. 
* Engine increments the player's lifeCount and the platformCount is reset to 0.
* When the call to Engine.updateState(input) returns the viewState object, the Game Runner displays the updated number of lives.

## 23. The game character runs in front of a cloud
* The cloud is a GameObject with a position and velocity that does not do anything when it collides with another object.
* The game player moves forward and collides with the cloud and the collision causes nothing to happen. 
* On the call to Engine.updateState(input) no change associated with this collision is returned in the viewState object to the Game Runner. 

## 24. The game character falls off the screen and loses a life.
* The game character moves forward off of a platform and begins to fall.
* The game character falls according to gravity in the scene and collides with the lower edge of the screen.
* The engine detects the collision and updates the game character's lifecount.
* The game character still has available lives so the engine resets the game character's position to the most recent checkpoint.

## 25. 2 moving platforms collide and reverse direction
* The moving platforms are moving towards each other in the x direction with equal and opposite velocities.
* Engine detects that the two moving platforms collide, and updates each platforms velocity so that it is moving in the opposite direction.

## 26. The game character reaches the end of the level and the camera stops scrolling.
* The game character moves to the furthest right point in the level and reaches the end of the level.
* The camera scrolls until its edge reaches the end of the level.
* The game character continues to the move to the right but the camera no longer scrolls with the game character as it has reached the rightmost boundary in the level. 

# Authoring
## 27. Create a new object type
* User will select the object option from the "Create New Type" page
* A small window will appear and they will select from a dropdown first, what the new object's name is, then what object type the created object is, and lastly what this new object type is based on
* Once they select create, their new object will appear in the "Created Object Types" library

## 28. Add Single Object Event
* The user will right click on an object already displayed on the scene
* They will then select "Edit Events"
* From this page they will select the "+" option
* From here they can select the type of Event from a dropdown
* Next they will select the desired action from a second dropdown
* Lastly they will select "Apply" for the event to be saved

## 29. Add Two-Object Event
* The user will right click on an object already displayed on the scene
* They will then select "Edit Events"
* From this page they will select the "+" option
* From here they can select the type of Event from a dropdown
* They can then select, from a dropdown, either a Group (specified by a label in the properties page), a Type (Hero, Villain, etc), or a specific instance (hero1) with which that event occurs
* Next they will select the desired action, from a dropdown, when the event occurs
* Lastly they will select "Apply" for the event to be saved

## 30. Edit Object Size
* The user will select the desired object on the "Viewer" with a left mouse click
* They can then go to the "Properties" page in the lower right corner of the screen
* Scrolling to the "Size" section, there will be numbers that they can select and then type in new values for the width and height
    * Note: The object will snap its upper left corner to the grid so all objects are aligned

## 31. Load Past Game
* User will be able to navigate to "File" and then to "Open"
* They can then open a past game to be edited

## 32. Set Background from Offered Files
* User looks at the "Level Properties" Pane
* User left clicks on "Background"
* An Assets window will appear
* The user selects one of the provided images and then clicks apply

## 33. User Adds an Object to the "Viewer"
* User selects an object from the "Created Object Types" library
* The user then navigates their mouse over to the "Viewer"
* When they select a location, this object is placed
    * Note: The object snaps to grid lines that are not necessarily visible, but always active. Snaps to upper left corner of the grid line

## 34. User creates a new game
* When running the Authoring environment, a splash screen will appear
* Select "Create New Game" 
* A Splash Screen will appear to name and describe the game
* Once filled in, the user will click "Create" and the authoring environment will appear

## 35. User Saves Game from Authoring
* User selects "File" on the top row of tabs
* User selects "Save"
* The Game and all of its associated assets files will be saved to a folder that can be used to play or load the game later

## 36. User Closes the Authoring Environment
* User selects "File"
* User selcts "Close"
* This ends the program and closes all the windows

## 37. User Exits the Current Game
* User selects "File"
* User selects "Exit"
* This returns the user to the intro screen with options to "Create New Game" and "Load New Game"



# Game Center
## 38. User clicks read more on a game
* The game name is loaded from whatever is stored in the button
* Then, the display accesses the information on the game 
* The screen switches to a new display that shows picture, ratings (sprint 2), description, etc
* This screen should also contain a play button
* This screen will also contain a back-button
## 39. User uploads a game of their own
* They select a filepath for a folder of data files to access from
* Some error checking would be necessary to make sure the files are of the right format
* This then then becomes a game of its own and they can play it from there
## 40. User clicks play on a game
* This would open up a new window created by the GameRunner module that essentially displays the view to the user. 
* This can be closed out and return to the Game Center
* Multiple games can be opened at once
## 41. User views their own profile
* This also takes to a new screen that contains their information. 
* For the first sprint, this will just be a simple profile with a picture of the user and a description of them. 
* In later sprints, this may also include games played, ratings, etc. 
## 42. User goes to settings
* This takes the user to a new page that shows all of their settings
* This includes keybindings, profile settings, and may eventually become something like changing passwords

## 43. Game ends
* Game runner is updated by game engine that the game is over
* Game runner closes the window it created 
* Game center saves high score
* Game center returns to game selection

## 44. Player loses a life
* Engine updates status of sprites
* Runner displays new status of sprites
* Character disappears
* Runner updates heads up display with new stats

## 45. User chooses a game
* Game runner creates a new stage
* Game runner sends data file to game engine to create game
* Game engine creates list of active sprites and sends to game runner
* Game runner displays active sprites on stage

## 46. User presses arrow to move character to the right
* Translate right arrow into arbitrary button (ex. Button 1)
* Send to game engine that button 1 has been pressed
* Game engine handles the effects of button one
* Game engine updates states of sprites
* Game loop in game runner calls sprites.display()
* Display is updated

## 47. User starts game
* Game runner creates a new game engine from that data file
* Initial sprites are loaded from data file
* Game runner begins game loop 
* Game runner updates display as they are updated by engine

## 48. Create new Engine Component
* Requires adding a new interface which implements Component
* Create at least one subclass that implements the new interface
* Ready for use with other components 

## 49. Create new Engine Object
* Form new composition of components
* Optionally with new instances of the component interfaces
* New Engine object will integrate with project with no further code changes

## 50. Change Existing Engine Object Collision Logic
* Create new implementation of Collision Component interface
* Replace old Collision Component with new Collision Component in Engine Object
* Engine Object now has new collision logic, no further code changes elsewhere
* This new type of Collision implementation can also be used for other Engine Objects
 
 
 
# Miscellaneous
## 51. User uses cheat-keys to test the game
## 52. User adds a new level to the game
## 53. User changes the ordering of the levels
## 54. User creates a new playable character and the GUI automatically inputs engine.external.events
## 55. User creates an enemy and their inputs are not populated since it is not controlled by the user
## 56. User tries to create a room that is smaller than the view
## 57. User moves their character towards the edge of the view and the view scrolls to keep the character in the center
## 58. User tries to make the window smaller by dragging from the corner
## 59. The user reaches the end of the room and stops, the view stops scrolling
## 60. A character uses a power up and the power-up and its effects has to be displayed on the front-end.
## 61. A user clicks on the right arrow and the character moves to the right.
## 62. A user pauses the game and the game pauses to display a pause screen.
## 63. A user tries to use a cheat key without cheat key priveleges.
 
 
# Sprint 2
## 1. User creates a game with auto-scrolling
## 2. User plays the game with wii remote
## 3. User defines the translation from wii remote to keyboard 
## 4. User decides what controls bind to
## 5. Share scores on social media 
## 6. Display score analytics for a user game 



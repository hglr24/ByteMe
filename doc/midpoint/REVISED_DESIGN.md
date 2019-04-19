REVISED_DESIGN
===
## Introduction
Gamers are restricted by creators in regards to the environment, progression, and difficulty of the games they play. We wish to create an environment in which a user can not only design the aesthetics of the game they play, but also the objective and purpose of the game itself. With this goal in mind, our aim is to offer gamers the flexibility to create any world they can imagine. 

We have ultimately chosen to let users create and play within a Scrolling Platformer game environment. The platforming genre centers around player-controlled characters who navigate across suspended platforms, often encountering items like obstacles and power-ups along a traversal of levels until the win/lose condition(s) is(are) met. Given the seemingly unlimited number of game elements, each with a specified appearance and behavior, that a user could come across in a platformer, we wish to allow as much freedom in user choice as we can through a flexible design. By abstracting the elements of a platformer as well as the possible events and actions surrounding them in our authoring environment, we offer users a great degree of liberty in the games that they would like to create.

## Overview

![Revised Module Dependencies](https://i.imgur.com/wVdYecB.png)
(The arrows above indicate Dependencies. eg Launcher is dependent on Authoring, Center, and Data)

Our program is divided up into 6 modules: 
* Launcher
* Authoring Environment
* Data
* Engine
* Game Runner
* Game Center

The Launcher is where the user can sign in to their account and will be able to access the authoring environment or the game center.  We thought this would be a valuable addition to our project because it would allow users to transition from the authoring environment to the game center without having to start a new program.

The authoring environment is where the user can create and customize platformer games. The authoring environment does not provide anything in its API, but requires save() and load() from the Data API so that the user can save created games and their associated information and modify previously created games. The authoring environment allows for customization of different Entities (objects of the game) through a user interface the allows the user to navigate the different available options and set parameters and values associated with each Entity. The Data module provides various save() and load() in its API, which are used by the authoring environment to save and load games, the Game Center to save and load user profiles, and to load when running games. Data provides the serialization functionality needed for saving and loading different information that needs to be stored including games, user profiles, ratings and other metrics associated with a game or user. The Engine is initialized by the initialize() method in the Engine API and is passed the intial state of the game to be run. In the game loop the Engine.updateState(input) method is called and passed the user inputs (buttons pressed). The Engine will update the states of all the Entities based on the game rules and logic and return the new state object containing the changed Entities to the Game Runner to display the updated state. The Game Runner provides run() in its API, which is called by the Game Center when a user chooses to run a game. The Game Runner uses the Engine API to initialize the game engine and to update the state of the game in the game loop with updateState(). The Game Runner can graphically display any information that it chooses to use from the Engine. This allows for separation of Engine and the graphical display of the game. The Game Center uses the run() method in the GameRunner API when a user selects a game to play. Since Game Center is the area from which the user can run a game, view recorded high scores and game ratings, the Game Center also uses save() and load() from the Data API to store the files with the information and game data created by the user. 

## User Interface
This section describes what components the user will interact with in your program (keep it simple to start) and how a game is represented to the designer and what support is provided to make it easy to create a game. Include design goals for the implementation of the UI that shows the kind of abstractions you plan to build on top of OpenJFX's standard components and how they can be flexibily exchanged within the GUI itself. Finally, describe any erroneous situations that are reported to the user (i.e., bad input data, empty data, etc.).
![UI](https://i.imgur.com/TpQWlDK.jpg)

### Authoring Enviroment GUI Layout

![](https://i.imgur.com/xSVkV4f.png)

![](https://i.imgur.com/bSemt45.png)

The images above are of the wireframe of our intended Authoring Environment GUI. This wireframe will be included in our git repository.

Our Authoring Environment GUI will have a main "Viewer" where the player can place game objects that create the level. On the right, there is a "Create New Type" dropdown page that contains some default starter Entities that the user can base their own object types off of. The objects in this page cannot be directly placed but can only be built off of. When selected, a pop-up window will appear where the user can create an actual object type. This will then show up in the "Created Object Types" window further to the right. These are object types created by the user that can be directly placed onto the "Viewer" once selected.

Below the "Viewer" and the library panes, there are Level and Object Properties. These will populate with the information respective to the current level and object selected. There will be areas the user can edit such as size, level background, etc. In "Object Properties," the user can also select "Set Events" where a new window will open, allowing the user to set actions and events for the selected object.

Along the top of the screen, there is a tool bar with a "File", "Edit", and "View" tab. "File" allows for options such as new, open, save, close, exit. "Edit" will allow for options such as "Assets" and "Preferences". On the far right, "Test" will open the game and allow the author to test-run it.

### Game Center GUI Layout
This will be structured as a main screen that lists off multiple games. Each game will contain a play button, which will open up a new window of that specific game to play. Additionally, each game will have a read more section with user ratings (sprint 2). In the top right, there will be a user profile section where users can view settings, their profile, and log in/out of the center (sprint 2). A simple wireframe of this can be found [here](https://meganphibbons.wixsite.com/wireframeplayer).
Here is an up-to-date screenshot of the Game Center as of the end of Basic.
![](https://i.imgur.com/45fMmk4.jpg)


### Game Runner GUI Layout
The game runner will be different for each game as it will show the game that the game author creates.


## Design Details
*This section describes each module introduced in the Overview in detail (as well as any other sub-modules that may be needed but are not significant to include in a high-level description of the program). Describe how each module handles specific features given in the assignment specification, what resources it might use, how it collaborates with other modules, and how each could be extended to include additional requirements (from the assignment specification or discussed by your team). Look for opportunities to share APIs between your sub-teams (e.g., authoring and player front ends or authoring and engine back ends) that may themselves be separate modules (like Java and OpenJFX are composed of several modules). Note, each sub-team should have its own authoring.center.data.engine.runner.launcher.external and authoring.center.data.engine.runner.launcher.internal APIs. Finally, justify the decision to create each module with respect to the design's key goals, principles, and abstractions.*

### Modules
* **Authoring**
    * The authoring module is in charge of the authoring environment providing a GUI for the user to drag and drop entities into the game they are creating as well as attaching components to the entities and setting the values of the entities.

* **Engine**
    * The engine handles the mechanics of the game including actions and events that can occur dynamically due to user input. This module will establish a common language between authoring as well in order to implement what the authoring environment allows for its creators. The game runner relies on the engine in order to figure out what should be rendered on each update cycle of the game loop.

* **Data**
    * The data module is in charge of encapsulating all the interaction with game data. Ultimately, the data module will connect to a database that stores all the saved game and user information allowing for more sophisticated user and game experiences involving networking. 

* **GameCenter**
    * This module is for managing the game center where the user can choose games to play. This is split up into a MainCenter class which contains instances of GameCard objects. Each GameCard object will be unique to the game and contain PlayGame buttons. When these buttons are pressed, the datapath associated with the button pressed is loaded into a GameRunner object. This module requires the GameRunner, because when users press play, the runner opens up into a new window. Additionally, it requires Data, because it needs to process the information it is given in order to pass it on to Runner. 
* **GameRunner**
  *  The game runner module is essentially the front end of the game the user creates. It creates the user interface that displays the game and allows the user to interact with and play the game. GameRunner will need to load the XML files from data and turn those files into objects for a playable game. It will then create a game engine with those objects and from that point forward its job is to simply display the game. The engine will handle the mechanics of the game and how objects move around and interact with eachother. Game runner will get the states of those objects and display the objects on the screen while running the game loop. It will continue to run the game loop until prompted by the engine that the game is over.


### External APIs
* **Launcher**
    * Provides: nothing
    * Requires:
        * Authoring - launch
        * GameCenter - launch
* **Authoring**
    * Provides: nothing
    * Requires:
        * Data - save/load
        * GameRunner - run
        * Engine - Entity, Component, event, action
* **Engine**
    * Provides: 
        * `Collection<Entity> updateState (Collection<KeyCode> inputs)`
            * Call expected to be made by GameRunner. Accepts a Collection of KeyCode inputs received from GameRunner on the front end, and invokes all Systems one by one to update Entities' status and execute Events
            * @param inputs collection of user Keycode inputs received on this game loop
            * @return all game Entities after being updated by Systems in current game loop
        * `Collection<Entity> getEntities (Collection<KeyCode> inputs)`
            * @return an unmodifiable collection of Entities within the currently running game
        * Level, Entity and Component classes 
    * Requires:
        * GameRunner - frontend user input (Collection of KeyCode indicating button/key pressed) passed in by the updateState() call
* **Data**
    * Provides: 
        * save/load
    * Requires: nothing
* **GameCenter**
    * Provides: nothing
    * Requires: 
        * GameRunner - run
        * Data - save/load (for user profiles)
* **GameRunner**
    * Provides:
        * run
    * Requires:
        * Engine - updateState(input), initialize
        * Data - save/load

## Example Games
Describe three example games from your genre in detail that differ significantly. Clearly identify how the functional differences in these games is supported by your design and enabled by your authoring environment. Use these examples to help clarify the abstractions in your design.  

**Example One: Super Mario Bros.**
*Overview*
* Relatively simple, primarily horizontal scrolling
* Every object interacts on a collision->reaction basis
* Camera follows player character around game room
* Goal is to get to the rightmost side of the screen  

*Operation*
*For the sake of these examples, "player_character," "goomba," "powerup_box," "mushroom," "floor," and "flagpole" are established object types defined by the user in the authoring environment.*
* On key input -> move left or right, jump by modifying xspeed, yspeed of "player_character" object.
    * ```Input of 'D'``` Event of "player_character" triggers ```Set My X Speed to 50``` Action every frame until released.
* Collide with "goomba" -> if "player_character" is above "goomba" on collision, destroy goomba and add to score
    * ```Collision with "goomba" on Bottom``` is an Event that triggers ```Set Score to Score+100``` Action and ```Destroy Nearest Instance of "goomba"``` Action.
    * Alternatively, ```Collision with "goomba" from Left``` is another Event that triggers ```Reset Current Level``` Action and ```Set Lives to Lives-1``` Action.
* "player_character" collides with power-up box from below -> create "mushroom", set block state to indicate "broken" i.e. cannot be hit again for another mushroom
    * ```Collision with "powerup_box" on Top``` is an Event in "player_character" that triggers ```Set My Y Speed to 0```.
    *  ```Collision with "player_character" on Bottom``` is an Event of "powerup_box" that triggers ```Create Instance Of "mushroom"``` at instance location and ```Set Variable isBroken to 1``` only if allotted conditional modifier ```isBroken equals 0``` is true.
* "player_character" collides with mushroom, grows in size
    * ```Collision with "mushroom" on Any``` Event of "player_character" triggers ```Set Image to "sprite_big.png"``` and ```Set Variable "state" to 1```.
* "player_character" touches floor, stops falling, then jumps
    * ```Collision with "floor" on Bottom``` Event of "player_character" triggers ```Set My Gravity to 0``` to disable falling and ```Set My Y Position to ypos-1``` to disengage from collision (otherwise Y Speed would be infinitely 0 as soon as "player_character" touches "floor").
    *  ```Input of "Space"``` Event in "player_character" triggers ```Set My Gravity to 10``` and ```Set My Y Speed to 50```, which sends "player_character" upward with an initial impulse and continues vertically until Gravity decreases My Y Speed to a negative value and "player_character" falls back down.
* "player_character" touches flagpole, progresses to next level
    * ```Collision with "flagpole" on Any``` Event of "player_character" triggers ```Go To Next Level``` Action, player lives and score counts have their values retained between levels

**Example Two: VVVVVV** 
*Overview*
* Game consists of jumping, alternating gravity, and avoiding enemies to stay alive, all of which our design accounts for.

*Operation*
*For the sake of these examples, "player_character," "enemy," "boundary," and "floor" are established object types defined by the user in the authoring environment.*
* "player_character" touches floor, stops falling, then jumps
    * See example in Super Mario Bros.
* "player_character" touches touches checkpoint, then touches enemy in a different room, returns to a checkpoint
    * ```Collision with "checkpoint" from Any``` Event triggers ```Set Checkpoint``` Action which stores current game state in a passive game data object which also stores data like lives and score between levels. 
    * ```Collision with "boundary" from Any``` Event in "player_character" triggers ```Go To Next Level``` Action.
    * Once in next level, "player_character" hits an enemy and dies, reverts to saved checkpoint:
        * ```Collision with "enemy" from Any``` Event in "player_character" triggers ```Set Lives to Lives-1``` and ```Restore Checkpoint``` which reverts game state to what was saved, minus the contents of passive game data object.
* "player_character" jumps to invert gravity
    * ```Input of 'Space'``` Event in "player_character" triggers ```Set My Gravity to -10``` Action which sets Gravity of "player_character" to negative and ```Set Image to 'upside_down.png'``` which is a flipped version of the original sprite.

**Example Two: Celeste**
*Overview*
* Some vertical and some horizontal scrolling
* Checkpoints on each screen
* launcher.Main mechanics that differ from Super Mario Bros. and VVVVVV are wall-clinging and dashing

*Operation*
*For the sake of these examples, "player_character" and "wall" are established object types defined by the user in the authoring environment.*
* "player_character" touches a "wall" and clings to it
    * ```Collision with "wall" from Any``` Event in "player_character" triggers ```Set My Gravity to 0``` and ```Set My X Speed to 0``` Actions. While the character is clinging to the wall, this Event is constantly triggered every frame until another Event triggers an Action that sets X Speed to something nonzero and moves player_character slightly so that it does not touch the wall anymore, creating a wall-jump effect.
* "player_character" is in mid-air and presses a button to dash right
    * (Assuming original X Speed was 20) ```Input of 'Shift'``` Event in "player_character" triggers ```Set X Speed to 50``` Action and ```Reset Timer 1``` Action.
    * ```Timer 1 reaches 0.5``` Event in "player_character" triggers ```Set X Speed to 20``` Action


## Design Considerations
### 1. How do rules get to Engine?
#### Assumption:
Authoring and Engine agree on the definition of an Entity (as a basic element in the game) and import the same Component classes defining properties that could be possessed by an Entity.
When a game is designed, Authoring sets up Components of Entities with basic parameters (e.g. x/y positions) and defines the behaviors to perform when certain conditions are met (e.g. collision) via the Event class, and packages all Entities and Events into a Level object to be saved and loaded by Engine; Engine retrieves the Entities and Events, runs the general Systems for controlling and updating game status, and invokes each Event to check the conditions it's tied to and perform actions when conditions are met.


### 2. How does Data communicate with Runner?
Data will turn the game that is created in authoring into XML files via serialization. The data team plans to split up the necessary information into files that are necessary just for the runner (background color, music, etc) anmd files that are necessary to pass to the game engine. This file will have  list of byte streams and would contain all the information necessary to create a game object. Runner will take these game objects and create a game engine with these objects and then display the updated objects as the game is run.  

### 3. How does Data know what to save from Authoring?
The Authoring Environment will call saveGame() on the Data Module. It will pass in a String of the name of the game as well as a list of Game Objects. These will be saved to XML files and into a new folder, created by Data, that is titled the game's name and contains all necessary files for the game. This game can then be loaded by the Runner through passing the name of the game.

### 3. Engine Class Architecture (Composition Over Inheritance)
The Engine should handle a lot of different game pieces, which are likely to have some degree of similarity. One could create a traditional inheritance tree, and attempt to box similar game objects together. However, it was discovered that it was quite difficult to group objects into such a tree without having duplicated code for at least some functionality. One would often run into the diamond inheritance problem as well. 

Instead, a component approach was taken. In this approach, basic foundational components are constructed, and game objects are formed from different combinations of these components. This greatly increases the reusability of code, and allows for much more flexibility in object creation. This also avoids the creation of a very deep and convoluted inheritance tree. 
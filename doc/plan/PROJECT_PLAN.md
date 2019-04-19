Project Plan
===
### Roles
* **Game Data**
    * Ryan
        * A game will need to be represented in very different forms throughout the game. It must be represented as a simple view in the game center, a set of rules for the engine, and a mangeable display in the runner. Ryan's primary role will be to manage communicating information to the engine and runner via serialization, along with presenting the game center with an encapsulated set of information to display a brief summary/statistics associated with a specific game. Secondary role will be working on the front end of Game Center and working with other modules to make sure their classes are able to be serialized.
    * Anna
        * The data files from authoring need to be stored in such a manner that they can be easily accessed and changed according to user preference from the perspective of the authoring environment and then saved into a format such that complex abstractions, such as characters and engine.external.actions, can be emulated in the engine and runner. Anna's primary role will be to manage the data transfer to and from the data module and the authoring environment. Secondary role will be to update files when they are altered in the game center (such as a name change, or a reset)
* **Game Authoring Environment**
    * Harry
        * An object will need to define its behaviors relative to other objects in the authoring environment and can do so either by being a part of an aggregate group that responds similarly to the same object or can also define specific responses for itself. This will also include specifying engine.external.events that occur when a particular action (either between two objectsor as a consequence of some logic) happens. 
    * Carrie
        * Our authoring environment will allow the user to drag and drop objects into a scrollable canvas to design the level architecture, place characters (such as enemies), checkpoints, and other game objects the user wishes to interact with. There will be some default options and a way a user may add new elements.
    * Anna
        * A user must be able to load and store game files in order to edit and test current games, respectively. This information will need to be packaged in such a manner that duplicate graphics will be able to appear in the Runner environment and respond to appropriate logic in the engine.
* **Game Player (game center & game runner)**
  * Megan: Game Center
    * Game center is essentially the user interface that is used to select a game. At a very basic level, it will contain all the possible games that the user can choose from. This is the core feature to be implemented in the first sprint. Further extensions that we have considered implementing are game ratings, game high scores, allowing the user to login with a username and password, and potentially even sharing scores via social media. Megan's secondary role will be to assist Louis with game runner. 
  * Louis: Game Runner
    * Game Runner is essentially the front end component of the game that the user plays and interacts with. It relies on engine to control the mechanics of the game and displays whatver is output by engine. In the first sprint this will involve getting a simple runner working so that the user can view and play a simple game they have created. In the second sprint some further extensions that will be added are a heads up display, the ability to open and polay multiple game windows, and the ability to save specific points in the game that can be loaded later. Louis' secondary role will be to assist Megan with game center.
* **Game Engine**
    - All Engine Tasks
        - Engine Object Interface
        - Engine Components Interface
    - Lucas
         - Authoring Back-end/Architecture
         - Authoring Events/Actions Internal API
    - Hsing
         - Engine Managers
         - Entity-ID Handling
    - Dima
         - Authoring Back-end/Architecture
         - Authoring Events/Actions Internal API
    - Feroze
        - Engine-Runner API
        - Engine Managers
    
        


## General Sprint 1 Goals
* GameCenter
    * Basic game chooser
        * Includes listing multiple games
    * Basic user profile
    * Launch GameRunner 
    * Interact with Data
* Data
    * Use XStream to store GameObjects
    * Communicate basic interactions and displays of objects to engine
    * Be able to reload a previous game into authoring to edit
* Authoring Environment
    * able to create a reusable object, such as a power block
    * Users can drag and drop onto a scrollable page
    * Upload a new image
    * Ability to respond to basic engine.external.events
    * Save a created game that can then be run by runner
    * Upload a game to edit 
* Game Runner
* Engine

## General Sprint 2 Goals
* GameCenter
    * Save usernames/passwords
    * Load a game from a previous savepoint
    * Changing keybindings to allow for different controller types
* Data
    * Store multiple users information and records
    * Post users' new scores to social media
    * Use statistical analysis tools to track user's abilities over time
* Authoring Environment
    * Audio Files
    * Ability to display an interaction between two objects when an event occurs
    * Ability to define behavior according to state of objects not involved in the interaction (sometimes the background or orientation of a game affects how an enemy hurts the user or whether that enemy is capable of hurting the user at all, not sure if we ever will get here, its a bit ambitious)
* Game Runner
* Engine

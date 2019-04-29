README
===

### Team Members: 
Ryan Culhane
Anna Darwish
Dima Fayyad
Carrie Hunner
Louis Jensen
Lucas Liu
Feroze Mohideen
Megan Phibbons
Harry Ross
Hsingchih Tang

### Start Date: March 21 2019
### End Date: April 29 2019
### Estimated number of hours working:
* Full Team Meetings: 45 hours 
* Individual without Team Meetings:
    * Dima Fayyad: ~100 hours
    * Lucas Liu: 164 hours
    * Feroze Mohideen: 85 hours
    * Hsingchih Tang: ~100 hours
    * Anna Darwish: 110 hours
    * Harry Ross: 125 hours
    * Louis Jensen: 
    * Megan Phibbons: ~100 hours
    * Ryan Culhane: ~120 hours
    * Carrie: 144 hours


### Roles:
* Engine
    * Dima Fayyad
        * Backend engine (Events, Actions, Conditions)
    * Lucas Liu
        * Entity-Component-System
        * Backend engine
    * Feroze Mohideen
        * Overall design of Components
        * Structuring of Event/Action/Conditions
    * Hsingchih Tang
        * Level, Engine and backend Systems
        * Setting up junit and reflection utility modules

* Authoring
    * Anna Darwish
        * Setting up modules and xstream
        * Launcher for opening authoring environment and game center
        * Generating events in authoring
    * Carrie Hunner
        * Authoring Viewer
        * Default Entity types
        * User created types
        * Saving/Loading Assets from database
    * Harry Ross
        * Authoring backend
        * UI, CSS, property binding, export
    
* Game Runner
    * Louis Jensen
        * Front end of game runner
        * Running game loop
* Game Center
    * Megan Phibbons
        * Game Center Development
        * Create GameCenter main UI
        * Display each game, game ratings, game scores, and User pages
        * Allow users to add a new rating
        * Allow users to refresh GameCenter
        * Allow users to set their own images and bios

* Data
    * Ryan Culhane
        * Data management
        * Saving and loading games;
        * Setting up database

### Resources:
* Online Resources:
    * [Game Programming Patterns](http://gameprogrammingpatterns.com/contents.html)
    * [Introduction to Component Based Architecture in Games](https://www.raywenderlich.com/2806-introduction-to-component-based-architecture-in-games)
    * [Entities, Components, and Systems](https://medium.com/ingeniouslysimple/entities-components-and-systems-89c31464240d)
    * [StackExchange: pattern for collision handling](https://gamedev.stackexchange.com/questions/137344/pattern-for-collision-handling)
    * [StackExchange: entity-system questions](https://gamedev.stackexchange.com/questions/tagged/entity-system?sort=votes&pageSize=15)
    * [StackExchange: systems in component-based entity architecture](https://gamedev.stackexchange.com/questions/31473/what-is-the-role-of-systems-in-a-component-based-entity-architecture)
    * [JavaFX Documentations](https://docs.oracle.com/javafx/2/api/overview-summary.html)
* Human Resources:
    * CompSci 308 TA:
        * Benjamin Xu
        * Christina Li
        * Matthew O’Boyle
        * Claire Fu
    * Professor Duvall


### Files used to start the project
The main class to run this project is the JARLauncher in the Launcher module. This class connects and calls all of the main classes. On running, it provides the option to log in and then either Create or Play a game. Selecting "Create" will launch the Authoring Environment and allow the user to create or load a game associated with their username. Selecting "Play" will launch the Game Center which displays games as well as information about them and their creator.


### Files used to test the project
* Testing game behaviours in engine and runner: runner/src/runner/internal/DummyGameObjectMaker.java
* Engine system Junit tests: engine/src/engine/internal/EngineSystemTest
* authoring/src/ui/main/MainTester
* data/test/DataTest

### Errors you expect your program to handle without crashing
* User not creating 'complete' entities in the game. Missing components needed will be added to the entities, with default values when engine runs the game
* Game Name Uniqueness: Incorporate author name into the saving and loading of games for uniqueness
* Data files for images and sounds not found in the database: engine will skip any missing file and continue to load the rest of the game
* User enters an invalid user name and password combination.
* Game Center uses default images when the images cannot be found in the database (for both users and games)

### Data or resource files required by the project
* All resource folders in each of the modules 
    * authoring: properties files, CSS
    * engine: default images, System properties files that identify the required components for each system, the order in which the systems run, components to remove when the game is saved
    * runner: CSS, sample images
    * center: CSS, properties files, and default image files/icons found in data folder
    * launcher: CSS, default images, properties files
* Database (accessible via MySQLWorkbench)
    * Email ryan.culhane@duke.edu for permissions
* Libraries:
    * MySQL-JDBCDriver
    * junit
    * xstream
    * javafx
* Utility Module:
    * voogasalad_util Reflection module


### Any information about using the program 
*(i.e., command-line/applet arguments, key inputs, interesting example data files, or easter eggs)*
* Key inputs:
    If users have defined events and actions to trigger with certain key inputs in the authoring environment, the game will be able to react to any keyboard input in the middle of a game.
    e.g. Main character jumps when 'J' is pressed.
* User accounts:
    The database has remotely connected tables for storing user profile information (usernames, hashed password, log-in records, bio, etc.) to enable user logging-in feature.
* Example DummyGame:
    The class `runner.internal.DummyGameObjectMaker` initializes an example game with pre-defined dummy Entity objects, Events and Actions. The class defines events associated with certain conditions (key inputs, collisions, etc.) and demonstrate a number of actions that could be executed to accomplish desired effects.
    e.g. Two characters bounce off each other when collisions happen
    e.g. Play music when 'M' is pressed
* User profiles can be displayed if you press username on a game card
* After adding a rating, it is necessary to close the game popup and reopen it to see the new rating and it is necessary to refresh the game center
* When a new game is added by authoring, it is necessary to refresh the center to load in the new games

### Any decisions, assumptions, or simplifications you made to handle vague, ambiguous, or conflicting requirements
* Game Player was split into runner and game center. The game center is where the user can view all games, ratings, information and select the game to play. From there, runner allows the player to play the selected game.
* GameCenter UI primarily assumes that Data handles all of the error checking, but as part of debugging this will eventually change by setting defaults
* Generally, if the image cannot be found, then no image is displayed.

### Any known bugs, crashes, or problems with the project's functionality
* Assets are saved into the Database with the game's name and author's name appended on to the front of the image name. This allows us to save all of the images to the same table in the database. This assumes that once the user initially sets their name and sets the game, that they will not change it. If they do change the game name, the assets will still be accessible because they will still exist in the database, however they will not appear in the Asset Managers, as the assets are loading using the gameName + authorName prefix.
* Pressing Play or Read More from GameCenter is generally pretty slow
* Average game ratings are a little bit off and don't fully work (i.e. a 5 star rating contributes 6 to the rating)

### Any extra features included in the project
* User login, game ratings, and game center to house games built by all authors.
* Database: all user information, game information and asset files (images, sounds) are stored into a SQL database when users save games on the authoring end, and are loaded on the runner end for playing games.
* Game Center allows a slight social center with ratings and comments on games. Additionally, the users' icons are displayed and UserProfiles exist to add to the social center vibe. Lastly, high scores for games are displayed so that users can see how they compare to others.
* Users can order pizza from within a game using the provided Pizza Action which redirects to a window that opens up the dominos website.

### Impressions of the assignment to help improve it in the future
* Dima Fayyad: The project timeline felt a bit skewed, as the basic sprint is quite a bit shorter than the complete sprint. While I do think that this might be an incentive to reach certain goals early, I think that given more time for basic would spread the project goals more evenly across the coding sprints. 
* Lucas Liu: Enjoyed having this as a capstone project. Working in a larger team was a valuable experience. The use of git could be improved. Git issues were mostly a burden, and didn't make sense to have when creating a project from scratch. Issues would make more sense for an established project, upon which clients had particular concerns. Maybe the class should've been using user stories, and not issues.
* Feroze Mohideen: I definitely learned a lot about working on a large team and efficient design during this project. However, I wish that the grading policies were more clearly defined so that we wouldn't be spending a ton of time on things that don't matter as much. 
* Hsingchih Tang: The project itself was pretty interesting to work on and provided a great opportunity for learning new things. The timeline could be designed better though -- maybe adjust the complete deadline so that it's not due on the midnight of the last day of reading period.
* Anna Darwish: I thought this project was interesting to work on, and have really enjoyed working with the team I was lucky enough to end up with. But as my other team members have noted, it has been quite stressful. No team member expects someone to drop everything to work on this, but it feels as though that's the only way to get things accomplished. The only real way to be successful in this class, and on this project, is to decide your other classes don't matter.
* Harry Ross: Project was difficult to plan and conceptualize, but I felt like it worked out well and I'm happy with what we achieved. 
* Louis Jensen: I enjoyed the project but it was  difficult to work as such a large group when everybody has very different schedules. I thought the most challenging part was incorporating the work of each subteam together but I feel like we all learned a lot.
* Megan Phibbons: This was a very helpful project in learning how to effectively work with other groups. However, I almost wish it was due earlier on (just a week or so) so that I can have some time to study for finals as well as work on 308. This is a very time-intensive project and I think even if it was due earlier in reading period, it wold help with time-management some. Overall, though, this project definitely helped me grow as a computer science student and I'm glad that I could take the time to benefit from the workload.
* Ryan Culhane: I can already tell the analysis is going to be very stressful seeing as it is over finals week.  I think it would be better to have the maintenance component of analysis
* Carrie: The timing of this project is extremely stressful. I have three other finals that I have been unable to study for due to the time demand of this class. Having the code due the night reading period ends and then also having the analysis hanging over us during finals week is a lot. Additionally, jumping from a team of four to a team of ten was a major shift. I think this size team was good for the scale of the project, but it was a big change. Lastly, it seems like it could be better to have the maintenance component earlier in the semester, or realeased earlier.
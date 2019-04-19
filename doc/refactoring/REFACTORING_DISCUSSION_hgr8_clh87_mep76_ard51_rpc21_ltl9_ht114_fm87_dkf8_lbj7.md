REFACTORING_DISCUSSION
===
# Engine
* Casting events and actions as serializable
* Removed unused imports
* Implementing reflection utility module for reflection calls
* Implementing exception/error handling when encountering FileNotFoundExceptions or ReflectionExceptions
# Authoring
* Create control-specific CSS options instead of hardcoding particular values and settings into the code
* Separate out reflection for different events to shorten longer method and allow for more flexibility in editting these methods
* Remove all unused imports and hard-coded Strings for properties files or keys
* Refactor events to remove dependency on ordering of StringProperty values obtained from the pop-up pane
# Center
* Remove unused constants and import statements 
    * These were relatively new due to switching from inline styling to CSS, so there wasn't really any debate of whether or not I should keep them
* Remove wildcard imports
    * This was an unnecessary wildcard import because it was from a package not created by our team and I only needed a few of the classes. 
    * I instead split this up into individual import statments.
* Error handling - this is still on my to-do list.
    * Some of the issues that come from exception throwing are because I am intentionally doing nothing, for example in the GameCard class. Here, I am doing nothing because the card will simply not have any image displayed
    * In others, I need to add in error message display so that the users will know what they did incorrectly. 
# Runner
* Remove unecessary import statements and commented out code
* Pull out pieces of Runner class into smaller single responsibility classes
* Remove hard coded strings and use properties file
* Add error checking for game creation
# Data
* Remove hard-coded Strings for SQL queries
* Remove unused import statements
* Get rid of empty methods/deprecate ones that were used for saving and loading games prior to moving to a database system
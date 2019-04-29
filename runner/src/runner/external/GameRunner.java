package runner.external;

import data.external.DataManager;
import engine.external.Level;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import runner.internal.DummyGameObjectMaker;
import runner.internal.LevelRunner;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Primary class that runs the game
 * Gets game object from database and plays it
 * @author Louis Jensen
 */
public class GameRunner {
    private int mySceneWidth;
    private int mySceneHeight;
    private List<Level> myLevels;
    private Game myGame;
    private Stage myGameStage;
    private String myGameName;
    private String myAuthorName;
    private DataManager myDataManager;

    /**
     * Constructor for GameRunner
     * @param gameName - name of the game
     * @param authorName - name of the game's author
     * @throws FileNotFoundException if game is not found
     */
    public GameRunner(String gameName, String authorName, String username) throws FileNotFoundException {
        //TODO Change defualt author to actual author
        //String authorName = "DefaultAuthor";
        myGame = loadGameObject(gameName, authorName);
        myGameName = gameName;
        myAuthorName = authorName;
        myGameStage = new Stage();
        int firstLevel = 1;
        runLevel(firstLevel);
    }

    private Game loadGameObject(String gameName, String authorName){
//        DummyGameObjectMaker dm2 = new DummyGameObjectMaker();
//        Game gameMade = dm2.getGame(gameName);
        myDataManager = new DataManager();
//        dm.saveGameData(gameName, authorName,gameMade);
//        System.out.println("Serialization complete");

        try {
            return (Game) myDataManager.loadGameData(gameName, authorName);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void runLevel(int currentLevelNumber){
        DataManager dm = new DataManager();
        //dm.saveGameData(myGameName, myAuthorName, myGame);
        Game gameToPlay;
        try {
            gameToPlay = (Game) dm.loadGameData(myGameName, myAuthorName);
        } catch(SQLException e){
            gameToPlay = myGame;
        }
        Level currentLevel = gameToPlay.getLevels().get(currentLevelNumber - 1);
        mySceneWidth = myGame.getWidth();
        mySceneHeight = myGame.getHeight();
        Consumer<Double> goToNext = (level) -> {
            nextLevel(level);
        };
        Image background;
        try {
           background = new Image(myDataManager.loadImage(currentLevel.getBackground()), currentLevel.getWidth(), currentLevel.getHeight(), false, false);
        } catch (Exception e){
            background = new Image(myDataManager.loadImage("byteme_default_runnerBackground"), currentLevel.getWidth(), currentLevel.getHeight(), false, false);
           // background = new Image(myDataManager.loadImage("byteme_default_runnerBackground"), currentLevel.getWidth(), currentLevel.getHeight(), false, false);
        }

        new LevelRunner(currentLevel, mySceneWidth, mySceneHeight, myGameStage,
                goToNext, gameToPlay.getLevels().size(), background);
    }

    private void nextLevel(Double level) {
        int levelToPlay;
        try{
            levelToPlay = level.intValue();
        } catch (Exception e){
            levelToPlay = 10;
        }
        System.out.println("progressing to level " + levelToPlay);
        this.runLevel(levelToPlay);
    }

}
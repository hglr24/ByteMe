package runner.external;

import data.external.DataManager;
import engine.external.Level;
import javafx.stage.Stage;
import runner.internal.DummyGameObjectMaker;
import runner.internal.LevelRunner;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class GameRunner {
    /**
     * This will be the primary class that creates a new game engine
     * and displays sprites on a stage
     */
    private int mySceneWidth;
    private int mySceneHeight;
    private List<Level> myLevels;
    private Game myGame;
    private Stage myGameStage;

    public GameRunner(String game) throws FileNotFoundException {
        myGame = loadGameObject(game);
        myGameStage = new Stage();
        Level levelOne = myGame.getLevels().get(0);
        int firstLevel = 1;
        runLevel(firstLevel);
    }

    private Game loadGameObject(String path){
        DummyGameObjectMaker dm2 = new DummyGameObjectMaker();
        Game gameMade = dm2.getGame(path);
        DataManager dm = new DataManager();
        dm.createGameFolder("YeetRevised2");
        dm.saveGameData("YeetRevised3", gameMade);
        System.out.println("Serialization complete");
        try {
            return (Game) dm.loadGameData("YeetRevised3");
        } catch (SQLException e) {
            return null;
        }
    }


    private void runLevel(int currentLevelNumber){
        Level currentLevel = myGame.getLevels().get(currentLevelNumber - 1);
        mySceneWidth = myGame.getWidth();
        mySceneHeight = myGame.getHeight();
        Consumer<Double> goToNext = new Consumer<Double>() {
            @Override
            public void accept(Double aDouble) {
                nextLevel(aDouble);
            }
        };
        new LevelRunner(currentLevel, mySceneWidth, mySceneHeight, myGameStage, goToNext);
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
package runner.internal;

import data.external.DatabaseEngine;
import javafx.application.Application;
import javafx.stage.Stage;
import runner.external.GameRunner;

/**
 * Creates new GameRunner object for testing game
 * @author Louis Jensen
 */
public class RunnerTester extends Application {

    /**
     * Runs application start method
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Starts application
     * @param primaryStage - Stage to create game in
     * @throws Exception if application cannot start
     */
    @Override
    public void start (Stage primaryStage) throws Exception{
        GameRunner runner = new GameRunner("YeetRevised3");
    }

    /**
     * Closes application
     * @throws Exception upon failure
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        DatabaseEngine.getInstance().close();
    }

}



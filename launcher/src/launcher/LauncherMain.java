package launcher;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import manager.SceneManager;


public class LauncherMain extends Application {
    private static final double STAGE_SIZE = 400;
    private static final Color BACKGROUND_COLOR = Color.LIGHTPINK;

    @Override
    public void start(Stage myStage) {
        SceneManager myScene = new SceneManager();
        myScene.render(myStage);
    }

    public static void main(String[] args){
        launch(args);
    }

}



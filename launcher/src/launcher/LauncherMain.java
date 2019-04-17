package launcher;
import javafx.application.Application;
import javafx.stage.Stage;
import manager.SceneManager;

public class LauncherMain extends Application {

    @Override
    public void start(Stage myStage) {
        SceneManager myScene = new SceneManager();
        myScene.render(myStage);
    }

    public static void main(String[] args){
        launch(args);
    }

}



package runner;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int STAGE_SIZE = 200;
    private static final Color BACKGROUND_COLOR = Color.LIGHTPINK;

    public static void main(String[] args){
        launch(args);
    }
    public void start (Stage myStage) throws Exception {
        Group myNode = new Group();
        Scene myScene = new Scene(myNode,STAGE_SIZE,STAGE_SIZE,BACKGROUND_COLOR);
        myStage.setScene(myScene);
        myStage.show();
    }
}

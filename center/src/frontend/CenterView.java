package frontend;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import frontend.games.GamePane;
import frontend.header.HeaderBar;

public class CenterView extends Application {
    private Scene myScene;

    private static final double STAGE_WIDTH = 1250;
    private static final double STAGE_HEIGHT = 750;
    private static final Color BACKGROUND_COLOR = Color.rgb(46, 43, 51);

    public void start (Stage myStage) throws Exception {
        CenterView view = new CenterView();
        myStage.setScene(view.getScene());
        myStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

    public CenterView() {
        initializeScene();
    }

    public Scene getScene() {
        return myScene;
    }

    private void initializeScene() {
        BorderPane root = new BorderPane();
        HeaderBar myHeader = new HeaderBar();
        Pane layout = myHeader.getHeaderLayout();
        BorderPane.setAlignment(layout, Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: transparent;");
        layout.setStyle("-fx-background-color: transparent;");
        root.setTop(layout);
        root.setCenter((new GamePane()).getDisplay());
        myScene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT, BACKGROUND_COLOR);
    }

}

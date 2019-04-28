package runner;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameBeatenScreen {
    private Stage myStage;
    private VBox myNode;

    public GameBeatenScreen(Stage stage, Double translatedX) {
        myStage = stage;
        Button exit = new Button("Exit Game");
        exit.setOnMouseClicked(event ->{
            myStage.close();
        });
        exit.setId("button");
        Label congrats = new Label("Congratulations! \n you have completed \n the game");
        congrats.setId("text");
        myNode = new VBox(8);
        myNode.setId("PauseMenu");
        myNode.setLayoutX(180 - translatedX);
        myNode.setLayoutY(180);
        myNode.getChildren().addAll(congrats, exit);
    }

    public VBox getNode(){
        return myNode;
    }

}

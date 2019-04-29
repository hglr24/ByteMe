package runner.internal;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameBeatenScreen {
    private Stage myStage;
    private VBox myNode;

    public GameBeatenScreen(Stage stage, Double translatedX, Boolean win) {
        myStage = stage;
        Button exit = new Button("Exit Game");
        exit.setOnMouseClicked(event ->{
            myStage.close();
        });
        exit.setId("button");
        String text;
        if (win){
            text = "Congratulations! \n you have completed \n the game";
        } else {
            text = "You have lost the game \n good luck next time!";
        }
        Label congrats = new Label(text);
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

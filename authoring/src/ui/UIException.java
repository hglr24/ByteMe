package ui;

import events.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Harry Ross
 */
public class UIException extends Exception {
    private String errorMessage;
    public UIException(String message) {
        super(message);
        errorMessage = message;
    }

    public void displayUIException(){
        Stage popUpWindow = new Stage();
        VBox popUpSceneRoot = new VBox();
        popUpSceneRoot.getChildren().add(new Label(errorMessage));
        Scene popUpScene  = new Scene(popUpSceneRoot);
        popUpScene.getStylesheets().add("default.css");
        popUpWindow.setScene(popUpScene);
        popUpWindow.show();

    }


}

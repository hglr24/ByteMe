package ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Harry Ross
 */
public class UIException extends Exception {
    private String errorMessage;
    private static final String EVENT_ERROR = "Event";
    public UIException(String message) {
        super(message);
        errorMessage = message;
    }

    public void displayUIException(){
        ErrorBox myError = new ErrorBox(EVENT_ERROR,errorMessage);
        myError.display();
    }


}

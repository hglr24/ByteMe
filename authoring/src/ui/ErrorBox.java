package ui;

import javafx.scene.control.Alert;

/**
 * Class for instantiating an JavaFX error-type Alert box as a condensed object
 * @author Harry Ross
 */
public class ErrorBox extends Alert {

    public static final String DEFAULT_STYLE = "default.css";

    /**
     * Creates an ErrorBox with given type and message
     * @param type Type of error (displayed in box header)
     * @param message Message to display in box
     */
    public ErrorBox(String type, String message) {
        super(AlertType.ERROR);
        this.setTitle("Error");
        this.setHeaderText(type);
        this.setContentText(message);
        this.getDialogPane().getStylesheets().add(DEFAULT_STYLE);
    }

    /**
     * Displays ErrorBox until closed by user
     */
    public void display() {
        this.showAndWait();
    }
}

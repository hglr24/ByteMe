package popup;

import javafx.scene.control.Alert;

public class ErrorPopUp extends Alert {
    private static final String ERROR = "Error";
    private static final String STYLE = "default_launcher.css";

    public ErrorPopUp(String header, String message){
        super(AlertType.ERROR);
        this.setTitle(ERROR);
        this.setHeaderText(header);
        this.setContentText(message);
        this.getDialogPane().getStylesheets().add(STYLE);
    }
    /**
     * Displays ErrorBox until closed by user
     */
    public void display() {
        this.showAndWait();
    }

}

package pane;

import controls.TitleLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class WelcomeDisplay extends HBox {
    private static final String MY_STYLE = "default_launcher.css";
    public WelcomeDisplay(String welcomeLabel){
        this.getStylesheets().add(MY_STYLE);
        Label myLabel = new TitleLabel(welcomeLabel);
        this.getChildren().add(myLabel);
        this.setAlignment(Pos.CENTER);
    }
    public WelcomeDisplay(String welcomeLabel, String uniqueName){
        this.getStylesheets().add(MY_STYLE);
        TitleLabel myLabel = new TitleLabel(welcomeLabel);
        myLabel.addToLabel(uniqueName);
        this.getChildren().add(myLabel);
        this.setAlignment(Pos.CENTER);
    }

}

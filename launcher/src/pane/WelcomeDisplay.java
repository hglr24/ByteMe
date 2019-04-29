package pane;

import controls.TitleLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;

public class WelcomeDisplay extends HBox {
    private static final String MY_STYLE = "default_launcher.css";
    /**
     * In the future, the welcome label in the splash page will need to have unique functionality relative to the other
     * labels throughout the launch environment, which is why it is its own class, and not another title pane.
     * @author Anna Darwish
     */
    public WelcomeDisplay(String welcomeLabel){
        Label myLabel = new TitleLabel(welcomeLabel);
        this.getChildren().add(myLabel);
        setStyle();
    }
    public WelcomeDisplay(String welcomeLabel,String modifyLabel){
        Label myLabel = new TitleLabel(welcomeLabel,modifyLabel);
        this.getChildren().add(myLabel);
        setStyle();
    }
    private void setStyle(){
        this.getStylesheets().add(MY_STYLE);
        this.setAlignment(Pos.CENTER);
    }


}

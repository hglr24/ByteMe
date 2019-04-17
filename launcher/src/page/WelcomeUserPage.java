package page;

import javafx.scene.layout.VBox;
import manager.SwitchToUserOptions;
import pane.UserOptionsDisplay;
import pane.WelcomeDisplay;

public class WelcomeUserPage extends VBox {
    private static final String MY_STYLE = "default_launcher.css";
    private static final String WELCOME_LABEL_KEY = "specific_welcome";
    /**
     * This page will prompt the user either enter the authoring environment to create games or go to the game center so
     * they can play games
     * @author Anna Darwish
     */
    public WelcomeUserPage(SwitchToUserOptions switchDisplay, SwitchToUserOptions switchToLauncher){
        this.getStyleClass().add(MY_STYLE);
        this.getChildren().add(0,new WelcomeDisplay(WELCOME_LABEL_KEY));
        this.getChildren().add(1,new UserOptionsDisplay(switchDisplay, switchToLauncher));
    }


}

package page;

import javafx.scene.layout.VBox;
import manager.SwitchToUserOptions;
import pane.UserOptionsDisplay;
import pane.WelcomeDisplay;

public class WelcomeUserPage extends VBox {
    private static final String MY_STYLE = "default_launcher.css";
    private static final String WELCOME_LABEL_KEY = "specific_welcome";

    public WelcomeUserPage(SwitchToUserOptions switchDisplay, SwitchToUserOptions switchToLauncher){
        this.getStyleClass().add(MY_STYLE);
        this.getChildren().add(0,new WelcomeDisplay(WELCOME_LABEL_KEY));
        this.getChildren().add(1,new UserOptionsDisplay(switchDisplay, switchToLauncher));
    }
    public void addUserName(String userName){
        this.getChildren().set(0, new WelcomeDisplay(WELCOME_LABEL_KEY, userName));
    }


}

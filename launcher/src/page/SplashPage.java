package page;
import javafx.scene.layout.VBox;
import manager.SwitchToUserOptions;
import pane.UserStartDisplay;
import pane.WelcomeDisplay;

public class SplashPage extends VBox{
    private static final String MY_STYLE = "default_launcher.css";
    private static final String WELCOME_LABEL_KEY = "general_welcome";
    public SplashPage(SwitchToUserOptions switchDisplay){
        this.getStyleClass().add(MY_STYLE);
        this.getChildren().add(0,new WelcomeDisplay(WELCOME_LABEL_KEY));
        this.getChildren().add(1,new UserStartDisplay(switchDisplay));
    }

}

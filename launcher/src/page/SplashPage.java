package page;
import javafx.scene.layout.VBox;
import manager.SwitchToUserOptions;
import pane.UserStartDisplay;
import pane.WelcomeDisplay;

public class SplashPage extends VBox{
    private static final String MY_STYLE = "default_launcher.css";
    private static final String WELCOME_LABEL_KEY = "general_welcome";
    /**
     * This page will prompt the user to enter their credentials so they can login to their future account
     * @author Anna Darwish
     */
    public SplashPage(SwitchToUserOptions switchDisplay){
        this.getStyleClass().add(MY_STYLE);
        this.getChildren().add(0,new WelcomeDisplay(WELCOME_LABEL_KEY));
        this.getChildren().add(1,new UserStartDisplay(switchDisplay));//@TODO add in a way for the user to create a new account as well
    }

}
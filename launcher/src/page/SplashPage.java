package page;
import controls.LauncherSymbol;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import manager.SwitchToUserOptions;
import manager.SwitchToUserPage;
import pane.UserLoginDisplay;
import pane.WelcomeDisplay;

public class SplashPage extends VBox {
    private static final String MY_STYLE = "default_launcher.css";
    private static final String WELCOME_LABEL_KEY = "general_welcome";
    private static final String START_ACTION = "new_player";

    /**
     * This page will prompt the user to enter their credentials so they can login to their future account
     *
     * @author Anna Darwish
     */

    public SplashPage(SwitchToUserOptions switchToNewUser, SwitchToUserPage switchToLoggedIn) {
        this.getStyleClass().add(MY_STYLE);
        this.getChildren().add(new WelcomeDisplay(WELCOME_LABEL_KEY));
        makeLoginOptions(switchToNewUser, switchToLoggedIn);
        //this.getChildren().add(new UserStartDisplay(switchToNewUser,switchToLoggedIn));
    }

    private void makeLoginOptions(SwitchToUserOptions switchToNewUser, SwitchToUserPage switchToLoggedIn) {
        HBox loginOptions = new HBox();
        LauncherSymbol mySymbol = new LauncherSymbol(START_ACTION, switchToNewUser);
        loginOptions.getChildren().add(mySymbol);
        loginOptions.getChildren().add(new UserLoginDisplay(switchToLoggedIn));
        loginOptions.setTranslateY(100);
        loginOptions.setAlignment(Pos.CENTER);
        loginOptions.setSpacing(100);
        this.getChildren().add(loginOptions);
    }
}

package page;

import javafx.scene.layout.VBox;
import manager.SwitchToAuthoring;
import manager.SwitchToUserOptions;
import pane.CreateGameDisplay;
import pane.WelcomeDisplay;

public class CreateNewGamePage extends VBox {
    private static final String MY_STYLE = "default_launcher.css";
    private static final String CREATE_KEY = "create_game";
    public CreateNewGamePage(SwitchToAuthoring mySwitchScene, SwitchToUserOptions switcher){
        this.getStyleClass().add(MY_STYLE);
        this.getChildren().add(0,new WelcomeDisplay(CREATE_KEY));
        this.getChildren().add(1, new CreateGameDisplay(mySwitchScene,switcher));
    }
}

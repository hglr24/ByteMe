package pane;

import controls.LauncherSymbol;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import manager.SwitchToUserOptions;

public class UserStartDisplay extends HBox {
    private static final String START_ACTION = "new_player";
    private static final String CSS_STYLE_NAME = "default_launcher.css";
    /**
     * These are the two user-interactive areas on the splash page for the user - either a way to navigate to a new
     * account page or a login option
     * here
     * @author Anna Darwish
     */
    public UserStartDisplay(SwitchToUserOptions switchPage){
        this.getStyleClass().add(CSS_STYLE_NAME);
        this.setTranslateY(100);
        this.getChildren().add(0,new LauncherSymbol(START_ACTION));
        this.getChildren().add(1, new UserLoginDisplay(switchPage));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(100);

    }
}

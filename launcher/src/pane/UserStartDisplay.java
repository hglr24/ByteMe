package pane;

import controls.LauncherSymbol;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import manager.SwitchToUserOptions;

public class UserStartDisplay extends HBox {
    private static final String START_ACTION = "new_player";
    public UserStartDisplay(SwitchToUserOptions switchPage){
        this.getStyleClass().add("default_launcher.css");
        this.setTranslateY(100);
        this.getChildren().add(0,new LauncherSymbol(START_ACTION));
        this.getChildren().add(1, new UserLoginDisplay(switchPage));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(100);

    }
}

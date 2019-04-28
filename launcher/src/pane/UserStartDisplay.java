package pane;
import controls.LauncherSymbol;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import manager.SwitchToUserOptions;
import manager.SwitchToUserPage;

public class UserStartDisplay extends HBox {
    private static final String START_ACTION = "new_player";
    private static final String CSS_STYLE_NAME = "default_launcher.css";
    /**
     * These are the two user-interactive areas on the splash page for the user - either a way to navigate to a new
     * account page or a login option
     * here
     * @author Anna Darwish
     */
    public UserStartDisplay(SwitchToUserOptions switchPage, SwitchToUserPage switchNewUser){
//        this.getStyleClass().add(CSS_STYLE_NAME);
//        this.setTranslateY(100);
//        newUserOptions(switchNewUser);
//        LauncherSymbol mySymbol = new LauncherSymbol(START_ACTION,switchNewUser);
//        this.getChildren().add(mySymbol);
//        this.getChildren().add(new UserLoginDisplay(switchPage));
//        this.setAlignment(Pos.CENTER);
//        this.setSpacing(100);

    }

    private void newUserOptions(SwitchToUserPage swichNewUser){

        this.getStyleClass().add(CSS_STYLE_NAME);
        this.setTranslateY(100);
        //LauncherSymbol mySymbol = new LauncherSymbol(START_ACTION,switchNewUser);
        //this.getChildren().add(0,mySymbol);
        //this.getChildren().add(1, new UserLoginDisplay(switchPage));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(100);


    }
}

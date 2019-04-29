package controls;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import manager.SwitchToUserOptions;

public class LogOutButton extends ImageView {
    private static final String IMAGE = "logout.jpg";
    private static final String STYLE = "log-out";

    public LogOutButton(SwitchToUserOptions logOut){
        this.setImage(new Image(IMAGE));
        this.getStyleClass().add(STYLE);
        this.setOnMouseClicked(mouseEvent -> logOut.switchPage());
    }

}
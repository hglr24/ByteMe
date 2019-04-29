package controls;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import manager.SwitchToUserOptions;

public class BackButton extends ImageView {
    private static final String IMAGE = "back_button.png";
    private static final String STYLE = "back-button";
    public BackButton(SwitchToUserOptions backPage){
        this.setImage(new Image(IMAGE));
        this.getStyleClass().add(STYLE);
        this.setOnMouseClicked(mouseEvent -> backPage.switchPage());
    }

}

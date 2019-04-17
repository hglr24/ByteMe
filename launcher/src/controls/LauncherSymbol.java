package controls;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ResourceBundle;

public class LauncherSymbol extends ImageView {
    private static final String WELCOME_RESOURCE = "launcher_display";
    private static final String DEFAULT_KEY = "default_";
    private static final String ACTIVE_KEY = "active_";

    private static final ResourceBundle myResources = ResourceBundle.getBundle(WELCOME_RESOURCE);
    private Image myDefaultImage;
    private Image myPressedImage;
    /**
     * When pressed, these LaunchSymbols switch their image to appear as active
     * @author Anna Darwish
     */
    public LauncherSymbol(String action){
       myDefaultImage = new Image(myResources.getString(DEFAULT_KEY + action));
       myPressedImage = new Image(myResources.getString(ACTIVE_KEY + action));
       this.setImage(myDefaultImage);
       this.setScaleX(1.5);
       this.setScaleY(1.5);
       this.setOnMousePressed(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               switchImage(myPressedImage);
           }
       });
        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switchImage(myDefaultImage);
            }

        });
    }

    private void switchImage(Image switchedImage){
        this.setImage(switchedImage);
    }
}

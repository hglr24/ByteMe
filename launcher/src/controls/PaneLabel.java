package controls;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class PaneLabel extends Label {
    private static final String WELCOME_RESOURCE = "launcher_display";

    public PaneLabel(String key){
        ResourceBundle myResources = ResourceBundle.getBundle(WELCOME_RESOURCE);
        this.setText(myResources.getString(key));
        this.getStylesheets().add("default.css");

    }


}

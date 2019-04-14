package controls;

import javafx.scene.control.Label;

import java.util.ResourceBundle;

public class PaneLabel extends Label {
    private static final String WELCOME_RESOURCE = "launcher_display";
    /**
     * This are the small labels for the individual panes throughout the launcher environment
     * @author Anna Darwish
     */
    public PaneLabel(String key){
        ResourceBundle myResources = ResourceBundle.getBundle(WELCOME_RESOURCE);
        this.setText(myResources.getString(key));
        this.getStylesheets().add("default.css");

    }


}

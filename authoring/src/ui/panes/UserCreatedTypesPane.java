package ui.panes;

import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

/**
 * @author Carrie Hunner
 * This is a pane that will display all of the types created by the user
 */
public class UserCreatedTypesPane extends VBox {
    private EntityMenu myEntityMenu;
    private ResourceBundle myResources;
    private static final String RESOURCE = "default_entity_type";

    public UserCreatedTypesPane(){
        myResources = ResourceBundle.getBundle(RESOURCE);
        String title = myResources.getString("UserCreatedTitle");
        myEntityMenu = new EntityMenu(title);
    }
}

package ui.panes;

import engine.external.Entity;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.DefaultTypesFactory;
import ui.windows.CreateNewTypeWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Carrie Hunner
 * This class creates the page displaying the default types
 * provided
 */
public class DefaultTypesPane extends VBox{
    private EntityMenu myEntityMenu;
    private ResourceBundle myResources;
    private UserCreatedTypesPane myUserCreatedTypesPane;
    private DefaultTypesFactory myDefaultTypesFactory;

    private static final String RESOURCE = "default_entity_type";
    private static final String TITLE_KEY = "DefaultTitle";

    /**
     * Creates a new page to display the default types
     */
    public DefaultTypesPane(UserCreatedTypesPane userCreatedTypesPane)     {
        myResources = ResourceBundle.getBundle(RESOURCE);
        myEntityMenu = new EntityMenu(myResources.getString(TITLE_KEY));
        myUserCreatedTypesPane = userCreatedTypesPane;
        myDefaultTypesFactory = new DefaultTypesFactory();
        populateEntityMenu();
        this.getChildren().add(myEntityMenu);
    }

    private void populateEntityMenu(){
        List<String> tabNames = myDefaultTypesFactory.getCategories();
        for(String s1 : tabNames){
            ArrayList<Pane> labelsList = new ArrayList<>();
            List<String> specificTypes = myDefaultTypesFactory.getTypes(s1);
            for(String s2 : specificTypes){
                Label label = new Label(s2);
                VBox pane = createAndFormatVBox(s1, s2, label);
                labelsList.add(pane);
            }
            myEntityMenu.addDropDown(s1);
            myEntityMenu.addToDropDown(s1, labelsList);
        }

    }

    //takes in the Strings for the type and basedOn as well as the label
    //sets up the event for when clicked on
    private VBox createAndFormatVBox(String s1, String s2, Label label) {
        VBox pane = new VBox(label);
        pane.setFillWidth(true);
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CreateNewTypeWindow createNewTypeWindow = new CreateNewTypeWindow(s1, s2);
                createNewTypeWindow.showAndWait();
                Entity entity = createNewTypeWindow.getUserCreatedEntity();
                String[] info = createNewTypeWindow.getCategoryInfo();
                String typeOf = info[0];
                String basedOn = info[1];
                if(entity != null){
                    myUserCreatedTypesPane.addUserDefinedType(typeOf, entity, basedOn);
                }
            }
        });
        return pane;
    }

}

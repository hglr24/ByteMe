package ui.manager;

import events.EventType;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.*;
import ui.panes.CurrentEventsPane;

/**
 * The EventManager handles displaying options for the user to create a new event according to the particular AuthoringEntity
 * that is currently in focus. These options include adding a new event, removing one previously made, and in the future,
 * modifying current ones
 * @author Harry Ross, Anna Darwish
 */
public class EventManager extends Stage {

    private AuthoringEntity myEntity;
    private String myEntityName;
    private Refresher myRefresher = this::refreshEventListing;
    private static final String TITLE = "Conditions \t\t\t\t Actions \t\t\t\t Modify Event";
    private static final String ADD_EVENT = "+ Event";
    private static final String STYLE = "default.css";
    private static final String STYLE_CLASS = "event-manager";
    private static final String STYLE_VBOX = "event-manager-vbox";
    private static final String BIGBUTTON = "BigButton";
    private LabelManager myLabelManager;
    public EventManager(Propertable prop) { // Loads common Events for object instance based on type label
        myEntity = (AuthoringEntity) prop; // EventManager is only ever used for an Entity, so cast can happen
        myLabelManager = myEntity.getObjectManager().getLabelManager();
        Scene myDefaultScene = createPane();
        this.setScene(myDefaultScene);
        myEntityName = myEntity.getPropertyMap().get(EntityField.LABEL);
    }

    private Scene createPane() {
        BorderPane myEventsDisplay = new BorderPane();

        Scene myScene = new Scene(myEventsDisplay);

        myScene.getStylesheets().add(STYLE);
        myEventsDisplay.getStyleClass().add(STYLE_CLASS);

        myEventsDisplay.setTop(createTitle());
        myEventsDisplay.setLeft(null);
        myEventsDisplay.setCenter(new CurrentEventsPane(myEntity.getEvents(), myRefresher));
        myEventsDisplay.setRight(null);
        myEventsDisplay.setBottom(createEventsToolPane());
        return myScene;
    }

    private VBox createTitle(){
        VBox myEntityTile = new VBox();
        myEntityTile.getStyleClass().add(STYLE_VBOX);
        Label myTitle = new Label(TITLE);
        myEntityTile.getChildren().add(myTitle);
        return myEntityTile;
    }

    private VBox createEventsToolPane(){
        VBox myTools = new VBox();
        ChoiceBox<String> myEventsPopUp = new ChoiceBox<>(FXCollections.observableArrayList(EventType.allDisplayNames));
        myEventsPopUp.getSelectionModel().selectedItemProperty().addListener((observableValue, s, eventName) -> {
            AuthoringEvent myAuthoringEvent = makeAuthoringEvent(eventName.replaceAll(" ",""),myEntityName);
            myAuthoringEvent.addSaveComponents(myRefresher,myEntity.getEvents());
            myAuthoringEvent.render();

        });
//        myEventsPopUp.getStylesheets().add(STYLE);
//        myEventsPopUp.getStyleClass().add(BIGBUTTON);
        myTools.getChildren().add(myEventsPopUp);
        return myTools;
    }


    private void refreshEventListing(){
        this.setScene(createPane());
    }

    private AuthoringEvent makeAuthoringEvent(String eventName, String entityName){
        if (EventType.valueOf(eventName).isInteractive()){
            return new AuthoringInteractiveEvent(myLabelManager,eventName,entityName);
        }
        else {
            return new AuthoringConditionalEvent(entityName);
        }
    }
}

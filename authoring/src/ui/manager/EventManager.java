package ui.manager;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.*;
import ui.panes.CurrentEventsPane;
import ui.panes.EventPane;
import ui.panes.EventsPopUpPane;

/**
 * The EventManager handles displaying options for the user to create a new event according to the particular AuthoringEntity
 * that is currently in focus. These options include adding a new event, removing one previously made, and in the future,
 * modifying current ones
 * @see EventPane
 * @author Harry Ross, Anna Darwish
 */
public class EventManager extends Stage {

    private AuthoringEntity myEntity;
    private String myEntityName;
    private static final String INTERACTIVE_EVENT = "INTERACTIVE";
    private static final String EVENT_CLASSIFIER_RESOURCE = "event_classifier";
    private BorderPane myEventsDisplay;
    private Refresher refreshEventsListing = () -> refreshEventListing();
    public EventManager(Propertable prop) { // Loads common Events for object instance based on type label
        myEntity = (AuthoringEntity) prop; // EventManager is only ever used for an Entity, so cast can happen
        Scene myDefaultScene = createPane();
        this.setScene(myDefaultScene);
        myEntityName = myEntity.getPropertyMap().get(EntityField.LABEL);
    }


    private Node createContent() {
        GridPane eventsGrid = new GridPane();

        for (int i = 0; i < myEntity.getEvents().size(); i++) {
            eventsGrid.add(new HBox(new Label(myEntity.getEvents().get(i).getClass().toString())), 0, i);
        }

        return new ScrollPane(eventsGrid);
    }

    private Scene createPane() {
        myEventsDisplay = new BorderPane();
        myEventsDisplay.setTop(createTitle());
        myEventsDisplay.setLeft(null);
        myEventsDisplay.setCenter(new CurrentEventsPane(myEntity.getEvents(), refreshEventsListing));
        myEventsDisplay.setRight(null);
        myEventsDisplay.setBottom(createEventsToolPane());
        Scene myScene = new Scene(myEventsDisplay);
        this.setMinHeight(700);
        this.setMinWidth(800);
        this.setMaxHeight(700);
        this.setMaxWidth(800);
        this.setResizable(false);
        myScene.getStylesheets().add("default.css");
        return myScene;
    }

    private VBox createTitle(){
        VBox myEntityTile = new VBox();
        myEntityTile.setAlignment(Pos.CENTER);
        myEntityTile.getChildren().add(new Label(myEntityName + "'s Events"));
        return myEntityTile;
    }
//    private VBox createEventOptions(){
//        List<String> entityActors = new ArrayList<>();
//        entityActors.addAll(myEntity.getInteractionListing());
//        entityActors.add(myEntityName);
//        return new EventOptionsPane(entityActors,myEntity.getInteractionListing(),myEntity.getPropertyMap(),refreshEventListing);
//    }
    private VBox createEventsToolPane(){
        VBox myTools = new VBox();
        Button myEventsPopUp = new Button("+ Event");
        myEventsPopUp.setOnMouseClicked(e -> new EventsPopUpPane(myEntity.getPropertyMap(), myEntity.getEvents(), myEntityName,refreshEventsListing ));
        myTools.getChildren().add(myEventsPopUp);
        return myTools;
    }


//    private ObservableList<Event> filterEvents(String eventType){
//        ResourceBundle myEventsClassifier = ResourceBundle.getBundle(EVENT_CLASSIFIER_RESOURCE);
//        List<Event> mySubsetOfEvents = new ArrayList<>();
//
//        for (Event entityEvent: myEntity.getEvents()){
//            String fullClassName = entityEvent.getClass().toString();
//            String briefClassName = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
//            String enumName = myEventsClassifier.getString(briefClassName.replaceAll(" ",""));
//            if (EventType.valueOf(enumName).getEventClassifier().equals(eventType)) {
//                mySubsetOfEvents.add(entityEvent);
//            }
//        }
//        return FXCollections.observableArrayList(mySubsetOfEvents);
//    }


    private void refreshEventListing(){
        this.setScene(createPane());
    }

}

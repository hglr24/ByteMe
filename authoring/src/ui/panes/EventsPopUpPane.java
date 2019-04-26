package ui.panes;
import engine.external.events.Event;
import events.EventFactory;
import events.EventType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.manager.Refresher;

import java.util.ResourceBundle;


public class EventsPopUpPane extends Stage {
    private ChoiceBox<String> myEvents;
    private EventOptionsPane myOptions = new EventOptionsPane();

    private static final String EVENT_DISPLAY = "event_display";
    private ResourceBundle myEventDisplay = ResourceBundle.getBundle(EVENT_DISPLAY);

    private static final String SELECT = "Select";
    private static final String SAVE = "Save";
    private static final String CANCEL = "Cancel";

    private static final String STYLE = "default.css";
    private static final String STYLE_CLASS = "event-pop-up";

    public EventsPopUpPane(ObservableList<Event> myEntityEvents, String myEntityName, Refresher myEventsRefresher){
        VBox eventTypeChooser = setUpScene();
        HBox buttonOptions = setUpButtons(myEntityEvents,myEntityName,myEventsRefresher);

        eventTypeChooser.getChildren().add(buttonOptions);
        Scene myScene = new Scene(eventTypeChooser);
        myScene.getStylesheets().add(STYLE);

        this.setScene(myScene);
        this.showAndWait();
    }

    private VBox setUpScene(){
        VBox eventTypeChooser = new VBox();
        eventTypeChooser.getStyleClass().add(STYLE_CLASS);
        HBox eventBox = createEventListing();
        eventTypeChooser.getChildren().add(eventBox);
        eventTypeChooser.getChildren().add(myOptions);
        return eventTypeChooser;
    }

    private HBox createEventListing(){
        HBox eventBox = new HBox();
        myEvents = new ChoiceBox<>(FXCollections.observableArrayList(EventType.allDisplayNames));
        myEvents.getSelectionModel().selectedItemProperty().addListener((observableEvent, previousEvent, selectedEvent) ->
                manageEventParameters(selectedEvent));
        eventBox.getChildren().add(EventFactory.createLabel(SELECT));
        eventBox.getChildren().add(myEvents);
        return eventBox;
    }

    private void manageEventParameters(String eventName){
        myOptions.displayEventOptions(eventName);
    }


    private HBox setUpButtons(ObservableList<Event> myEntityEvents, String myEntityName, Refresher myEventsRefresher){
        HBox buttonOptions = new HBox();
        buttonOptions.getChildren().add(makeSaveButton(myEntityEvents, myEntityName,myEventsRefresher));
        buttonOptions.getChildren().add(makeCancelButton());
        return buttonOptions;
    }

    private Button makeSaveButton(ObservableList<Event> myEntityEvents, String myEntityName, Refresher myEventsRefresher ){
        Button saveButton = new Button(myEventDisplay.getString(SAVE));
        saveButton.setOnMouseClicked(mouseEvent -> {
            String eventKeyName = myEvents.getValue().replaceAll(" ","");
            Event userMadeEvent = myOptions.saveEvent(myEntityName, eventKeyName);
            myEntityEvents.add(userMadeEvent);
            myEventsRefresher.refresh();
            closePage();
        });
        return saveButton;
    }
    private Button makeCancelButton() {
        Button myCancelButton = new Button(CANCEL);
        myCancelButton.setOnMouseClicked(mouseEvent -> closePage());
        return myCancelButton;
    }
    private void closePage(){
        this.close();
    }


}

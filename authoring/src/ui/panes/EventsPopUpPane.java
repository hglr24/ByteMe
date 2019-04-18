package ui.panes;
import engine.external.events.Event;
import engine.external.events.EventFactory;
import engine.external.events.EventType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.manager.Refresher;


public class EventsPopUpPane extends Stage {
    private ChoiceBox<String> myEvents;
    private EventOptionsPane myOptions = new EventOptionsPane();
    private EventFactory myFactory = new EventFactory();

    public EventsPopUpPane(ObservableMap<Enum, String> uniqueComponents,
                           ObservableList<Event> myEntityEvents, String myEntityName, Refresher myEventsRefresher){
        VBox eventTypeChooser = setUpScene();

        HBox buttonOptions = new HBox();
        buttonOptions.getChildren().add(makeSaveButton(myEntityEvents, myEntityName,myEventsRefresher));
        buttonOptions.getChildren().add(makeCancelButton());
        eventTypeChooser.getChildren().add(buttonOptions);

        Scene myScene = new Scene(eventTypeChooser);
        myScene.getStylesheets().add("default.css");
        this.setScene(myScene);
        this.show();
    }

    private VBox setUpScene(){
        VBox eventTypeChooser = new VBox();
        eventTypeChooser.setMinSize(500,400);
        HBox eventBox = new HBox();
        myEvents = new ChoiceBox<>(FXCollections.observableArrayList(EventType.allDisplayNames));
        myEvents.getSelectionModel().selectedItemProperty().addListener((observableEvent, previousEvent, selectedEvent) ->
                manageEventParameters(selectedEvent));
        eventBox.getChildren().add(EventFactory.createLabel("Select Event - "));
        eventBox.getChildren().add(myEvents);
        eventTypeChooser.getChildren().add(eventBox);
        eventTypeChooser.getChildren().add(myOptions);
        return eventTypeChooser;
    }

    private void manageEventParameters(String eventName){
        myOptions.displayEventOptions(eventName);
    }


    private Button makeSaveButton(ObservableList<Event> myEntityEvents, String myEntityName, Refresher myEventsRefresher ){
        Button saveButton = new Button("save");
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
        Button myCancelButton = new Button("Cancel");
        myCancelButton.setOnMouseClicked(mouseEvent -> closePage());
        return myCancelButton;
    }
    private void closePage(){
        this.close();
    }


}

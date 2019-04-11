package ui.manager;

import events.EventType;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ui.*;
import ui.panes.EventPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Harry Ross
 */
public class EventManager extends Stage {

    private AuthoringEntity myEntity;
    private String myEntityName;
    private Scene myDefaultScene;

    private Refresher myRefreshAccess = new Refresher() {
        @Override
        public void refresh() {
            refreshDisplayOfEvents();
        }
    };
    public EventManager(Propertable prop) { // Loads common Events for object instance based on type label
        myEntity = (AuthoringEntity) prop; // EventManager is only ever used for an Entity, so cast can happen
        myDefaultScene = createPane();
        this.setScene(myDefaultScene);
        this.setResizable(false);
        createContent();
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
        Map<String, List<String>> description = new LinkedHashMap<>();
        description.put("label", new ArrayList<>(Collections.singletonList("Manage " + myEntity.getPropertyMap().get(EntityField.LABEL) + " Events")));
        description.put("sub-label", new ArrayList<>(Collections.singletonList("Add or Remove Events for " + myEntity.getPropertyMap().get(EntityField.LABEL))));

        ComboBox<String> myAddEventBox = new ComboBox<>(FXCollections.observableArrayList(EventType.allDisplayNames));
        myAddEventBox.setValue("Add Event...");
        myAddEventBox.getStylesheets().add("default.css");
        addEvent(myAddEventBox);
        Button removeButton = Utility.makeButton(this, "removeEvent", "Remove");
        Button closeButton = Utility.makeButton(this, "closeWindow", "Close");

        //Scene myScene = Utility.createDialogPane(Utility.createLabelsGroup(description), createContent(), Arrays.asList(removeButton, closeButton));
        return Utility.createGeneralPane(Utility.createLabelsGroup(description), createContent(),
                Arrays.asList(myAddEventBox,removeButton,closeButton));
    }

    private void addEvent(ComboBox<String> myEvents) {
        myEvents.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    try {
                        openEventOptions(myEvents.getValue());
                    } catch (UIException e) {
                        e.displayUIException();
                    }
                });
    }

    private void openEventOptions(String eventName) throws UIException {
        new EventPane(eventName, myEntityName, myEntity.getEvents(), myRefreshAccess);
    }

    private void refreshDisplayOfEvents(){
        this.setScene(createPane());
    }


    private void removeEvent() {
        // Remove selected Event from ObservableList (myEntity.getEvents())
        // We have to allow for the user to select a gridpane cell to remove, maybe replace entirely with listview to make that easier??
    }

    public void closeWindow() {
        this.close();
    }
}

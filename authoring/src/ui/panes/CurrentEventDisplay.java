package ui.panes;
import engine.external.actions.Action;
import engine.external.conditions.Condition;
import engine.external.events.Event;
import events.EventFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.UIException;
import ui.manager.AddKeyCode;
import ui.manager.LabelManager;

import java.util.*;

    class CurrentEventDisplay extends GridPane {
    private Event myEvent;
    private Editor myEventRemover;
    private Editor myEventModifier;
    private AddKeyCode myKeyCodeAdder;
    private ResourceBundle myKeyCodes = ResourceBundle.getBundle("keycode");
    private ResourceBundle myErrorMessage = ResourceBundle.getBundle("error_messages");
    private ResourceBundle myKeyCodesDisplay = ResourceBundle.getBundle("concrete_keycode");
    private ResourceBundle KEY_DISPLAY = ResourceBundle.getBundle("display_keycodes");
    private static final String EDIT = "Edit";
    private static final String REMOVE = "Remove";
    private static final String DELIMITER = ".";
    private static final String CSS = "current-events-display";


    CurrentEventDisplay(Map<Class<?>, List<?>> myMap, Event myEvent, Editor eventRemover, Editor eventModifier, AddKeyCode addKeyCode){
        this.myEvent = myEvent;
        this.myEventRemover = eventRemover;
        this.myEventModifier = eventModifier;
        this.myKeyCodeAdder = addKeyCode;
        if (invalidEvent(myMap)){
            return;
        }
        this.getStyleClass().add(CSS);
        setUpEventInformation(myMap);
        setUpEditToolBar();

    }

    private void setUpEventInformation(Map<Class<?>, List<?>> myMap){
        Label myConditions = setUpLabel(myMap.get(Condition.class));
        Label myActions = setUpLabel(myMap.get(Action.class));
        this.add(myConditions,0,0);
        this.add(myActions,1,0);
    }
    private Label setUpLabel(List<?> myEventComponents){
        StringBuilder text = new StringBuilder();
        try {
            for (Object eventComponent : myEventComponents) {
                text.append(eventComponent.toString());
                text.append("\n");
            }
        }
        catch(ClassCastException e){
            UIException wrongCast = new UIException(myErrorMessage.getString(this.getClass().getSimpleName()));
            wrongCast.displayUIException();
        }
        Label label = new Label(text.toString());
        label.getStyleClass().add(CSS);
        return label;

    }
    private void setUpEditToolBar(){
        Button editButton = new Button(EDIT);
        Button removeButton = new Button(REMOVE);
        ChoiceBox<String> keyCode = new ChoiceBox<>();

        setUpKeyCodes(keyCode);
        removeButton.setOnMouseClicked(mouseEvent -> myEventRemover.editEvent(myEvent));
        editButton.setOnMouseClicked(mouseEvent -> myEventModifier.editEvent(myEvent));

        VBox buttons = new VBox();
        buttons.getChildren().add(editButton);
        buttons.getChildren().add(removeButton);
        buttons.getChildren().add(keyCode);
        this.add(buttons,2,0);
    }

    private boolean invalidEvent(Map<Class<?>, List<?>> myMap) {
        return !myMap.containsKey(Condition.class) || !myMap.containsKey(Action.class) ||
                myMap.get(Condition.class).size() == 0 || myMap.get(Action.class).size() == 0;
    }

    private void setUpKeyCodes(ChoiceBox<String> myKeyCodesListing){
        EventFactory.setUpKeyCode(myKeyCodes,myKeyCodesListing);
        if (myEvent.getImmutableKeyCodes().size()!= 0){
            myKeyCodesListing.setValue(KEY_DISPLAY.getString(myEvent.getImmutableKeyCodes().get(0).toString()));
        }
        myKeyCodesListing.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) -> {
            myKeyCodesListing.setAccessibleText(newValue);
            myKeyCodeAdder.refresh(myEvent,KeyCode.getKeyCode(myKeyCodesDisplay.getString(newValue)));
        });


    }
}

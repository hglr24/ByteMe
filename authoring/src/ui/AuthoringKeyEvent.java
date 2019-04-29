package ui;

import engine.external.component.NameComponent;
import engine.external.conditions.Condition;
import engine.external.conditions.StringEqualToCondition;
import engine.external.events.Event;
import events.EventBuilder;
import events.EventFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.manager.RefreshEvents;

import java.util.ResourceBundle;

public class AuthoringKeyEvent extends AuthoringEvent {
    private static final String USER_PROMPT = "Select Key...";
    private ResourceBundle myKeyCodes = ResourceBundle.getBundle("keycode");
    private ResourceBundle generateKeyCodes = ResourceBundle.getBundle("concrete_keycode");

    private static final String SAVE = "Save";
    private String myEntityName;
    private StringProperty keyName = new SimpleStringProperty(); //Name of the component for the conditional
    

    private RefreshEvents myRefresher;
    private ObservableList<Event> myEntityEvents;
    public AuthoringKeyEvent(String entityName){
        myEntityName = entityName;
    }
    @Override
    public VBox generateEventOptions(){
        VBox eventOptions = new VBox();
        eventOptions.getChildren().add(generateKeyOptions());
        eventOptions.getChildren().add(super.createActionOptions());
        eventOptions.getChildren().add(createToolBar());
        return eventOptions;
    }


    @Override
    public void addSaveComponents(RefreshEvents refresher, ObservableList<Event> entityEvents) {
        myRefresher = refresher;
        myEntityEvents = entityEvents;
    }

    private HBox generateKeyOptions() {
        Label userPrompt = EventFactory.createLabel(USER_PROMPT);
        ChoiceBox<String> keyCodeOptions = new ChoiceBox<>();
        EventFactory.setUpKeyCode(myKeyCodes,keyCodeOptions);
        keyName.bindBidirectional(keyCodeOptions.accessibleTextProperty());
        HBox keyOptions = new HBox();
        keyOptions.getChildren().add(userPrompt);
        keyOptions.getChildren().add(keyCodeOptions);
        return keyOptions;
    }

    private HBox createToolBar(){
        Button saveButton = new Button(SAVE);
        saveButton.setOnMouseClicked(mouseEvent -> saveKeyEvent());
        return super.createToolBar(saveButton);
    }

    private void saveKeyEvent(){
        Event conditionalEvent = new Event();
        conditionalEvent.addConditions(new StringEqualToCondition(NameComponent.class,myEntityName));
        conditionalEvent.addInputs(KeyCode.valueOf(generateKeyCodes.getString(keyName.getValue())));
        super.saveAction(conditionalEvent);
        super.saveEvent(conditionalEvent, myRefresher, myEntityEvents);

        super.closeWindow();
    }
}

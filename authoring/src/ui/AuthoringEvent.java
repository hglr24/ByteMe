package ui;

import engine.external.actions.Action;
import engine.external.actions.ProgressionAction;
import engine.external.events.Event;
import events.EventBuilder;
import events.EventFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.manager.RefreshEvents;

import java.util.*;

public abstract class AuthoringEvent extends Stage {
    private static final String USER_ACTION_PROMPT = "Action:";
    private static final String ACTION_RESOURCE = "actions_display";
    private static final String CANCEL = "Cancel";
    private static final String CHECKPOINT = "Click to Insert CheckPoint";
    private static final String DEFAULT_STYLESHEET = "default.css";

    private StringProperty componentName = new SimpleStringProperty(); //Name of the component for the conditional
    private StringProperty modifierOperator = new SimpleStringProperty(); //type of modifier such as set or scale
    private StringProperty triggerValue = new SimpleStringProperty();  //value associated with action
    private BooleanProperty checkPoint = new SimpleBooleanProperty();

    abstract VBox generateEventOptions();

    public abstract void addSaveComponents(RefreshEvents myRefresher, ObservableList<Event> myEntityEvents);

    public void saveEvent(Event createdEvent, RefreshEvents myRefresher, ObservableList<Event> myEntityEvents){
        myEntityEvents.add(createdEvent);
        myRefresher.refreshEventDisplay(createdEvent);
        myRefresher.refreshEventDisplay(createdEvent);
        this.close();
    }

    public void render(){
        VBox eventOptions = generateEventOptions();
        Scene myScene = new Scene(eventOptions);
        myScene.getStylesheets().add(DEFAULT_STYLESHEET);
        eventOptions.getStyleClass().add("event-builder");
        this.setScene(myScene);
        this.sizeToScene();
        this.show();
    }

    VBox createActionOptions(){
        VBox actionOptions = new VBox();
        HBox actions = createEventComponentOptions(USER_ACTION_PROMPT,ACTION_RESOURCE,componentName,modifierOperator,triggerValue);
        ToggleButton myButton = new ToggleButton(CHECKPOINT);
        myButton.setOnMouseClicked(mouseEvent -> {
            if (myButton.isSelected())
                myButton.setTextFill(Color.AQUAMARINE);
        });
        checkPoint.bindBidirectional(myButton.selectedProperty());
        actionOptions.getChildren().add(actions);
        actionOptions.getChildren().add(myButton);
        return actionOptions;
    }

    void saveAction(Event buildingEvent){
        EventBuilder myBuilder = new EventBuilder();
        try {
            Action createdAction = myBuilder.createGeneralAction(componentName.getValue(), modifierOperator.getValue(), triggerValue.getValue());
            buildingEvent.addActions(createdAction);
            if (checkPoint.getValue())
                buildingEvent.addActions(new ProgressionAction(true));
        }
        catch (UIException e){
            e.displayUIException();
        }
    }

    HBox createEventComponentOptions(String prompt, String resourceName, StringProperty componentName,
                                               StringProperty modifierOperator, StringProperty triggerValue){
        return EventFactory.createEventComponentOptions(prompt,resourceName,componentName,modifierOperator,triggerValue);

    }

    ChoiceBox<String> setUpPairedChoiceBoxes(Map<String,ObservableList<String>> actionOperatorOptions,
                                                     StringProperty controller, StringProperty dependent,HBox parent){
        return EventFactory.setUpPairedChoiceBoxes(actionOperatorOptions,controller,dependent,parent);
    }

    HBox createToolBar(Button saveButton){
        HBox toolBar = new HBox();
        toolBar.getStyleClass().add("buttons-bar");
        Button cancelButton = new Button(CANCEL);
        cancelButton.setOnMouseClicked(mouseEvent -> closeWindow());
        toolBar.getChildren().add(saveButton);
        toolBar.getChildren().add(cancelButton);
        return toolBar;
    }

    void closeWindow(){
        this.close();
    }

}

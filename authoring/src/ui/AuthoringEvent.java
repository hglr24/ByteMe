package ui;

import engine.external.actions.Action;
import engine.external.events.Event;
import events.EventBuilder;
import events.EventFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.manager.Refresher;

import java.util.*;

public abstract class AuthoringEvent extends Stage {
    private static final String USER_ACTION_PROMPT = "Select Action...";
    private static final String ACTION_RESOURCE = "actions_display";
    private static final String CANCEL = "Cancel";

    private StringProperty componentName = new SimpleStringProperty(); //Name of the component for the conditional
    private StringProperty modifierOperator = new SimpleStringProperty(); //type of modifier such as set or scale
    private StringProperty triggerValue = new SimpleStringProperty();  //value associated with action

    abstract VBox generateEventOptions();

    public abstract void addSaveComponents(Refresher myRefresher, ObservableList<Event> myEntityEvents);

    public void saveEvent(Event createdEvent, Refresher myRefresher, ObservableList<Event> myEntityEvents){
        myEntityEvents.add(createdEvent);
        myRefresher.refresh();
    }

    public void render(){
        Scene myScene = new Scene(generateEventOptions());
        this.setScene(myScene);
        this.show();
    }

    HBox createActionOptions(){
        return createEventComponentOptions(USER_ACTION_PROMPT,ACTION_RESOURCE,componentName,modifierOperator,triggerValue);
    }

    void saveAction(Event buildingEvent){
        EventBuilder myBuilder = new EventBuilder();
        try {
            Action createdAction = myBuilder.createGeneralAction(componentName.getValue(), modifierOperator.getValue(), triggerValue.getValue());
            buildingEvent.addActions(createdAction);
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
        Button cancelButton = new Button(CANCEL);
        cancelButton.setOnMouseClicked(mouseEvent -> closeWindow());
        toolBar.getChildren().add(saveButton);
        toolBar.getChildren().add(cancelButton);
        return toolBar;
    }

    private void closeWindow(){
        this.close();
    }

}

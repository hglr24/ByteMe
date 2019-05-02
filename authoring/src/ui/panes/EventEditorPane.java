package ui.panes;

import engine.external.actions.Action;
import engine.external.component.NameComponent;
import engine.external.conditions.CollisionCondition;
import engine.external.conditions.Condition;
import engine.external.conditions.StringEqualToCondition;
import engine.external.events.Event;
import events.EventBuilder;
import events.EventFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.manager.Refresher;
import voogasalad.util.reflection.Reflection;
import java.util.List;
import java.util.ResourceBundle;

class EventEditorPane extends Stage {

    private static final String SAVE = "Save";

    private static final String ACTION = "Action";
    private static final String CONDITION = "Condition";

    private static final String STYLE = "default.css";
    private static final String STYLE_CLASS = "event-editor";
    private static final String VBOX_STYE = "event-component-vbox";
    private Stage myPopUpStage;

    private static final ResourceBundle eventComponentResource = ResourceBundle.getBundle("event_editor");
    private static final String ADD = "Add";
    private static final String REMOVE= "Remove";
    private static final String BUILD = "Build";
    private static final String PROMPT= "Prompt";
    private static final String RESOURCE = "Resource";

    private StringProperty componentName = new SimpleStringProperty(); //Name of the component for the conditional
    private StringProperty conditionOperator = new SimpleStringProperty(); //type of condition, such as a LessThanCondition
    private StringProperty triggerValue = new SimpleStringProperty();  //value bound to trigger the actions associated with this event

    EventEditorPane(Event unfinishedEvent, Refresher eventDisplayRefresher){
        HBox splitEditorPane = new HBox();

        List<?> myEventConditions = unfinishedEvent.getEventInformation().get(Condition.class);
        List<?> myEventActions = unfinishedEvent.getEventInformation().get(Action.class);

        TitledPane myConditionScroll = eventComponentPane(myEventConditions,unfinishedEvent, CONDITION);
        TitledPane myActionScroll = eventComponentPane(myEventActions,unfinishedEvent,ACTION);
        splitEditorPane.getChildren().add(myConditionScroll);
        splitEditorPane.getChildren().add(myActionScroll);

        Scene myScene = new Scene(splitEditorPane);
        myScene.getStylesheets().add(STYLE);
        splitEditorPane.getStyleClass().add(STYLE_CLASS);
        this.setOnCloseRequest(windowEvent -> eventDisplayRefresher.refresh());
        this.setScene(myScene);
    }

    private TitledPane eventComponentPane(List<?> myConditions, Event event, String eventComponent){
        ScrollPane eventComponentList = eventComponentScrollPane(myConditions,event,eventComponent);
        return new TitledPane(eventComponent,eventComponentList);
    }


    private ScrollPane eventComponentScrollPane(List<?> myConditions, Event event, String eventComponentName){
        ScrollPane myPane = new ScrollPane();
        VBox myParent = new VBox();
        int index = 0;
        for (Object eventElement: myConditions){
            addEventComponent(eventElement, eventComponentName, myParent, event, index);
            index++;
        }
        Button addButton = new Button(ADD);

        myParent.getChildren().add(addButton);
        setUpAddButton(addButton,event,myParent,eventComponentName);
        myPane.setContent(myParent);
        return myPane;
    }

    private void addEventComponent(Object eventComponent, String eventComponentName, VBox myParent, Event event, int childIndex){
        VBox eventSubInformation = new VBox();
        eventSubInformation.getStyleClass().add(VBOX_STYE);
        Button removeButton = new Button(REMOVE);
        eventSubInformation.getChildren().add(EventFactory.createLabel(eventComponent.toString()));
        if (!eventComponent.getClass().equals(CollisionCondition.class) && !(eventComponent.getClass().equals(StringEqualToCondition.class) && ((StringEqualToCondition) eventComponent).getComponentClass().equals(NameComponent.class)))
            eventSubInformation.getChildren().add(removeButton);
        myParent.getChildren().add(childIndex, eventSubInformation);
        setUpRemoveButton(removeButton, eventComponent, event, eventComponentName, myParent, eventSubInformation);
    }



    private void setUpRemoveButton(Button myButton, Object eventElement, Event event, String eventMethod,VBox parent,VBox child){
        String removeMethodName = eventComponentResource.getString(eventMethod + REMOVE);
        myButton.setOnMouseClicked(mouseEvent -> {
            Reflection.callMethod(event,removeMethodName,eventElement);
            parent.getChildren().remove(child);
        });
    }

    private void setUpAddButton(Button myButton, Event event,VBox parent,String eventComponentName){
        myButton.setOnMouseClicked(mouseEvent -> displayEventComponentMaker(event,parent,eventComponentName));
    }

    private void displayEventComponentMaker(Event event,VBox parent, String eventComponentName){
        VBox myDisplay = getEventControls(event,parent,eventComponentName);
        myDisplay.getStylesheets().add(STYLE);
        myPopUpStage = new Stage();
        myPopUpStage.sizeToScene();
        myPopUpStage.setScene(new Scene(myDisplay));
        myPopUpStage.show();

    }

    private VBox getEventControls(Event myEvent,VBox parent, String eventComponentName){
        VBox myDisplay = new VBox();
        myDisplay.getStyleClass().add("event-builder");
        String promptText = eventComponentResource.getString(eventComponentName + PROMPT);
        String resourceName = eventComponentResource.getString(eventComponentName + RESOURCE);

        HBox controls = EventFactory.createEventComponentOptions(promptText,resourceName,componentName,conditionOperator,triggerValue);
        controls.getStyleClass().add("event-options");
        myDisplay.getChildren().add(controls);
        HBox buttonBar = new HBox(saveButton(myEvent, parent, eventComponentName));
        buttonBar.getStyleClass().add("buttons-bar");
        myDisplay.getChildren().add(buttonBar);
        return myDisplay;
    }

    private Button saveButton(Event myEvent, VBox myParent,String eventComponentName){
        Button mySaveButton = new Button(SAVE);
        EventBuilder myBuilder = new EventBuilder();
        String buildComponentMethodName = eventComponentResource.getString(eventComponentName + BUILD);
        String addComponentMethodName = eventComponentResource.getString(eventComponentName + ADD);
        mySaveButton.setOnMouseClicked(mouseEvent -> {
            Object myEventComponent = Reflection.callMethod(myBuilder,buildComponentMethodName,componentName.getValue(),
                    conditionOperator.getValue(),triggerValue.getValue());
            Reflection.callMethod(myEvent,addComponentMethodName,myEventComponent);
            addEventComponent(myEventComponent,eventComponentName,myParent,myEvent,myParent.getChildren().size()-1);
            myPopUpStage.close();
        });
        return mySaveButton;
    }

}




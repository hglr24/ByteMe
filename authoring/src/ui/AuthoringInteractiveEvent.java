package ui;

import engine.external.component.Component;
import engine.external.component.NameComponent;
import engine.external.conditions.Condition;
import engine.external.conditions.StringEqualToCondition;
import engine.external.events.Event;
import events.EventType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.manager.LabelManager;
import ui.manager.RefreshEvents;
import ui.manager.Refresher;
import voogasalad.util.reflection.Reflection;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AuthoringInteractiveEvent extends AuthoringEvent {
    private static final String SAVE = "Save";

    private static final String GROUP = "Group";
    private static final String ENTITY = "Entity";

    private static final String INTERACTEE_PREFIX = "engine.external.events.";
    private static final String COMPONENT_KEY = "Component";

    private static final String STYLE = "default.css";
    private static final String STYLE_SIZING = "event-editor";
    private static final String COLLISION_PROMPT = "Collision With...";
    private Map<String,ObservableList<String>> myInteractees = new HashMap<>();

    private StringProperty interactionType = new SimpleStringProperty(); //Whether this will be an interaction with a group or entity
    private StringProperty interacteeName =  new SimpleStringProperty(); //Name of group or entity this interactive event would occur with

    private String myEntityName;
    private String myEventName;
    private static final String ERROR_PACKAGE_NAME = "error_messages";
    private static final ResourceBundle myErrors = ResourceBundle.getBundle(ERROR_PACKAGE_NAME);

    private RefreshEvents myRefresher;
    private ObservableList<Event> myEntityEvents;

    public AuthoringInteractiveEvent(LabelManager myLabelManager, String eventName, String entityName){
        ObservableList<String> myEntityNames = myLabelManager.getLabels(EntityField.LABEL);
        ObservableList<String> myGroupNames = myLabelManager.getLabels(EntityField.GROUP);
        myInteractees.put(GROUP,myGroupNames);
        myInteractees.put(ENTITY,myEntityNames);
        myEventName = eventName;
        myEntityName = entityName;
    }

    @Override
    public VBox generateEventOptions(){
        VBox eventOptions = new VBox();
        eventOptions.getStylesheets().add(STYLE);
        eventOptions.getStyleClass().add(STYLE_SIZING);
        HBox myInteractionOptions = new HBox();
        super.setUpPairedChoiceBoxes(myInteractees,interactionType,interacteeName,myInteractionOptions);
        eventOptions.getChildren().add(createInteractionOptions(myInteractionOptions));
        eventOptions.getChildren().add(super.createActionOptions());
        eventOptions.getChildren().add(createToolBar());
        myInteractionOptions.getStyleClass().add("event-options");
        return eventOptions;
    }

    private HBox createInteractionOptions(HBox myInteractionOptions) {
        HBox optionsWithLabel = new HBox();
        optionsWithLabel.getStyleClass().add("event-options");
        optionsWithLabel.getChildren().add(new Label(COLLISION_PROMPT));
        optionsWithLabel.getChildren().add(myInteractionOptions);
        return optionsWithLabel;
    }

    @Override
    public void addSaveComponents(RefreshEvents refresher, ObservableList<Event> entityEvents) {
        myRefresher = refresher;
        myEntityEvents = entityEvents;
    }


    private HBox createToolBar(){
        Button saveButton = new Button(SAVE);
        saveButton.setOnMouseClicked(mouseEvent -> saveConditionalEvent());
        return super.createToolBar(saveButton);
    }

    private void saveConditionalEvent(){
        boolean isGrouped = interactionType.getValue().equals(GROUP);
        Event interactiveEvent = (Event)Reflection.createInstance(EventType.valueOf(myEventName).getClassName(),interacteeName.getValue(),isGrouped);
        //saveInteractee(interactiveEvent);
        interactiveEvent.addConditions(new StringEqualToCondition(NameComponent.class,myEntityName));
        super.saveAction(interactiveEvent);
        super.saveEvent(interactiveEvent,myRefresher,myEntityEvents);
        super.closeWindow();
    }

    private void saveInteractee(Event event){
//        try {
//            String className = INTERACTEE_PREFIX + interactionType.getValue() + COMPONENT_KEY;
//            Class<? extends Component> componentClass = (Class<? extends Component>)Class.forName(className);
//            Condition interacteeCondition = new StringEqualToCondition(componentClass, interacteeName.getValue());
//            event.addConditions(interacteeCondition);
//        }
//        catch(Exception e){
//            UIException invalidInteractee = new UIException(myErrors.getString(this.getClass().getSimpleName()));
//            invalidInteractee.displayUIException();
//        }
    }
}

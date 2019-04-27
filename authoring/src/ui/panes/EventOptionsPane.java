package ui.panes;
import engine.external.actions.Action;
import engine.external.component.NameComponent;
import engine.external.conditions.Condition;
import engine.external.conditions.StringEqualToCondition;
import engine.external.events.Event;
import events.EventBuilder;
import events.EventFactory;
import events.EventType;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.AuthoringConditionalEvent;
import ui.AuthoringEvent;
import ui.AuthoringInteractiveEvent;
import ui.UIException;
import ui.manager.LabelManager;

import java.awt.*;
import java.util.*;

public class EventOptionsPane extends VBox {
    private static final String CONTROLS = "event_controls";
    private static final String INTERACTIVE_EVENTS = "interaction_events";

    private static final String STYLE = "default.css";
    private static final String STYLE_CLASS = "event-options-pane";

    private EventFactory myEventFactory = new EventFactory();
    private Map<String,StringProperty> myEventOptionsListener = new HashMap<>();
    private Map<String,StringProperty> myActionOptionsListener = new HashMap<>();

    private static final String ERROR_PACKAGE_NAME = "error_messages";
    private static final String ACTION_KEY = "Action";

    private static final ResourceBundle myErrors = ResourceBundle.getBundle(ERROR_PACKAGE_NAME);
    private static final String ERROR_ONE_KEY = "InvalidEvent";
    private static final String ERROR_TWO_KEY = "InvalidAction";
    private EventBuilder myBuilder = new EventBuilder();
    private LabelManager myInteractionListing;
    public EventOptionsPane(LabelManager interactionListing){
        this.getStylesheets().add(STYLE);
        this.getStyleClass().add(STYLE_CLASS);
        this.myInteractionListing = interactionListing;

    }

    void displayEventOptions(String eventName) {
        this.getChildren().clear();
        myEventOptionsListener.clear();
        HBox eventOptionsPanel = new HBox();


        this.getChildren().add(eventOptionsPanel);
        HBox actionOptionsPanel = new HBox();
        myEventFactory.factoryDelegator(CONTROLS,ACTION_KEY,actionOptionsPanel,myActionOptionsListener);
        this.getChildren().add(actionOptionsPanel);
    }

    Event saveEvent(String entityName, String eventName){
        Event userMadeEvent = new Event();
        userMadeEvent.addConditions(new StringEqualToCondition(NameComponent.class,entityName));
        try {
            userMadeEvent = myBuilder.createCollisionEvent(entityName,eventName,myEventOptionsListener);
        }
        catch (Exception notCollisionEvent) {
            try {
                Condition userMadeCondition = myBuilder.createGeneralCondition(myEventOptionsListener);
                userMadeEvent.addConditions(userMadeCondition);
            }
            catch (Exception notGeneralEvent) {
                UIException myException = new UIException(myErrors.getString(ERROR_ONE_KEY));
                myException.displayUIException();
            }
        }
        Action userMadeAction = null;
        try {
             userMadeAction = myBuilder.createGeneralAction(myActionOptionsListener);
        }
        catch(Exception e){
            UIException myException = new UIException(myErrors.getString(ERROR_TWO_KEY));
            myException.displayUIException();
        }
        userMadeEvent.addActions(userMadeAction);
       return userMadeEvent;
    }

}

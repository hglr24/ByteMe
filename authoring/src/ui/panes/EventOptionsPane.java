package ui.panes;
import engine.external.actions.Action;
import engine.external.conditions.Condition;
import engine.external.events.Event;
import events.EventBuilder;
import events.EventFactory;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.UIException;
import java.util.*;

public class EventOptionsPane extends VBox {
    private static final String CONTROLS = "event_controls";
    private static final String DISPLAY = "event_classes";

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
    public EventOptionsPane(){
        this.getStylesheets().add(STYLE);
        this.getStyleClass().add(STYLE_CLASS);

    }

    void displayEventOptions(String eventName) {
        this.getChildren().clear();
        myEventOptionsListener.clear();
        HBox eventOptionsPanel = new HBox();
        myEventFactory.factoryDelegator(CONTROLS,eventName,eventOptionsPanel,myEventOptionsListener);
        this.getChildren().add(eventOptionsPanel);
        HBox actionOptionsPanel = new HBox();
        myEventFactory.factoryDelegator(CONTROLS,ACTION_KEY,actionOptionsPanel,myActionOptionsListener);
        this.getChildren().add(actionOptionsPanel);
    }

    Event saveEvent(String entityName, String eventName){
        Event userMadeEvent = new Event(entityName);
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

package events;
import engine.external.actions.Action;
import engine.external.actions.HeightAction;
import engine.external.actions.NumericAction;
import engine.external.conditions.Condition;
import engine.external.conditions.ConditionType;
import engine.external.events.Event;
import javafx.beans.property.StringProperty;
import ui.UIException;
import voogasalad.util.reflection.Reflection;
import java.util.Map;
import java.util.ResourceBundle;

public class EventBuilder {
    private static final String DISPLAY = "event_classes";

    private static final ResourceBundle DISPLAY_RESOURCES = ResourceBundle.getBundle(DISPLAY);

    private static final String ERROR_PACKAGE_NAME = "error_messages";
    private static final String ACTION_PACKAGE_PREFIX = "engine.external.actions.";
    private static final String COMPONENT_PACKAGE_PREFIX = "engine.external.component.";

    private static final String COLLIDEE = "COLLIDEE";
    private static final String COMPARATOR = "COMPARATOR";
    private static final String COMPONENT = "COMPONENT";
    private static final String ACTION = "ACTION";
    private static final String MODIFIER = "MODIFIER";
    private static final String VALUE = "VALUE";

    private static final String ACTION_KEY = "Action";
    private static final String COMPONENT_KEY = "Component";

    private static final ResourceBundle myErrors = ResourceBundle.getBundle(ERROR_PACKAGE_NAME);
    private static final String ERROR_TWO_KEY = "InvalidAction";


    public Event createCollisionEvent(String entityName, String eventName,Map<String,StringProperty> myEventOptionsListener) {
        String eventClassName = DISPLAY_RESOURCES.getString(eventName);
        return (Event) Reflection.createInstance(eventClassName,entityName,myEventOptionsListener.get(COLLIDEE).getValue());
    }

    public Condition createGeneralCondition(Map<String,StringProperty> myEventOptionsListener, Event modifiableEvent) throws Exception{
        Condition generatedCondition = createGeneralCondition(myEventOptionsListener);
        modifiableEvent.addConditions(generatedCondition);
        return generatedCondition;
    }

    public Condition createGeneralCondition(Map<String,StringProperty> myEventOptionsListener) throws Exception{
        ConditionType myConditionType = ConditionType.valueOf(myEventOptionsListener.get(COMPARATOR).getValue().replaceAll(" ",""));
        String conditionClassName = myConditionType.getClassName();
        Class componentClass = Class.forName(COMPONENT_PACKAGE_PREFIX + myEventOptionsListener.get(COMPONENT).getValue() + COMPONENT_KEY);
        try{
            Double value = Double.parseDouble(myEventOptionsListener.get(VALUE).getValue());
            return (Condition)Reflection.createInstance(conditionClassName,componentClass,value);
        }
        catch(Exception e){
            String value = myEventOptionsListener.get(VALUE).getValue();
            return (Condition)Reflection.createInstance(conditionClassName,componentClass,value);
        }
    }
    public Action createGeneralAction(Map<String,StringProperty> myEventOptionsListener, Event modifiableEvent) {
        Action generatedAction = createGeneralAction(myEventOptionsListener);
        modifiableEvent.addActions(generatedAction);
        return generatedAction;
    }

    public Action createGeneralAction(Map<String,StringProperty> myActionOptionsListener) {
        String actionClassName = ACTION_PACKAGE_PREFIX + myActionOptionsListener.get(ACTION).getValue()+ ACTION_KEY;
        try {
            return (Action)Reflection.createInstance(actionClassName, NumericAction.ModifyType.valueOf(myActionOptionsListener.get(MODIFIER).getValue()),
                    Double.parseDouble(myActionOptionsListener.get(VALUE).getValue()));
        }
        catch(Exception nonNumericAction){
            try {
                return (Action)Reflection.createInstance(actionClassName,myActionOptionsListener.get(VALUE).getValue()) ;
            }
            catch(Exception e){
                UIException myException = new UIException(myErrors.getString(ERROR_TWO_KEY));
                myException.displayUIException();
            }
        }
        return new HeightAction(NumericAction.ModifyType.RELATIVE,0.0);
    }
}

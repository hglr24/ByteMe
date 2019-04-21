package ui.panes;
import engine.external.actions.Action;
import engine.external.actions.NumericAction;
import engine.external.conditions.Condition;
import engine.external.conditions.ConditionType;
import engine.external.events.Event;
import events.EventFactory;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Constructor;
import java.util.*;

public class EventOptionsPane extends VBox {
    private static final String CONTROLS = "event_controls";
    private static final String DISPLAY = "event_classes";

    private static final String CONTROL_SEPARATOR = "::";
    private static final String PARAMETER_SEPARATOR = ",";

    private static final ResourceBundle CONTROLS_RESOURCES = ResourceBundle.getBundle(CONTROLS);
    private static final ResourceBundle DISPLAY_RESOURCES = ResourceBundle.getBundle(DISPLAY);

    private EventFactory myEventFactory = new EventFactory();
    private ArrayList<StringProperty> myEventOptionsListener = new ArrayList<>();
    private ArrayList<StringProperty> myActionOptionsListener = new ArrayList<>();

    public EventOptionsPane(){
        this.setMinSize(500,140);
        this.setMaxSize(500,140);
        this.setSpacing(30);
    }

    public void displayEventOptions(String eventName) {
        this.getChildren().clear();
        myEventOptionsListener.clear();
        HBox eventOptionsPanel = new HBox();
        myEventFactory.factoryDelegator(CONTROLS,eventName,eventOptionsPanel,myEventOptionsListener);
        this.getChildren().add(eventOptionsPanel);
        this.getChildren().add(myEventFactory.createActionsOptions(myActionOptionsListener));
    }

    Event saveEvent(String entityName, String eventName){
        Event userMadeEvent = new Event("object1");
        Class eventClass = null;
        Constructor eventConstructor;
        Object[] eventConstructorParameters;
//
        try {
            //Collision case

            eventClass = Class.forName(DISPLAY_RESOURCES.getString(eventName));
            eventConstructor = eventClass.getConstructor(String.class,String.class);
            eventConstructorParameters = new Object[2];
            eventConstructorParameters[0] = entityName; // @TODO Get Entity name here
            eventConstructorParameters[1] = myEventOptionsListener.get(0).getValue();
            userMadeEvent = (Event)eventConstructor.newInstance(eventConstructorParameters);
            //This should be the collidee name if eventCollision was selected
        }
        catch (Exception e) {
            try {
                //GeneralCase

                eventConstructor = eventClass.getConstructor(String.class);
                eventConstructorParameters = new Object[1];
                eventConstructorParameters[0] = entityName;
                userMadeEvent = (Event)eventConstructor.newInstance(eventConstructorParameters);

                Class conditionClass;
                Constructor conditionConstructor;
                Object[] conditionConstructorParameters;

                ConditionType myConditionType = ConditionType.valueOf(myEventOptionsListener.get(1).getValue().replaceAll(" ",""));
                // Component < Value, need <
                conditionClass = myConditionType.getClassName();

                conditionConstructor = conditionClass.getConstructor(Class.class,Double.class);// @TODO Handle different condition constructors

                conditionConstructorParameters = new Object[2];
                conditionConstructorParameters[0] = Class.forName("engine.external.component." + myEventOptionsListener.get(0).getValue() + "Component");
                //need the class name of the component
                conditionConstructorParameters[1] = Double.parseDouble(myEventOptionsListener.get(2).getValue());
                //need the value corresponding to it
                Condition userMadeCondition =  (Condition)conditionConstructor.newInstance(conditionConstructorParameters);
                userMadeEvent.addConditions(userMadeCondition);

            }
            catch (Exception e2) {
                System.out.println("NO STRING OR ZERO PARAMETER CONSTRUCTOR OR EVENT DOESN'T EXIST");
            }
        }
        try {

            Class actionClass = Class.forName("engine.external.actions." + myActionOptionsListener.get(0).getValue()+ "Action");
            Constructor actionConstructor = actionClass.getConstructor(NumericAction.ModifyType.class,Double.class); //@TODO handle different action constructors
            Object[] actionConstructorParameters = {NumericAction.ModifyType.valueOf(myActionOptionsListener.get(1).getValue()),
                    Double.parseDouble(myActionOptionsListener.get(2).getValue())};
            Action userMadeAction = (Action)actionConstructor.newInstance(actionConstructorParameters);
            userMadeEvent.addActions(userMadeAction);

        }
        catch(Exception e){
            System.out.println("ACTION CREATED INCORRECTLY");
        }
       return userMadeEvent;
    }


}

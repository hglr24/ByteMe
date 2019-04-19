package EngineExampleCode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * Component for handling collision and touching engine.external.events
 * Interacts with CollisionManager and TouchManager
 */
public class CollisionComponent extends Component {

    private final ResourceBundle COLLISION_ACTION_RESOURCE = ResourceBundle.getBundle("doc/plan/usecases/CollisionEventAction.properties");
    private final ResourceBundle TOUCH_ACTION_RESOURCE = ResourceBundle.getBundle("doc/plan/usecases/TouchEventAction.properties");
    private final ResourceBundle ACTION_RESOURCE = ResourceBundle.getBundle("doc/plan/usecases/Actions.properties");

    public CollisionComponent(GameObject o){
        super(o);
    }

    /**
     * Look up the properties file for collision engine.external.events to find engine.external.actions mapped to a collision between this and the other GameObject
     * Concatenate the UniqueIDs of two GameObjects as the key for looking up the specific collision event
     * @param otherObject the other GameObject with which the collision happens
     */
    public void collide(GameObject otherObject) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] actions = COLLISION_ACTION_RESOURCE.getStringArray(generateEventKey(otherObject));
        executeActions(actions);
    }

    /**
     * Look up the properties file for touching engine.external.events to find engine.external.actions mapped to touching between this and the other GameObject
     * Concatenate the UniqueIDs of two GameObjects as the key for looking up the specific touching event
     * @param otherObject the other GameObject with which the touching happens
     */
    public void touch(GameObject otherObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String[] actions = TOUCH_ACTION_RESOURCE.getStringArray(generateEventKey(otherObject));
        executeActions(actions);
    }

    /**
     * Each GameObject has a UniqueID field; different instances of GameObject of the same type share a common UniqueID
     * Every pair of UniqueID defines a unique event between two types of GameObjects, which can be used as a reference
     * when looking up the properties file to find engine.external.actions to perform
     * @param o
     * @return
     */
    private String generateEventKey(GameObject o){
        StringBuilder eventString = new StringBuilder();
        eventString.append(myGameObject.getMyUniqueID());
        eventString.append(o.getMyUniqueID());
        return eventString.toString();
    }

    /**
     * Actions are labeled by integer, and are mapped to certain methods in EngineExampleCode.ActionManager
     * EngineExampleCode.ActionManager has access to the this GameObject and EngineExampleCode.CollisionComponent
     * Implement reflection to make the call to EngineExampleCode.ActionManager so that the engine.external.actions can take effect
     * @param actions
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void executeActions(String[] actions) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(String label :actions){
            Method myMethod = myActionManager.getClass().getDeclaredMethod(ACTION_RESOURCE.getString(label));
            myMethod.invoke(myActionManager);
        }
    }

}

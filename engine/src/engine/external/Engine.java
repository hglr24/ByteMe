package engine.external;

import engine.external.component.*;
import engine.internal.systems.*;
import engine.internal.systems.VoogaSystem;
import javafx.scene.input.KeyCode;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Hsingchih Tang
 * Game Engine class which interacts with Game Runner to maintain an ongoing game. Manages and updates Component values
 * of all Entities, and invokes pre-defined Events for checking engine.external.conditions and triggering engine.external.actions on each game loop.
 */
public class Engine {
    private final ResourceBundle SYSTEM_COMPONENTS_RESOURCES = ResourceBundle.getBundle("SystemRequiredComponents");
    private final ResourceBundle SYSTEM_ORDER_RESOURCES = ResourceBundle.getBundle("SystemUpdateOrder");
    private final String SYSTEMS_PACKAGE_PATH = "engine.internal.systems.";
    private final String COMPONENTS_PACKAGE_PATH = "engine.external.component.";

    private HashMap<Integer,VoogaSystem> mySystems;
    protected Collection<Entity> myEntities;
    protected Collection<IEventEngine> myEvents;

    /**
     * An Engine is expected be initialized by a GameRunner and accepts a Level object containing all data (Entities and
     * Events) defined for a specific game level. Engine retrieves the collection of Entities and Events from Level, and
     * sets up concrete Systems for handling the Entities and Events in the game process
     * @param level Level object of the current game to run
     */
    public Engine(Level level){
        myEntities = level.getEntities();
        myEvents = level.getEvents();
        initSystemMap();
    }

    /**
     * Call expected to be made by GameRunner. Accepts a Collection of KeyCode inputs received from GameRunner on
     * the front end, and invokes all Systems one by one to update Entities' status and execute Events
     * @param inputs collection of user Keycode inputs received on this game loop
     * @return all game Entities after being updated by Systems in current game loop
     */
    public Collection<Entity> updateState(Collection<KeyCode> inputs){
        for(int i = 0; i<SYSTEM_ORDER_RESOURCES.keySet().size(); i++){
            mySystems.get(i).update(myEntities,inputs);
        }
        return this.getEntities();
    }

    /**
     * @return an unmodifiable collection of Entities within the currently running game
     */
    public Collection<Entity> getEntities(){
        return myEntities;
    }

    /**
     * Expected to be called by CleanupSystem for permanently removing an Entity from the running game
     * @param e Entity to be removed
     */
    public void removeEntity(Entity e){
        myEntities.remove(e);
    }

    private void initSystemMap() {
        mySystems = new HashMap<>();
        Enumeration<String> enumSystems= SYSTEM_COMPONENTS_RESOURCES.getKeys();
        while (enumSystems.hasMoreElements()){
            initSystem(enumSystems.nextElement());
        }
    }

    private void initSystem(String systemName) {
        Class systemClazz = Class.forName(this.getClass().getModule(),SYSTEMS_PACKAGE_PATH+systemName);
        Collection<Class<?extends Component>> systemComponents = retrieveComponentClasses(systemName);
        try {
            if (systemClazz == EventHandlerSystem.class) {
                mySystems.put(Integer.valueOf(SYSTEM_ORDER_RESOURCES.getString(systemName)), (VoogaSystem) systemClazz.getConstructor(new Class[]{Collection.class, Engine.class, Collection.class}).newInstance(systemComponents, this, myEvents));
            } else {
                mySystems.put(Integer.valueOf(SYSTEM_ORDER_RESOURCES.getString(systemName)), (VoogaSystem) systemClazz.getConstructor(new Class[]{Collection.class, Engine.class}).newInstance(systemComponents, this));
            }
        }catch(NoSuchMethodException|InstantiationException|IllegalAccessException e){
            System.out.println("Invalid reflection instantiation call on System: "+systemName);
        }catch(InvocationTargetException e){
            System.out.println("Exception occurred in constructor of "+systemName);
        }
    }

    @SuppressWarnings({"unchecked"})
    private Collection<Class<? extends Component>> retrieveComponentClasses(String systemName) {
        String[] componentArr = SYSTEM_COMPONENTS_RESOURCES.getString(systemName).split(",");
        ArrayList<Class<? extends Component>> componentList = new ArrayList<>();
        for(String component:componentArr){
            componentList.add((Class) Class.forName(this.getClass().getModule(),COMPONENTS_PACKAGE_PATH+component));
        }
        return componentList;
    }
}

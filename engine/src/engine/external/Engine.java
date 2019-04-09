package engine.external;

import engine.external.component.*;
import engine.internal.systems.*;
import engine.internal.systems.VoogaSystem;
import javafx.scene.input.KeyCode;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Engine {
    private final ResourceBundle SYSTEM_COMPONENTS_RESOURCES = ResourceBundle.getBundle("SystemRequiredComponents");
    private final ResourceBundle SYSTEM_ORDER_RESOURCES = ResourceBundle.getBundle("SystemUpdateOrder");
    private final String SYSTEMS_PACKAGE_PATH = "engine.internal.systems.";

    protected Collection<Entity> myEntities;
    protected Collection<IEventEngine> myEvents;
    private HashMap<Integer,VoogaSystem> mySystems;

    public Engine(Level level){
        myEntities = level.getEntities();
        myEvents = level.getEvents();
        initSystemMap();
    }

    public Collection<Entity> updateState(Collection<KeyCode> inputs){
        for(int i = 0; i<SYSTEM_ORDER_RESOURCES.keySet().size(); i++){
            mySystems.get(i).update(myEntities,inputs);
        }
        return this.getEntities();
    }

    public Collection<Entity> getEntities(){
        return Collections.unmodifiableCollection(myEntities);
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
            componentList.add((Class<? extends Component>)Class.forName(this.getClass().getModule(),component));
        }
        return componentList;
    }
}

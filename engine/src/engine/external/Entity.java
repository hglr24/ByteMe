package engine.external;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private Map<Class<?>, Component<?>> myComponents;

    public Entity() {
        myComponents = new HashMap<>();
    }

    public void addComponent(Component<?> component) {
        myComponents.put(component.getClass(), component);
    }

    public void removeComponent(Component<?> component){
        myComponents.remove(component.getClass());
    }

    /*
     * Added this in to test to see whether I was correctly reloading the components in of an entity - can certainly
     * delete later on
     * @author Anna
     */
    public void printMyComponents(){
        for (Component<?> c: myComponents.values())
            System.out.println(c.getValue());
    }
}

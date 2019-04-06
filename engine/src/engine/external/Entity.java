package engine.external;

import engine.external.component.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Entity {

    private Map<Class<? extends Component>, Component<?>> myComponents;

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

    public boolean hasComponents(Collection<Class<? extends Component>> components) {
        return myComponents.keySet().containsAll(components);
    }

    public boolean hasComponents(Class<? extends Component> component) {
        return hasComponents(Arrays.asList(component));
    }

    public Component<?> getComponent(Class<? extends Component> clazz) {
        return myComponents.get(clazz);
    }


}

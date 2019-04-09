package engine.external;

import engine.external.component.Component;

import java.io.Serializable;
import java.util.*;

public class Entity implements Serializable {

    private Map<Class<? extends Component>, Component<?>> myComponents;

    public Entity() {
        myComponents = new HashMap<>();
    }

    public void addComponent(Collection<Component<?>> components) {
        for (Component<?> component: components) {
            myComponents.put(component.getClass(), component);
        }
    }

    public void addComponent(Component<?> component) {
        addComponent(Arrays.asList(component));
    }

    public void removeComponent(Collection<Component<?>> components){
        for (Component<?> component: components) {
            myComponents.remove(component.getClass());
        }
    }

    public void removeComponent(Component<?> component) {
        removeComponent(Arrays.asList(component));
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

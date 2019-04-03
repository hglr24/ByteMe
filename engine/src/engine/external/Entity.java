package engine.external;

import engine.external.component.Component;

import java.util.Arrays;
import java.util.Collection;
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

    public boolean hasComponents(Collection<Class<? extends Component>> components) {
        return myComponents.keySet().containsAll(components);
    }

    public boolean hasComponents(Class<? extends Component> component) {
        return hasComponents(Arrays.asList(component));
    }


}

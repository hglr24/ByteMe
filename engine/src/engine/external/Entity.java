package engine.external;

import engine.external.component.Component;
import engine.external.component.WidthComponent;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lucas Liu
 *
 * The Entity is the standard element of any game. An Entity can represent a player, an obstacle, an enemy, or any other game element, even
 * abstract elements such as a "camera" that follows the player. Entities are composed of Components. The entirety of an Entity's behavior and
 * State is encapsulated by its Components.
 *
 * The Entity is a powerful abstraction that is simple to implement. Methods are simply basic getters and setters, but the contract is
 * constrained by the clever use of a map that takes Component class type as a key, and the actual instance of that component as the value. Each
 * Entity can only have one of each type of Component. This organization makes modifications of State easy (which happens quite frequently in a
 * game). The map also makes querying for relevant Components relatively streamlined.
 */
public class Entity implements Serializable {

    private Map<Class<? extends Component>, Component<?>> myComponents;

    public Entity() {
        myComponents = new HashMap<>();
    }

    public Entity copyEntity() {
        Entity newEntity = new Entity();
        for (Component c: this.myComponents.values()) {
            try {
                if(!c.getClass().equals(WidthComponent.class)){
                    Component newComponent = (Component) c.getClass().getConstructor(c.getValue().getClass()).newInstance(c.getValue());
                    newEntity.myComponents.put((Class) c.getClass() ,newComponent);
                }else{
                    newEntity.myComponents.put(WidthComponent.class, new WidthComponent((Double)c.getValue()+1));
                }
            }
            catch (Exception e) {
                //Do nothing
                System.out.println("Unable to copyEntity this entity");
            }
        }
        return newEntity;
    }


    public void addComponent(Collection<Component<?>> components) {
        for (Component<?> component: components) {
            myComponents.put(component.getClass(), component);
        }
    }

    public void addComponent(Component<?> component) {
        addComponent(Arrays.asList(component));
    }

    public void removeComponent(Collection<Class<? extends Component>> componentClazzes){
        for (Class<? extends Component> clazz: componentClazzes) {
            myComponents.remove(clazz);
        }
    }

    public void removeComponent(Class<? extends Component> componentClazz) {
        removeComponent(Arrays.asList(componentClazz));
    }

    /**
     * Added this in to test to see whether I was correctly reloading the components in of an entity - can certainly
     * delete later on
     * @author Anna
     */
    public void printMyComponents(){
        for (Component<?> c: myComponents.values()) {
            System.out.println(c.getValue());
        }
    }

    /**
     * Check if Entity has the specified requirements. Useful for filtering for only Entities that meet the necessary criteria
     * @param components
     * @return
     */
    public boolean hasComponents(Collection<Class<? extends Component>> components) {
        return myComponents.keySet().containsAll(components);
    }

    public boolean hasComponents(Class<? extends Component> component) {
        return hasComponents(Arrays.asList(component));
    }

    public Component<?> getComponent(Class<? extends Component> clazz) {
        return myComponents.get(clazz);
    }

    public Map<Class<? extends Component>, Component<?>> getComponentMap(){
        return myComponents;
    }


}

package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.Engine;
import engine.external.component.DestroyComponent;

import java.util.Collection;

/**
 * @author Hsingchih Tang
 * Checks the HealthComponent of every matching Entity, and mark the Entities whose HealthComponent values fall
 * below 0 as destroyable, such that CleanupSystem will remove those Entities from Engine at the end of game loop.
 */
public class HealthSystem extends VoogaSystem {

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public HealthSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    /**
     * Loops through all Entities equipped with a HealthComponent, checks the value stored in the Component, and sets
     * its DestroyComponent value to true or add a new DestroyComponent to the Entity if health value is less than 0
     */
    @Override
    protected void run() {
        for(Entity e:this.getEntities()){
            if((Double)getComponentValue(HEALTH_COMPONENT_CLASS,e)<0){
                if(e.hasComponents(DESTROY_COMPONENT_CLASS)){
                    ((DestroyComponent)e.getComponent(DESTROY_COMPONENT_CLASS)).setValue(true);
                }else{
                    e.addComponent(new DestroyComponent(true));
                }
            }
        }
    }
}

package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.Engine;
import engine.external.component.LeftCollidedComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Hsingchih Tang
 * Garbage Collection System to be invoked at the end of each game loop.
 * Checks all Entities' DestroyComponent, and permanantly removes those
 * that have been marked to destroy from the Engine's collection of Entities.
 */
public class CleanupSystem extends VoogaSystem {

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public CleanupSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    /**
     * Loops through the collection of Entities, removes any CollidedComponent, probes each Entity's DestroyComponent,
     * and notifies Engine to get rid of all Entities whose DestroyComponent hold "true" value
     */
    @Override
    protected void run() {
        for(Entity entity:this.getEntities()){
            entity.removeComponent(Arrays.asList(LEFT_COLLIDED_COMPONENT_CLASS,RIGHT_COLLIDED_COMPONENT_CLASS,TOP_COLLIDED_COMPONENT_CLASS,BOTTOM_COLLIDED_COMPONENT_CLASS,ANY_COLLIDED_COMPONENT_CLASS));
            if(entity.hasComponents(DESTROY_COMPONENT_CLASS)&&getBooleanComponentValue(DESTROY_COMPONENT_CLASS,entity)){
                myEngine.removeEntity(entity);
            }
        }
    }
}

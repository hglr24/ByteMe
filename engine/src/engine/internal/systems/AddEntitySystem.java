package engine.internal.systems;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.SpawnEntityComponent;

import java.util.Collection;

/**
 *
 * @author Dima Fayyad
 * @author Lucas Liu
 * Add Entity System to be invoked to add entities during the game.
 * Checks all Entities' SpawnEntityComponent, and adds the entity. Then destroys
 */
public class AddEntitySystem extends VoogaSystem {

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public AddEntitySystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    /**
     * Loops through the collection of Entities, adds entity stored in removes any CollidedComponent
     */
    @Override
    protected void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(SPAWN_ENTITY_COMPONENT_CLASS)){
                Entity newEntity = ((Entity)entity.getComponent(SpawnEntityComponent.class).getValue()).copyEntity();
                myEngine.addEntity(newEntity);
                entity.removeComponent(SPAWN_ENTITY_COMPONENT_CLASS);
            }
        }
    }
}

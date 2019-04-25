package engine.internal.systems;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.DestroyComponent;
import voogasalad.util.reflection.Reflection;
import voogasalad.util.reflection.ReflectionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

import static engine.external.Engine.COMPONENTS_PACKAGE_PATH;

/**
 * @author Hsingchih Tang
 * Cleans up all unserializable Components that have been added to Entities by Engine
 * before Runner saves the Entities for a running game
 */
public class SaveGameSystem extends VoogaSystem {

    private Collection<Class<? extends Component>> myComponentsToRemove;

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine             the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public SaveGameSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    /**
     * In contrast to the other Systems, the update() call on SaveGameSystem is not invoked in every game loop,
     * but is only called by the System itself when Engine invokes getSavedEntities to save the status of a game
     */
    @Override
    protected void run() {
        for (Entity entity : this.getEntities()) {
            entity.removeComponent(myComponentsToRemove);
        }
    }

    /**
     * Receives a collection of Entities that are expected to be a copy of the original Entities in the game
     * and remove all Components of interest from the Entities
     * @param entities Entities to clean up components
     * @return the same Entities that have been passed in with Components cleaned up
     */
    public Collection<Entity> getSavedEntities(Collection<Entity> entities, Collection<Class<? extends Component>> componentClazz) {
        this.myComponentsToRemove = componentClazz;
        this.update(entities,new ArrayList<>());
        return this.getEntities();
    }


}

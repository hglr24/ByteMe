package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import runner.internal.LevelRunner;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract super class for all of runner's systems
 * Each runner system make's its own specific changes
 * to the level in progress
 * @author Louis Jensen
 */
public abstract class RunnerSystem {

    private Collection<Class<? extends Component>> myRequiredComponents;
    private Collection<Entity> myEntities;
    private LevelRunner myLevelRunner;

    /**
     * Constructor for super class of system
     * @param requiredComponents - all components an entity needs to have to be affected by the system
     * @param levelRunner - LevelRunner object so that system can modify the level
     */
    public RunnerSystem(Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner) {
        myRequiredComponents = requiredComponents;
        myLevelRunner = levelRunner;
    }

    /**
     * This method will be called on each system during each game loop
     * It gets all the entities that can be affected by the system
     * and then runs the system
     * @param entities - Collection of the entities gotten from the LevelRunner
     */
    public void update(Collection<Entity> entities) {
        myEntities = new ArrayList<>();
        for (Entity e: entities) {
            if (filter(e)) {
                myEntities.add(e);
            }
        }
        run();
    }

    /**
     * Filters entities to only run system on appropriate entities
     * @param entity - Entity passed from LevelRunner
     * @return Whether or not the entity has the desired components
     */
    public boolean filter(Entity entity){
        return entity.hasComponents(myRequiredComponents);
    }

    /**
     * Abstract method to be implemented differently by each system
     */
    public abstract void run();

    /**
     * Gets all the entities of that system
     * @return collection of entities handled by that system
     */
    public Collection<Entity> getEntities() {
        return myEntities;
    }

    /**
     * Gets the value of an entity's specific component
     * @param componentClazz - component to get value of
     * @param entity - entity to get component of
     * @return value of component
     */
    public Object getComponentValue(Class<? extends Component> componentClazz,Entity entity){
        return entity.getComponent(componentClazz).getValue();
    }

}

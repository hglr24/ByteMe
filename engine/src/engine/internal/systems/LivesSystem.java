package engine.internal.systems;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.ProgressionComponent;

import java.util.Collection;

/**
 * @Author Hsingchih Tang
 * Monitors the number of lives left for the running game. The number of lives should be shared across different Levels
 * of the same GameObject, and LivesSystem applies filtering to find the main Entity carrying the LivesComponent on the
 * first game loop. On every subsequent game loop, it checks the number of lives left, and defines the game as lost
 * when lives fall below zero, by attaching a ProgressionComponent storing false to the main Entity. When the Entity is
 * returned to Runner, Runner will notice player has lost the game and display the appropriate screen accordingly.
 */
public class LivesSystem extends VoogaSystem {

    private Entity myMainEntity;

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     *
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine             the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public LivesSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    /**
     * Find the unique "main Entity" carrying the LivesComponent for current Level and verify that
     * there are still lives left in the game on every game loop. If the number of lives falls
     * below zero, attach a false ProgressionComponent to the main Entity.
     */
    protected void run() {
        if(myMainEntity==null){
            myMainEntity = this.getEntities().iterator().next();
        }
        if(livesBelowZero()){
            myMainEntity.addComponent(new ProgressionComponent(false));
        }
    }

    private boolean livesBelowZero(){
        return (Double)getComponentValue(LIVES_COMPONENT_CLASS,myMainEntity)<0;
    }

}

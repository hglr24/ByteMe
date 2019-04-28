package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.LivesComponent;
import runner.internal.HeadsUpDisplay;
import runner.internal.LevelRunner;
import java.util.Collection;

/**
 * System that updates the Lives number in heads up display
 * @author Louis Jensen
 */
public class LivesSystem extends RunnerSystem {
    private HeadsUpDisplay myHUD;

    /**
     * Constructor for LivesSystem
     * @param requiredComponents - list of all components necessary for system
     * @param levelRunner - LevelRunner object so that system can modify the level
     * @param hud - Heads up display access so the system can modify it
     */
    public LivesSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner, HeadsUpDisplay hud) {
        super(requiredComponents, levelRunner);
        myHUD = hud;
    }

    /**
     * Updates the lives display in the HUD
     */
    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(LivesComponent.class)){
                displayLives(entity);
                break;
            }
        }
    }

    private void displayLives(Entity entity) {
        myHUD.updateLives((Double) entity.getComponent(LivesComponent.class).getValue());
    }
}
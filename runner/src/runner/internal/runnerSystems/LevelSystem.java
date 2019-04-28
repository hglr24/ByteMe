package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.NextLevelComponent;
import runner.internal.HeadsUpDisplay;
import runner.internal.LevelRunner;
import java.util.Collection;

/**
 * System that updates the Level number in heads up display
 * @author Louis Jensen
 */
public class LevelSystem extends RunnerSystem {
    private HeadsUpDisplay myHUD;
    private int myLevelCount;

    /**
     * Constructor for LevelSystem
     * @param requiredComponents - list of all components necessary for system
     * @param levelRunner - LevelRunner object so that system can modify the level
     * @param hud - Heads up display access so the system can modify it
     * @param numLevels - Number of levels in the game that is being played
     */
    public LevelSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner, HeadsUpDisplay hud, int numLevels) {
        super(requiredComponents, levelRunner);
        myHUD = hud;
        myLevelCount = numLevels;
    }

    /**
     * Updates the Level display in HUD
     */
    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(NextLevelComponent.class) && !(((Double) entity.getComponent(NextLevelComponent.class).getValue()).intValue() > myLevelCount)){
                displayLevel(entity);
                break;
            }
        }
    }

    private void displayLevel(Entity entity) {
        myHUD.updateLevel((Double) entity.getComponent(NextLevelComponent.class).getValue());
    }
}

package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.ScoreComponent;
import runner.internal.HeadsUpDisplay;
import runner.internal.LevelRunner;
import java.util.Collection;

/**
 * System that updates the score in the heads up display
 * @author Louis Jensen
 */
public class ScoringSystem extends RunnerSystem {
    private HeadsUpDisplay myHUD;

    /**
     * Constructor for ScoringSystem
     * @param requiredComponents - list of all components necessary for system
     * @param levelRunner - LevelRunner object so that system can modify the level
     * @param hud - Heads up display access so the system can modify it
     */
    public ScoringSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner, HeadsUpDisplay hud) {
        super(requiredComponents, levelRunner);
        myHUD = hud;
    }

    /**
     * Updates the Score value in the HUD
     */
    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(ScoreComponent.class)){
                displayScore(entity);
                break;
            }
        }
    }

    private void displayScore(Entity entity) {
        myHUD.updateScore((Double) entity.getComponent(ScoreComponent.class).getValue());
    }
}

package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.NextLevelComponent;
import engine.external.component.ProgressionComponent;
import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import runner.internal.GameBeatenScreen;
import runner.internal.HeadsUpDisplay;
import runner.internal.LevelRunner;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * System that checks if the game is over and updates accordingly
 * @author Louis Jensen
 */
public class NextLevelSystem extends RunnerSystem {
    private Group myGroup;
    private Stage myStage;
    private Animation myAnimation;
    private int myWidth;
    private int myHeight;
    private Consumer myLevelChanger;
    private int myLevelCount;
    private HeadsUpDisplay myHUD;

    /**
     * Constructor for the progression system
     * @param requiredComponents - list of all components necessary for system
     * @param levelRunner - LevelRunner object so that system can modify the level
     * @param group - Group so that system can modify things on screen
     * @param stage - Stage of level to be modified
     * @param animation - Timeline that runs game loop
     * @param width - width of screen
     * @param height - height of screen
     * @param consumer - allows the system to change level
     * @param numLevels - total number of levels in the game
     */
    public NextLevelSystem(Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner,
                          Group group, Stage stage, Animation animation, int width, int height,
                          Consumer consumer, int numLevels, HeadsUpDisplay hud) {
        super(requiredComponents, levelRunner);
        myGroup = group;
        myStage = stage;
        myAnimation = animation;
        myWidth = width;
        myHeight = height;
        myLevelChanger = consumer;
        myLevelCount = numLevels;
        myHUD = hud;
    }

    /**
     * Goes to next level or resets level if necessary
     */
    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(NextLevelComponent.class)){
                changeLevel(entity);
                break;
            }
        }
    }

    private void changeLevel(Entity entity){
        System.out.println(entity.getComponent(NextLevelComponent.class).getValue());
        Double nextLevel = (Double) entity.getComponent(NextLevelComponent.class).getValue();
        if(nextLevel.intValue() > myLevelCount){
        myAnimation.stop();
        myGroup.getChildren().add(new GameBeatenScreen(myStage, myGroup.getTranslateX(), (Boolean) entity.getComponent(ProgressionComponent.class).getValue()).getNode());
        } else {
            endLevel(nextLevel);
        }
    }

    private void endLevel(Double levelToProgressTo) {
        myGroup.getChildren().clear();
        myAnimation.stop();
        myStage.setScene(new Scene(new Group(), myWidth, myHeight));
        myHUD.updateLevel(levelToProgressTo);
        myLevelChanger.accept(levelToProgressTo);
    }
}

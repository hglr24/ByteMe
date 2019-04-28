package runner.internal;

import engine.external.component.*;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import runner.internal.runnerSystems.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Creates and stores all systems used by runner in the game loop
 * @author Louis Jensen
 */
public class SystemManager {
    private List<RunnerSystem> mySystems;
    private LevelRunner myLevelRunner;
    private Group myGroup;
    private Stage myStage;
    private Timeline myAnimation;
    private int mySceneWidth;
    private int mySceneHeight;
    private Consumer myLevelChanger;
    private Scene myScene;
    private HeadsUpDisplay myHUD;
    private AudioManager myAudioManger;
    private int myLevelCount;

    /**
     * Constructor for SystemManager
     * Must hold all things the various systems need to do their job
     * @param levelRunner
     * @param group
     * @param stage
     * @param animation
     * @param width
     * @param height
     * @param changer
     * @param scene
     * @param hud
     * @param audioManager
     * @param numLevels
     */
    public SystemManager(LevelRunner levelRunner, Group group, Stage stage, Timeline animation,
                         int width, int height, Consumer changer, Scene scene, HeadsUpDisplay hud,
                         AudioManager audioManager, int numLevels){
        myLevelRunner = levelRunner;
        myGroup = group;
        myStage = stage;
        myAnimation = animation;
        mySceneWidth = width;
        mySceneHeight = height;
        myLevelChanger = changer;
        myScene = scene;
        myHUD = hud;
        myAudioManger = audioManager;
        myLevelCount = numLevels;
        mySystems = new ArrayList<>();
        addSystems();
    }

    private void addSystems() {
        Collection<Class<? extends Component>> components2 = new ArrayList<>();
        components2.add(CameraComponent.class);
        mySystems.add(new ScrollingSystem(components2, myLevelRunner, myGroup, myScene));

        Collection<Class<? extends Component>> components3 = new ArrayList<>();
        components3.add(ImageViewComponent.class);
        mySystems.add(new ImageDisplaySystem(components3, myLevelRunner, myGroup));

        Collection<Class<? extends Component>> components4 = new ArrayList<>();
        components4.add(ScoreComponent.class);
        mySystems.add(new ScoringSystem(components4, myLevelRunner, myHUD));

        Collection<Class<? extends Component>> components5 = new ArrayList<>();
        components5.add(ScoreComponent.class);
        mySystems.add(new LivesSystem(components5, myLevelRunner, myHUD));

        Collection<Class<? extends Component>> components6 = new ArrayList<>();
        components6.add(NextLevelComponent.class);
        mySystems.add(new LevelSystem(components6, myLevelRunner, myHUD, myLevelCount));

        Collection<Class<? extends Component>> components7 = new ArrayList<>();
        components7.add(PlayAudioComponent.class);
        mySystems.add(new SoundSystem(components7, myLevelRunner, myAudioManger));

        Collection<Class<? extends Component>> components = new ArrayList<>();
        components.add(ProgressionComponent.class);
        components.add(NextLevelComponent.class);
        mySystems.add(new ProgressionSystem(components, myLevelRunner,
                myGroup, myStage, myAnimation, mySceneWidth,
                mySceneHeight, myLevelChanger, myLevelCount));

        mySystems.add(new PizzaSystem(new ArrayList<>(){{add(PizzaComponent.class);}}, myLevelRunner));
    }

    /**
     * Gets all the systems
     * @return List of Systems so that LevelRunner can access them all to update level
     */
    public List<RunnerSystem> getSystems(){
        return mySystems;
    }
}

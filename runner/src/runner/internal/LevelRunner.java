package runner.internal;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.Level;
import engine.external.component.NameComponent;
import engine.external.component.TimerComponent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import runner.internal.runnerSystems.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * LevelRunner runs the game loop and displays level on screen
 * @author Louis Jensen
 */
public class LevelRunner {
    private PauseButton myPauseButton;
    private Node myPause;
    private Collection<Entity> myEntities;
    private int mySceneWidth;
    private int mySceneHeight;
    private Stage myStage;
    private Group myGroup;
    private Scene myScene;
    private Engine myEngine;
    private Timeline myAnimation;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Set<KeyCode> myCurrentKeys;
    private boolean canPause = false;
    private Consumer<Double> myLevelChanger;
    private List<RunnerSystem> mySystems;
    private HeadsUpDisplay myHUD;
    private Text myLabel;
    private Rectangle myHudBackground;
    private int myLevelCount;
    private AudioManager myAudioManager;

    /**
     * Constructor for level runner
     * @param level - current level to be played
     * @param width - width of screen
     * @param height - height of screen
     * @param stage - stage to create level on
     * @param playNext - consumer to change levels
     * @param numLevels - total number of levels in the current game
     */
    public LevelRunner(Level level, int width, int height, Stage stage, Consumer playNext, int numLevels){
        myLevelCount = numLevels;
        mySceneWidth = width;
        mySceneHeight = height;
        myCurrentKeys = new HashSet<>();
        myEngine = new Engine(level);
        myHUD = new HeadsUpDisplay(width);
        myEntities = myEngine.updateState(myCurrentKeys);
        myAudioManager = new AudioManager(5);
        myLevelChanger = playNext;
        myAnimation = new Timeline();
        buildStage(stage);
        startAnimation();
        addButtonsAndHUD();
        myStage.show();
    }

    private void initializeSystems() {
        SystemManager systems = new SystemManager(this, myGroup, myStage, myAnimation,
                mySceneWidth, mySceneHeight, myLevelChanger, myScene, myHUD, myAudioManager, myLevelCount);
        mySystems = systems.getSystems();
    }

    private void addButtonsAndHUD() {
        myHudBackground = new Rectangle(0,0,mySceneWidth, 40);
        myGroup.getChildren().add(myHudBackground);
        myPauseButton = new PauseButton(this, myAnimation, myGroup, myStage, myAudioManager);
        myPause = myPauseButton.getPauseButton();
        myGroup.getChildren().add(myPause);
        canPause = true;
        myLabel = myHUD.getLabel();
        myGroup.getChildren().add(myLabel);
    }

    private void buildStage(Stage stage) {
        myStage = stage;
        myStage.setResizable(false);
        myGroup = new Group();
        myScene = new Scene(myGroup, mySceneWidth, mySceneHeight);
        myScene.setFill(Color.BEIGE);
        myScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        myScene.getStylesheets().add("runnerStyle.css");
        myScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Gugi");
        initializeSystems();
        updateGUI();
        myStage.setScene(myScene);
    }

    private void startAnimation(){
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    private void handleKeyPress(KeyCode code) {
        myCurrentKeys.add(code);
    }

    private void handleKeyRelease(KeyCode code){
        myCurrentKeys.remove(code);
    }

    private void step (double elapsedTime) {
        myEntities = myEngine.updateState(myCurrentKeys);
        for (Entity entity : myEntities) {
            if (entity.hasComponents(TimerComponent.class)) {
                System.out.println("name: " + entity.getComponent(NameComponent.class).getValue() + " time: " + entity.getComponent(TimerComponent.class).getValue());
            }
        }

        updateGUI();
    }

    private void updateGUI(){
        myGroup.getChildren().retainAll(myPause, myLabel, myHudBackground);
        for(RunnerSystem system : mySystems){
            system.update(myEntities);
            System.out.println(system);
        }
        if (canPause) updateButtonsAndHUD();
    }

    private void updateButtonsAndHUD(){
        myPause.setLayoutX(myPauseButton.getButtonX() - myGroup.getTranslateX());
        myLabel.setLayoutX(myHUD.getX() - myGroup.getTranslateX());
        myHudBackground.setLayoutX(myHudBackground.getX() - myGroup.getTranslateX());
        myHUD.updateLabel();
    }

    /**
     * Gets entities currently in the level
     * @return Collection of current entities
     */
    public Collection<Entity> getEntities(){
        return myEntities;
    }

    /**
     * Gets the engine being used to create level
     * @return Engine
     */
    public Engine getEngine(){
        return myEngine;
    }
}
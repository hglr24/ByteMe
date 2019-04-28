package runner.internal;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.Level;
import engine.external.component.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.function.Consumer;

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
    private Level myLevel;
    private Set<KeyCode> myCurrentKeys;
    private boolean canPause = false;
    private Consumer<Double> myLevelChanger;

    private AudioManager myAudioManager;

    public LevelRunner(Level level, int width, int height, Stage stage, Consumer playNext){
        myLevel = level;
        mySceneWidth = width;
        mySceneHeight = height;
        myCurrentKeys = new HashSet<>();
        myEngine = new Engine(level);
        myEntities = myEngine.updateState(myCurrentKeys);
        myAudioManager = new AudioManager(5);
        myLevelChanger = playNext;
        buildStage(stage);
        startAnimation();
        addPauseButton();
        myStage.show();
    }

    private void addPauseButton() {
        myPauseButton = new PauseButton(myAnimation);
        myPause = myPauseButton.getPauseButton();
        myGroup.getChildren().add(myPause);
        canPause = true;
    }

    private void buildStage(Stage stage) {
        myStage = stage;
        myGroup = new Group();
        myScene = new Scene(myGroup, mySceneWidth, mySceneHeight);
        myScene.setFill(Color.BEIGE);
        myScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        showEntities();
        myStage.setScene(myScene);
    }

    private void startAnimation(){
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation = new Timeline();
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
        showEntities();
        printKeys();
        //printEntityLocations();
    }

    private void printKeys() {
        System.out.println(myCurrentKeys);
    }

    protected List<Double> getXYZasList(Entity entity){
        List<Double> list = new ArrayList<>();
        XPositionComponent xPositionComponent = (XPositionComponent) entity.getComponent(XPositionComponent.class);
        Double xPosition = xPositionComponent.getValue();
        YPositionComponent yPositionComponent = (YPositionComponent) entity.getComponent(YPositionComponent.class);
        Double yPosition = yPositionComponent.getValue();
        ZPositionComponent zPositionComponent = (ZPositionComponent) entity.getComponent(ZPositionComponent.class);
        Double zPosition = zPositionComponent.getValue();
        list.add(xPosition);
        list.add(yPosition);
        list.add(zPosition);
        return list;
    }

    private void printEntityLocations(){
        for(Entity entity : myEntities){
            List<Double> xyz = getXYZasList(entity);
            System.out.println(xyz);
        }
    }

    private void showEntities(){
        myGroup.getChildren().retainAll(myPause);
        //myGroup.getChildren().clear();
        for(Entity entity : myEntities){
            if (entity.hasComponents(PlayAudioComponent.class)) {
                myAudioManager.playSound(entity);
            }
            try {
                System.out.println(entity.getComponent(NextLevelComponent.class).getValue());
                System.out.println(entity.getComponent(ProgressionComponent.class).getValue());
            } catch (Exception e){
                //do nothing
            }
            if (entity.hasComponents(ScoreComponent.class)) {
                System.out.println("Score: " + entity.getComponent(ScoreComponent.class).getValue());
            }
            if(entity.hasComponents(ProgressionComponent.class) && (Boolean) entity.getComponent(ProgressionComponent.class).getValue()){
                System.out.println(entity.getComponent(NextLevelComponent.class).getValue());
                System.out.println(entity.getComponent(ProgressionComponent.class).getValue());
                Double nextLevel = (Double) entity.getComponent(NextLevelComponent.class).getValue();
                for (Component<?> component : entity.getComponentMap().values()){
                    component.resetToOriginal();
                }
                try {
                    endLevel(nextLevel);
                } catch (IndexOutOfBoundsException e){
                    System.out.println("GAME BEATEN");
                }
                break;
            }
            if(entity.hasComponents(CameraComponent.class)){
                scrollOnMainCharacter(entity);
            }
            ImageViewComponent imageViewComponent = (ImageViewComponent) entity.getComponent(ImageViewComponent.class);
            try {
                ImageView image = imageViewComponent.getValue();
                myGroup.getChildren().add(image);
            }
            catch (NullPointerException e) {
                 //TODO fix this
            }

        }
        if (canPause) {
            movePauseButton();
        }
    }

    private void endLevel(Double levelToProgressTo) {
        System.out.println(myAnimation);
        myGroup.getChildren().clear();
        myAnimation.stop();
        myStage.setScene(new Scene(new Group(), mySceneWidth, mySceneHeight));
        myLevelChanger.accept(levelToProgressTo);
    }

    private void movePauseButton(){
        myPause.setLayoutX(myPauseButton.getButtonX() - myGroup.getTranslateX());
    }

    private void scrollOnMainCharacter(Entity entity){
        Double x = (Double) entity.getComponent(XPositionComponent.class).getValue();
        Double origin = myGroup.getTranslateX();
        Double xMinBoundary = myScene.getWidth()/5.0;
        Double xMaxBoundary = myScene.getWidth()/4.0*3;
        if (x < xMinBoundary - origin) {
            myGroup.setTranslateX(-1 * x + xMinBoundary);
        }
        if (x > xMaxBoundary - origin) {
            myGroup.setTranslateX(-1 * x + xMaxBoundary);
        }
    }
}

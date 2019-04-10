package runner.external;

import data.external.DataManager;
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
import runner.internal.DummyGameObjectMaker;
import runner.internal.TestEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameRunner {
    /**
     * This will be the primary class that creates a new game engine
     * and displays sprites on a stage
     */
    private Collection<Entity> myEntities;
    private int mySceneWidth;
    private int mySceneHeight;
    private Stage myStage;
    private Group myGroup;
    private Scene myScene;
    private Engine myEngine;
    //private Engine myEngine;
    private Timeline myAnimation;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Map myEntitiesAndNodes;
    private List<Level> myLevels;
    private Game myGame;
    private Set<KeyCode> myCurrentKeys;



    public GameRunner(String game) throws FileNotFoundException{
       /* Actual way to get game object
        GameRunner will have parameter String name, not Game game
        code below */

        DummyGameObjectMaker dm2 = new DummyGameObjectMaker();
        dm2.serializeObject();
        Game gameMade = dm2.getGame(game);

        DataManager dm = new DataManager();

        myGame = (Game) dm.loadGameData(game);



        myCurrentKeys = new HashSet<KeyCode>();
        myLevels = myGame.getLevels();
        myEngine = new Engine(myLevels.get(0));
        myEntities = myEngine.updateState(myCurrentKeys);
        mySceneWidth = myGame.getWidth();
        mySceneHeight = myGame.getHeight();
        myStage = new Stage();
        myGroup = new Group();
        myScene = new Scene(myGroup, mySceneWidth, mySceneHeight);
        myScene.setFill(Color.BEIGE);
        myScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
       // myEntitiesAndNodes = initializeMap();
        showEntities();

        myStage.setScene(myScene);

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
        myStage.show();
    }

    private void doNothing(){}

    private void handleKeyPress(KeyCode code) {
        myCurrentKeys.add(code);
    }

    private void handleKeyRelease(KeyCode code){
        myCurrentKeys.remove(code);
    }

    private HashMap<Entity, Node> initializeMap() {
        HashMap<Entity, Node> map = new HashMap<Entity, Node>();
        for(Entity entity : myEntities){
            //Add Error checking to set defaults

            List<Double> xyz = getXYZasList(entity);
            WidthComponent widthComponent = (WidthComponent) entity.getComponent(WidthComponent.class);
//            Double width = (Double) widthComponent.getValue();
//            HeightComponent heightComponent = (HeightComponent) entity.getComponent(HeightComponent.class);
//            Double height = (Double) heightComponent.getValue();
//            //ImageViewComponent imageViewComponent = (ImageViewComponent) entity.getComponent(ImageViewComponent.class);
//            //ImageView image = (ImageView) imageViewComponent.getValue();
//            ImageView image = new ImageView("basketball.png");
//
//            image.setFitWidth(width);
//            image.setFitHeight(height);
////            image.setSmooth(false);
//            image.setLayoutX(xyz.get(0));
//            image.setLayoutY(xyz.get(1));
//
//            map.put(entity, image);

        }
        return map;
    }

    private void step (double elapsedTime) {
        myEntities = myEngine.updateState(myCurrentKeys);
       // updateMap();
        showEntities();
        printKeys();
        printEntityLocations();
    }

    private void printKeys() {
        System.out.println(myCurrentKeys);
    }

    private void updateMap(){
        for(Entity entity : myEntities){
            myEntitiesAndNodes.put(entity, updateNode(entity));
        }
    }

    private Node updateNode(Entity entity) {
        Node toUpdate = (ImageView) entity.getComponent(ImageViewComponent.class).getValue();


        List<Double> xyz = getXYZasList(entity);

        toUpdate.setLayoutX(xyz.get(0));
        toUpdate.setLayoutY(xyz.get(1));

        return toUpdate;
    }

    protected List<Double> getXYZasList(Entity entity){
        List<Double> list = new ArrayList<>();
        XPositionComponent xPositionComponent = (XPositionComponent) entity.getComponent(XPositionComponent.class);
        Double xPosition = (Double) xPositionComponent.getValue();
        YPositionComponent yPositionComponent = (YPositionComponent) entity.getComponent(YPositionComponent.class);
        Double yPosition = (Double) yPositionComponent.getValue();
        ZPositionComponent zPositionComponent = (ZPositionComponent) entity.getComponent(ZPositionComponent.class);
        Double zPosition = (Double) zPositionComponent.getValue();
        list.add(xPosition);
        list.add(yPosition);
        list.add(zPosition);
        return list;
    }

    private void printEntityLocations(){
        for(Entity entity : myEntities){
            List<Double> xyz = getXYZasList(entity);
            System.out.println(xyz);
            //TODO: refactor code to use XPositionComponent, YPositionComponent, ZPositionComponent :)

//            PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
//            Point3D position = (Point3D) positionComponent.getValue();
//            VoogaSystem.out.println(position.getX());
        }
    }

    private void showEntities(){
        myGroup.getChildren().clear();
        for(Entity entity : myEntities){

//            Node toAdd = (Node) myEntitiesAndNodes.get(entity);
//            System.out.println("begin");
//            System.out.println(myGroup);
//            System.out.println(myGroup.getChildren());
//            System.out.println(myEntitiesAndNodes);
//            System.out.println(myEntitiesAndNodes.get(entity));
//            System.out.println(entity);
//            System.out.println("end");
//            myGroup.getChildren().add((Node) myEntitiesAndNodes.get(entity));
            ImageViewComponent imageViewComponent = (ImageViewComponent) entity.getComponent(ImageViewComponent.class);
            System.out.println(imageViewComponent);
            ImageView image = (ImageView) imageViewComponent.getValue();

            myGroup.getChildren().add(image);
        }
    }

}
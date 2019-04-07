package runner.external;

import engine.external.Entity;
import engine.external.component.PositionComponent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import runner.internal.TestEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRunner {
    /**
     * This will be the primary class that creates a new game engine
     * and displays sprites on a stage
     */
    private List<Entity> myEntities;
    private int mySceneWidth;
    private int mySceneHeight;
    private Stage myStage;
    private Group myGroup;
    private Scene myScene;
    private TestEngine myTestEngine;
    private Timeline myAnimation;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Map myEntitiesAndNodes;


    public GameRunner(List<Entity> entities, int width, int height, Stage stage) {
        myEntities = entities;
        mySceneWidth = width;
        mySceneHeight = height;
        myStage = stage;
        myGroup = new Group();
        myScene = new Scene(myGroup, mySceneWidth, mySceneHeight);
        myScene.setFill(Color.BEIGE);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myEntitiesAndNodes = initializeMap();
        showEntities();
        myStage.setScene(myScene);
        myTestEngine = new TestEngine(myEntities);
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
        myStage.show();
    }

    private void handleKeyInput(KeyCode code) {
        //Need to decide how to pass info to engine
        //Could be method myEngine.handleKeyInput(code) ?
        System.out.println(code);
    }

    private HashMap<Entity, Node> initializeMap() {
        HashMap<Entity, Node> map = new HashMap<Entity, Node>();
        for(Entity entity : myEntities){
            PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
            Point3D position = (Point3D) positionComponent.getValue();
            ImageView image = new ImageView("basketball.png");
            image.setFitWidth(50);
            image.setPreserveRatio(true);
            image.setSmooth(false);
            image.setLayoutX(position.getX());
            image.setLayoutY(position.getY());
            map.put(entity, image);
        }
        return map;
    }

    private void step (double elapsedTime) {
        myTestEngine.update();
        updateMap();
        showEntities();
    }

    private void updateMap(){
        for(Entity entity : myEntities){
            myEntitiesAndNodes.put(entity, updateNode(entity));
        }
    }

    private Node updateNode(Entity entity) {
        Node toUpdate = (Node) myEntitiesAndNodes.get(entity);
        PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
        Point3D position = (Point3D) positionComponent.getValue();
       // System.out.println(position.getX());
        toUpdate.setLayoutX(position.getX());
        toUpdate.setLayoutY(position.getY());
        return toUpdate;
    }

    private void printEntityLocations(){
        for(Entity entity : myEntities){
            PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
            Point3D position = (Point3D) positionComponent.getValue();
            System.out.println(position.getX());
        }
    }

    private void showEntities(){
        myGroup.getChildren().clear();
        for(Entity entity : myEntities){
            Node toAdd = (Node) myEntitiesAndNodes.get(entity);
         //   System.out.println(toAdd.getLayoutX());
            myGroup.getChildren().add((Node) myEntitiesAndNodes.get(entity));
        }
    }

}
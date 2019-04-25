

package engine.internal;

import data.external.DatabaseEngine;
import engine.external.Engine;
import engine.external.Entity;
import engine.external.Level;
import engine.external.component.*;


import engine.internal.systems.CollisionSystem;
import engine.internal.systems.ImageViewSystem;
import engine.internal.systems.MovementSystem;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class EngineSystemTest extends Application{
    private Stage testStage;
    private Group testGroup;
    private Scene testScene;
    private Engine testEngine;
    private Level testLevel;
    private ImageViewSystem testImgViewSystem;
    private MovementSystem testMovementSystem;
    private CollisionSystem testCollisionSystem;
    private Entity bird;
    private Entity platform;
    private Entity mushroom;
    private Entity cloud;

    public EngineSystemTest() throws Exception{

    }

    @BeforeAll
    public static void setupJFX() throws InterruptedException{
//        System.out.printf("About to launch FX App\n");
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(EngineSystemTest.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
//        System.out.printf("FX App thread started\n");
        Thread.sleep(1000);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initStage();
    }


    @BeforeEach
    public void setUp() {
        initEntities();
        initLevel();
        initEngine();
        initSystems();
        DatabaseEngine.getInstance().open();
    }


    private void initSystems(){
        testImgViewSystem = new ImageViewSystem(Arrays.asList(SpriteComponent.class, XPositionComponent.class, YPositionComponent.class,ZPositionComponent.class),testEngine);
        testMovementSystem = new MovementSystem(Arrays.asList(XVelocityComponent.class,YVelocityComponent.class,XPositionComponent.class,YPositionComponent.class,ZPositionComponent.class),testEngine);
        testCollisionSystem = new CollisionSystem(Arrays.asList(CollisionComponent.class,ImageViewComponent.class,XPositionComponent.class,YPositionComponent.class),testEngine);
    }

    private void initStage(){
        testStage = new Stage();
        testGroup = new Group();
        testScene = new Scene(testGroup,200,10);
    }


    private void initEntities(){
        bird = new Entity();
        initBird();
        platform = new Entity();
        initPlatform();
        mushroom = new Entity();
        initMushroom();
        cloud = new Entity();
        initCloud();
    }

    private void initBird(){
        bird.addComponent(new XPositionComponent(10.0));
        bird.addComponent(new YPositionComponent(0.0));
        bird.addComponent(new ZPositionComponent(0.0));
        bird.addComponent(new XVelocityComponent(2.0));
        bird.addComponent(new YVelocityComponent(40.0));
        bird.addComponent(new XAccelerationComponent(0.0));
        bird.addComponent(new YAccelerationComponent(-10.0));
        bird.addComponent(new SpriteComponent("flappy_bird"));
        bird.addComponent(new WidthComponent(4.0));
        bird.addComponent(new HeightComponent(4.0));
        bird.addComponent(new CollisionComponent(true));
    }

    private void initPlatform(){
        platform.addComponent(new XPositionComponent(0.0));
        platform.addComponent(new YPositionComponent(45.0));
        platform.addComponent(new ZPositionComponent(0.0));
        platform.addComponent(new SpriteComponent("mario_block.png"));
        platform.addComponent(new WidthComponent(100.0));
        platform.addComponent(new HeightComponent(20.0));
        platform.addComponent(new CollisionComponent(true));
    }

    private void initMushroom(){
        mushroom.addComponent(new CollisionComponent(true));
        mushroom.addComponent(new XPositionComponent(18.0));
        mushroom.addComponent(new YPositionComponent(0.0));
        mushroom.addComponent(new ZPositionComponent(0.0));
        mushroom.addComponent(new XVelocityComponent(-5.0));
        mushroom.addComponent(new YVelocityComponent(33.0));
        mushroom.addComponent(new SpriteComponent("mushroom.png"));
        mushroom.addComponent(new WidthComponent(4.0));
        mushroom.addComponent(new HeightComponent(4.0));
        mushroom.addComponent(new CollisionComponent(true));
    }

    private void initCloud(){
        cloud.addComponent(new XPositionComponent(15.0));
        cloud.addComponent(new YPositionComponent(10.0));
        cloud.addComponent(new XVelocityComponent(-3.0));
        cloud.addComponent(new YVelocityComponent(0.0));
    }


    private void initLevel(){
        testLevel = new Level();
        testLevel.addEntity(bird);
        testLevel.addEntity(platform);
        testLevel.addEntity(mushroom);
        testLevel.addEntity(cloud);
    }


    private void initEngine() {
        testEngine = new Engine(testLevel);
    }


    /**
     * Test that all original Entities stored in Level are returned after Engine's update
     */
    @Test
    public void testEngineUpdateReturn(){
        Collection<Entity> updatedEntities = testEngine.updateState(new ArrayList<>());
        assertTrue(updatedEntities.size()==4);
        assertTrue(updatedEntities.containsAll(testLevel.getEntities()));
    }

    /**
     * Test that MovementSystem correctly handles position, velocity and acceleration
     */
    @Test
    public void testMovementSystem(){
        Collection<Entity> updatedEntities = testEngine.updateState(new ArrayList<>());
        assertTrue((Double) bird.getComponent(XPositionComponent.class).getValue()==12.0);
        assertTrue((Double) bird.getComponent(YPositionComponent.class).getValue()==35.0);
        assertTrue((Double) bird.getComponent(XVelocityComponent.class).getValue()==2);
        assertTrue((Double) bird.getComponent(YVelocityComponent.class).getValue()==30);
    }

    /**
     * Test that ImageViewSystem assigns ImageViews to Entities having SpriteComponents with correct sizes
     */
    @Test
    public void testImageViewSystemInit(){
        Collection<Entity> updatedEntities = testEngine.updateState(new ArrayList<>());
        assertTrue(bird.hasComponents(ImageViewComponent.class));
        assertTrue(platform.hasComponents(ImageViewComponent.class));
        assertTrue(mushroom.hasComponents(ImageViewComponent.class));
        assertFalse(cloud.hasComponents(ImageViewComponent.class));
        assertTrue(((ImageView)bird.getComponent(ImageViewComponent.class).getValue()).getBoundsInLocal().getWidth()==(Double)bird.getComponent(WidthComponent.class).getValue());
        assertTrue(((ImageView)bird.getComponent(ImageViewComponent.class).getValue()).getBoundsInLocal().getHeight()==(Double)bird.getComponent(HeightComponent.class).getValue());
        assertTrue(((ImageView)platform.getComponent(ImageViewComponent.class).getValue()).getBoundsInLocal().getWidth()==(Double)platform.getComponent(WidthComponent.class).getValue());
        assertTrue(((ImageView)platform.getComponent(ImageViewComponent.class).getValue()).getBoundsInLocal().getHeight()==(Double)platform.getComponent(HeightComponent.class).getValue());
        assertTrue(((ImageView)mushroom.getComponent(ImageViewComponent.class).getValue()).getBoundsInLocal().getWidth()==(Double)mushroom.getComponent(WidthComponent.class).getValue());
        assertTrue(((ImageView)mushroom.getComponent(ImageViewComponent.class).getValue()).getBoundsInLocal().getHeight()==(Double)mushroom.getComponent(HeightComponent.class).getValue());
    }

    /**
     * Test that CollisionSystem can detect collision between Entities and successfully register AnyCollidedComponent
     */
    @Test
    public void testAnyCollision(){
        testImgViewSystem.update(testLevel.getEntities(),new ArrayList<KeyCode>());
        testCollisionSystem.update(testLevel.getEntities(),new ArrayList<KeyCode>());
        testMovementSystem.update(testLevel.getEntities(),new ArrayList<KeyCode>());
        assertFalse(bird.hasComponents(AnyCollidedComponent.class));

        testImgViewSystem.update(testLevel.getEntities(),new ArrayList<KeyCode>());
        testCollisionSystem.update(testLevel.getEntities(),new ArrayList<KeyCode>());
        assertTrue(bird.hasComponents(AnyCollidedComponent.class));
    }

    /**
     * Test that CollisionSystem can detect and register directional collision between Entities
     */
    @Test
    public void testDirectionalCollision(){
        testImgViewSystem.update(testLevel.getEntities(),new ArrayList<KeyCode>());
        testCollisionSystem.update(testLevel.getEntities(),new ArrayList<KeyCode>());
        testMovementSystem.update(testLevel.getEntities(),new ArrayList<KeyCode>());

        assertFalse(bird.hasComponents(BottomCollidedComponent.class));
        assertFalse(bird.hasComponents(RightCollidedComponent.class));
        assertFalse(mushroom.hasComponents(TopCollidedComponent.class));
        assertFalse(mushroom.hasComponents(LeftCollidedComponent.class));

        testImgViewSystem.update(testLevel.getEntities(),new ArrayList<KeyCode>());
        testCollisionSystem.update(testLevel.getEntities(),new ArrayList<KeyCode>());
//        assertTrue(bird.hasComponents(BottomCollidedComponent.class));
        assertTrue(bird.hasComponents(RightCollidedComponent.class));
//        assertTrue(((Collection)bird.getComponent(BottomCollidedComponent.class).getValue()).contains(mushroom));
        assertTrue(((Collection)bird.getComponent(RightCollidedComponent.class).getValue()).contains(mushroom));
//        assertTrue(mushroom.hasComponents(TopCollidedComponent.class));
        assertTrue(mushroom.hasComponents(LeftCollidedComponent.class));
//        assertTrue(((Collection)mushroom.getComponent(TopCollidedComponent.class).getValue()).contains(bird));
        assertTrue(((Collection)mushroom.getComponent(LeftCollidedComponent.class).getValue()).contains(bird));
    }


    /**
     * Test that no false collision detected by CollisionSystem
     */
    @Test
    public void testNoDirectionalCollision(){
        testEngine.updateState(new ArrayList<>());
        initBird();
        bird.removeComponent(Arrays.asList(RightCollidedComponent.class));
        bird.removeComponent(Arrays.asList(LeftCollidedComponent.class));
        bird.removeComponent(Arrays.asList(TopCollidedComponent.class));
        bird.removeComponent(Arrays.asList(BottomCollidedComponent.class));
        bird.removeComponent(Arrays.asList(AnyCollidedComponent.class));
        initMushroom();
        Double birdUpdateRightBound = (Double)bird.getComponent(XPositionComponent.class).getValue()+(Double)bird.getComponent(XVelocityComponent.class).getValue()+((ImageView)bird.getComponent(ImageViewComponent.class).getValue()).getBoundsInLocal().getWidth();
        mushroom.addComponent(new XPositionComponent(0.5+birdUpdateRightBound-(Double)mushroom.getComponent(XVelocityComponent.class).getValue()));
        testEngine.updateState(new ArrayList<>());
        assertTrue(!bird.hasComponents(RightCollidedComponent.class)||!((Collection)bird.getComponent(RightCollidedComponent.class).getValue()).contains(mushroom));
    }

    /**
     * Test on the old/new value fields in PositionComponents
     */
    @Test
    public void testSetPosition(){
        XPositionComponent c1 = new XPositionComponent(10.0);
        c1.setValue(20.0);
        assertTrue(c1.getValue()==20.0);
        assertTrue(c1.getOldValue()==10.0);
    }

    /**
     * Test that saveGame call returns a copy of the Entities running in the game instead of the original Entities
     */
    @Test
    public void testSaveGame(){
        testEngine.updateState(new ArrayList<>());
        Collection<Entity> savedEntities = testEngine.saveGame();
        for (Entity entity:savedEntities){
            if(entity.getComponent(SpriteComponent.class).getValue().equals("flappy_bird")){
                assertTrue(entity!=bird);
                assertFalse(entity.hasComponents(AnyCollidedComponent.class));
                assertTrue(bird.hasComponents(AnyCollidedComponent.class));
                entity.addComponent(new ZPositionComponent(0.0));
                assertFalse(bird.hasComponents(ZPositionComponent.class));
            }
        }
    }
}


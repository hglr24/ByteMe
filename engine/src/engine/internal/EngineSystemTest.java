
package engine.internal;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.Level;
import engine.external.component.*;
import engine.internal.systems.ImageViewSystem;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class EngineSystemTest {
    private Engine testEngine;
    private Level testLevel;
    private Stage testStage;
    private Scene testScene;
    private ImageViewSystem testImgViewSystem;
    private Entity entityImageViewTop;
    private Entity entityImageViewBottom;
    private Entity entityMovableCollidable;

    public EngineSystemTest() {
    }


    @BeforeEach
    public void setUp() {
        initEntities();
        initLevel();
        initEngine();
    }

    private void initEntities(){
        entityImageViewTop = new Entity();
        entityImageViewTop.addComponent(new SpriteComponent("flappy_bird.png"));
        entityImageViewTop.addComponent(new XPositionComponent(0.0));
        entityImageViewTop.addComponent(new YPositionComponent(10.0));
        entityImageViewTop.addComponent(new ZPositionComponent(0.0));
        entityImageViewBottom = new Entity();
        entityImageViewBottom.addComponent(new SpriteComponent("mushroom.png"));
        entityImageViewBottom.addComponent(new XPositionComponent(0.0));
        entityImageViewBottom.addComponent(new YPositionComponent(0.0));
        entityImageViewBottom.addComponent(new ZPositionComponent(0.0));
        entityMovableCollidable = new Entity();
        entityMovableCollidable.addComponent(new CollisionComponent(true));
        entityMovableCollidable.addComponent(new WidthComponent(20.0));
        entityMovableCollidable.addComponent(new HeightComponent(20.0));
    }

    private void initLevel(){
        testLevel = new Level();
        testLevel.addEntity(entityImageViewTop);
        testLevel.addEntity(entityImageViewBottom);
        testLevel.addEntity(entityMovableCollidable);

    }

    private void initEngine() {
        testStage = new Stage();
        testEngine = new Engine(testLevel);
    }

    @Test
    public void testEngineUpdateReturn(){
        Collection<Entity> updatedEntities = testEngine.updateState(new ArrayList<KeyCode>());
        assertTrue(updatedEntities.size()==2);
        assertTrue(updatedEntities.containsAll(testLevel.getEntities()));
    }

    @Test
    public void testImageViewSystemInit(){
        Collection<Entity> updatedEntities = testEngine.updateState(new ArrayList<KeyCode>());
        assertTrue(entityImageViewBottom.hasComponents(ImageViewComponent.class));
    }




}


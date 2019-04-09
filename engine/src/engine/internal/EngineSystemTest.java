package engine.internal;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.Level;
import engine.external.component.*;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;


public class EngineSystemTest {
    private Engine testEngine;
    private Level testLevel;
    private Entity entityImageView;
    private Entity entityMovableCollidable;


    @BeforeEach
    public void setUp() {
        initEntities();
        initLevel();
        initEngine();
    }

    private void initEntities(){
        entityImageView = new Entity();
        entityImageView.addComponent(new SpriteComponent("PLACEHOLDER"));
        entityImageView.addComponent(new XPositionComponent(0.0));
        entityImageView.addComponent(new YPositionComponent(0.0));
        entityMovableCollidable = new Entity();
        entityMovableCollidable.addComponent(new CollisionComponent(true));
        entityMovableCollidable.addComponent(new WidthComponent(20.0));
        entityMovableCollidable.addComponent(new HeightComponent(20.0));
    }

    private void initLevel(){
        testLevel = new Level();
        testLevel.addEntity(entityImageView);
        testLevel.addEntity(entityMovableCollidable);
    }

    private void initEngine() {
        testEngine = new Engine(testLevel);
    }

    @Test
    public void testEngineUpdateReturn(){
        Collection<Entity> updatedEntities = testEngine.updateState(new ArrayList<KeyCode>());
        assertTrue(updatedEntities.size()==2&&updatedEntities.containsAll(testLevel.getEntities()));
    }



}

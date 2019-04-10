package runner.internal;

import data.external.DataManager;
import engine.external.Entity;
import engine.external.Level;
import engine.external.component.*;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import runner.Testing.actions.*;
import runner.Testing.conditions.Condition;
import runner.Testing.conditions.GreaterThanCondition;
import runner.Testing.events.Event;
import runner.Testing.events.LeftCollisionEvent;
import runner.Testing.events.RightCollisionEvent;
import runner.external.Game;
import javafx.stage.Stage;

import java.util.Arrays;

public class DummyGameObjectMaker {
    private Game myGame;

    public DummyGameObjectMaker(){
        myGame = new Game();
        initializeGame(myGame);
    }

    private void initializeGame(Game dummyGame) {
        Level level1 = new Level();
        addDummyEntities(level1);
        addDummyEvents(level1);
        dummyGame.addLevel(level1);

    }

    private void addDummyEvents(Level level1) {
        Event event = new Event("one");
        event.addInputs(KeyCode.SPACE);
        event.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE, 1.0));
        Event event2 = new Event("one");
        event2.addInputs(KeyCode.S);
        event2.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, 0.0));
        RightCollisionEvent lce = new RightCollisionEvent("one", "two");
        //lce.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, -10.0));
        lce.addActions(new XVelocityAction(NumericAction.ModifyType.SCALE, -1.0));
        lce.addActions(new HeightAction(NumericAction.ModifyType.SCALE, 2.0));
        //event.addConditions(new GreaterThanCondition(YPositionComponent.class, -50.0));
        level1.addEvent(event);
        level1.addEvent(event2);
        level1.addEvent(lce);
    }

    private void addDummyEntities(Level level) {
        Entity dummy1 = new Entity();
        Entity dummy2 = new Entity();
        Entity dummy3 = new Entity();
        dummy1.addComponent(new XPositionComponent(30.0));
        dummy1.addComponent(new YPositionComponent(30.0));
        dummy1.addComponent(new ZPositionComponent(0.0));
        dummy2.addComponent(new XPositionComponent(700.0));
        dummy2.addComponent(new YPositionComponent(30.0));
        dummy2.addComponent(new ZPositionComponent(0.0));
        dummy3.addComponent(new XPositionComponent(90.0));
        dummy3.addComponent(new YPositionComponent(10.0));
        dummy3.addComponent(new ZPositionComponent(0.0));

        dummy1.addComponent(new WidthComponent(100.0));
        dummy1.addComponent(new HeightComponent(100.0));
        dummy2.addComponent(new WidthComponent(100.0));
        dummy2.addComponent(new HeightComponent(100.0));
        dummy3.addComponent(new WidthComponent(80.0));
        dummy3.addComponent(new HeightComponent(80.0));

//        dummy1.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
//        dummy2.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
//        dummy3.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
        dummy1.addComponent(new SpriteComponent("flappy_bird.png"));
        dummy2.addComponent(new SpriteComponent("basketball.png"));
        dummy3.addComponent(new SpriteComponent("mario_block.png"));

        dummy1.addComponent(new NameComponent("one"));
        dummy2.addComponent(new NameComponent("two"));
        dummy3.addComponent(new NameComponent("three"));

        dummy1.addComponent(new XVelocityComponent(2.0));
        dummy1.addComponent(new YVelocityComponent(0.0));

        dummy1.addComponent(new CollisionComponent(true));
        dummy2.addComponent(new CollisionComponent(true));

        level.addEntity(dummy1);
        level.addEntity(dummy2);
        level.addEntity(dummy3);
    }

    public Game getGame(String dummyString){
        return myGame;
    }

    public void serializeObject(){
        DataManager dm = new DataManager();
        dm.saveGameData("game1", myGame);
    }
}

package runner.internal;

import data.external.DataManager;
import engine.external.Entity;
import engine.external.Level;
import engine.external.actions.NumericAction;
import engine.external.actions.XPositionAction;
import engine.external.actions.XVelocityAction;
import engine.external.actions.YPositionAction;
import engine.external.component.*;

import engine.external.events.Event;
import engine.external.events.RightCollisionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
        event.addInputs(KeyCode.RIGHT);
        event.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, 5.0));
        Event event2 = new Event("one");
        event2.addInputs(KeyCode.S);
        event2.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, 2.0));
        RightCollisionEvent lce = new RightCollisionEvent("one", "two");
        //lce.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, -10.0));
        lce.addActions(new XVelocityAction(NumericAction.ModifyType.SCALE, -1.1));
//        lce.addActions(new HeightAction(NumericAction.ModifyType.SCALE, 2.0));
        Event event3 = new Event("one");
        event3.addInputs(KeyCode.LEFT);
        event3.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, -5.0));
        //event.addConditions(new GreaterThanCondition(YPositionComponent.class, -50.0));

        //add up down keycodes
        Event moveup = new Event("one");
        moveup.addInputs(KeyCode.UP);
        moveup.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE, -5.0));

        Event movedown = new Event("one");
        movedown.addInputs(KeyCode.DOWN);
        movedown.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE, 5.0));

        level1.addEvent(event);
        level1.addEvent(event2);
        level1.addEvent(event3);
        level1.addEvent(moveup);
        level1.addEvent(movedown);

        level1.addEvent(lce);
    }

    private void addDummyEntities(Level level) {
        Entity dummy1 = new Entity();
        Entity dummy2 = new Entity();
        Entity dummy3 = new Entity();
        Entity dummy4 = new Entity();
        Entity dummy5 = new Entity();
        Entity dummy6 = new Entity();
        dummy1.addComponent(new XPositionComponent(40.0));
        dummy1.addComponent(new YPositionComponent(30.0));
        dummy1.addComponent(new ZPositionComponent(0.0));
        dummy2.addComponent(new XPositionComponent(400.0));
        dummy2.addComponent(new YPositionComponent(20.0));
        dummy2.addComponent(new ZPositionComponent(0.0));
        dummy3.addComponent(new XPositionComponent(90.0));
        dummy3.addComponent(new YPositionComponent(100.0));
        dummy3.addComponent(new ZPositionComponent(0.0));

        dummy1.addComponent(new WidthComponent(40.0));
        dummy1.addComponent(new HeightComponent(50.0));
        dummy2.addComponent(new WidthComponent(80.0));
        dummy2.addComponent(new HeightComponent(80.0));
        dummy3.addComponent(new WidthComponent(200.0));
        dummy3.addComponent(new HeightComponent(80.0));

//        dummy1.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
//        dummy2.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
//        dummy3.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
        dummy1.addComponent(new SpriteComponent("basketball.png"));
        dummy2.addComponent(new SpriteComponent("basketball.png"));
        dummy3.addComponent(new SpriteComponent("basketball.png"));

        dummy1.addComponent(new NameComponent("one"));
        dummy2.addComponent(new NameComponent("two"));
        dummy3.addComponent(new NameComponent("three"));

        dummy1.addComponent(new XVelocityComponent(0.0));
        dummy1.addComponent(new YVelocityComponent(0.0));

        dummy2.addComponent(new XVelocityComponent(0.0));
        dummy2.addComponent(new YVelocityComponent(0.0));

        dummy1.addComponent(new CollisionComponent(true));
        dummy2.addComponent(new CollisionComponent(true));

        dummy1.addComponent(new AnyCollidedComponent(Arrays.asList(dummy2)));
        dummy2.addComponent(new AnyCollidedComponent(Arrays.asList(dummy1)));

        dummy1.addComponent(new RightCollidedComponent(Arrays.asList(dummy2)));
        dummy2.addComponent(new RightCollidedComponent(Arrays.asList(dummy1)));
        dummy2.addComponent(new LeftCollidedComponent(Arrays.asList(dummy1)));
        dummy1.addComponent(new LeftCollidedComponent(Arrays.asList(dummy2)));

        dummy1.addComponent(new TopCollidedComponent(Arrays.asList(dummy2)));
        dummy2.addComponent(new TopCollidedComponent(Arrays.asList(dummy1)));

        dummy1.addComponent(new BottomCollidedComponent(Arrays.asList(dummy2)));
        dummy2.addComponent(new BottomCollidedComponent(Arrays.asList(dummy1)));


        dummy4.addComponent(new XPositionComponent(170.0));
        dummy4.addComponent(new YPositionComponent(100.0));
        dummy4.addComponent(new ZPositionComponent(0.0));
        dummy4.addComponent(new WidthComponent(80.0));
        dummy4.addComponent(new HeightComponent(80.0));
        dummy4.addComponent(new SpriteComponent("basketball.png"));
        dummy4.addComponent(new NameComponent("four"));

        dummy5.addComponent(new XPositionComponent(250.0));
        dummy5.addComponent(new YPositionComponent(100.0));
        dummy5.addComponent(new ZPositionComponent(0.0));
        dummy5.addComponent(new WidthComponent(80.0));
        dummy5.addComponent(new HeightComponent(80.0));
        dummy5.addComponent(new SpriteComponent("mario_block.png"));
        dummy5.addComponent(new NameComponent("five"));

        dummy6.addComponent(new XPositionComponent(330.0));
        dummy6.addComponent(new YPositionComponent(100.0));
        dummy6.addComponent(new ZPositionComponent(0.0));
        dummy6.addComponent(new WidthComponent(160.0));
        dummy6.addComponent(new HeightComponent(80.0));
        dummy6.addComponent(new SpriteComponent("basketball.png"));
        dummy6.addComponent(new NameComponent("six"));

        level.addEntity(dummy1);
        level.addEntity(dummy2);
//        level.addEntity(dummy3);
//        level.addEntity(dummy4);
//        level.addEntity(dummy5);
//        level.addEntity(dummy6);

    }

    public Game getGame(String dummyString){
        return myGame;
    }

    public void serializeObject(){
        DataManager dm = new DataManager();
        dm.saveGameData("game1", myGame);
    }
}

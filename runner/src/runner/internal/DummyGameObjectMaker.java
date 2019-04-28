package runner.internal;

import data.external.DataManager;
import engine.external.Entity;
import engine.external.Level;
import engine.external.actions.*;
import engine.external.component.*;
import engine.external.conditions.LessThanCondition;
import engine.external.conditions.StringEqualToCondition;
import engine.external.events.*;
import javafx.scene.input.KeyCode;
import runner.external.Game;

/**
 * Makes a game object for use in Runner Test
 * @author Louis Jensen, Feroze Mohideen
 */
public class DummyGameObjectMaker {
    private Game myGame;

    /**
     * Constructor that creates a dummy Game
     */
    public DummyGameObjectMaker(){
        myGame = new Game();
        initializeGame(myGame);
    }

    private void initializeGame(Game dummyGame) {
        Level level1 = new Level();
        addDummyEntities(level1, 1.0);
        addDummyEvents(level1, 2.0);
        dummyGame.addLevel(level1);

        Level level2 = new Level();
        addDummyEntities(level2, 2.0);
        addDummyEvents(level2, 3.0);
        dummyGame.addLevel(level2);

        Level level3 = new Level();
        addDummyEntities(level3, 3.0);
        addDummyEvents(level3, 4.0);
        dummyGame.addLevel(level3);

        Level level4 = new Level();
        addDummyEntities(level4, 4.0);
        addDummyEvents(level4, 5.0);
        dummyGame.addLevel(level4);

    }

    private void addDummyEvents(Level level1, Double next) {
        Event life = new Event();
        life.addConditions(new StringEqualToCondition(NameComponent.class, "game"));
        life.addInputs(KeyCode.L);
        life.addActions(new ChangeLivesAction(NumericAction.ModifyType.RELATIVE, -1.0));


        Event event = new Event();
        event.addConditions(new StringEqualToCondition(NameComponent.class, "one"));
        event.addInputs(KeyCode.RIGHT);
        event.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, 5.0));

        Event event2 = new Event();
        event2.addConditions(new StringEqualToCondition(NameComponent.class, "one"));
        event2.addInputs(KeyCode.S);
        event2.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, 2.0));

        RightCollisionEvent oneByTwo = new RightCollisionEvent("two", false);
        oneByTwo.addConditions(new StringEqualToCondition(NameComponent.class, "one"));
        //oneByTwo.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, -10.0));
        oneByTwo.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, 0.0));
//        oneByTwo.addActions(new HeightAction(NumericAction.ModifyType.SCALE, 2.0));

        LeftCollisionEvent twoByOne = new LeftCollisionEvent("one", false);
        twoByOne.addConditions(new StringEqualToCondition(NameComponent.class, "two"));
        twoByOne.addActions(new XVelocityAction(NumericAction.ModifyType.SCALE, -1.0));

        Event flappyMoveLeft = new Event();
        flappyMoveLeft.addConditions(new StringEqualToCondition(NameComponent.class, "one"));
        flappyMoveLeft.addInputs(KeyCode.LEFT);
        flappyMoveLeft.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, -5.0));
        //event.addConditions(new GreaterThanCondition(YPositionComponent.class, -50.0));

        //add up down keycodes
//        Event flappyMoveUp = new Event("one");
//        flappyMoveUp.addInputs(KeyCode.UP);
//        flappyMoveUp.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE, -1.0));

        // let the Entity jump
        Event mushroomJump = new Event();
        mushroomJump.addConditions(new StringEqualToCondition(NameComponent.class, "two"));
        mushroomJump.addInputs(KeyCode.J);
        mushroomJump.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -5.0));

        //double jump logic (next 2 events)
        Event flappyJump = new Event();
        flappyJump.addConditions(new StringEqualToCondition(NameComponent.class, "one"));
        flappyJump.addInputs(KeyCode.UP);
        flappyJump.addConditions(new LessThanCondition(ValueComponent.class,10.0));
        flappyJump.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -5.0));
        flappyJump.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-2.0));
        //flappyJump.addActions(new YAccelerationAction(NumericAction.ModifyType.ABSOLUTE,0.2));
        flappyJump.addActions(new ValueAction(NumericAction.ModifyType.RELATIVE,1.0));
        flappyJump.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, 100.0));
        //flappyJump.addActions(new SoundAction("bach_chaconne"));

        BottomCollisionEvent flappyOnPlatform = new BottomCollisionEvent("four", false);
        flappyOnPlatform.addConditions(new StringEqualToCondition(NameComponent.class, "one"));
        flappyOnPlatform.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,0.0));
        flappyOnPlatform.addActions(new ValueAction(NumericAction.ModifyType.ABSOLUTE,0.0));


        TopCollisionEvent platformKnocked = new TopCollisionEvent("one", false);
        platformKnocked.addConditions(new StringEqualToCondition(NameComponent.class, "four"));
        //platformKnocked.addActions(new HealthAction(NumericAction.ModifyType.RELATIVE,-1.0));

        /**
         * When flappy falls onto a platform with both nonzero acceleration and velocity,
         * setting its velocity to zero and not modifying acceleration will allow flappy to stand above the platform
         * and trigger CollisionEvent in every game loop until the platform crumbles;
         * setting its acceleration to zero and not modifying velocity will achieve a similar effect, except that
         * flappy's oscillation will be more obvious, so for the purpose of game display I'd recommend the first option;
         * setting both acceleration and velocity to zero will allow flappy to "float" above the platform without
         * triggering any further collision -- this could probably be useful in some cases depending on user's decision
         */



        BottomCollisionEvent mushroomOnPlatform = new BottomCollisionEvent("four", false);
        mushroomOnPlatform.addConditions(new StringEqualToCondition(NameComponent.class, "two"));
        mushroomOnPlatform.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,0.0));
//        mushroomOnPlatform.addActions(new YAccelerationAction(NumericAction.ModifyType.ABSOLUTE,0.0));


        Event levelOver = new Event();
        levelOver.addInputs(KeyCode.SPACE);
        levelOver.addActions(new ProgressionAction(true));
        levelOver.addActions(new NextLevelAction(NumericAction.ModifyType.ABSOLUTE, next));

        RightCollisionEvent rce = new RightCollisionEvent("three", false);
        rce.addConditions(new StringEqualToCondition(NameComponent.class, "one"));
        rce.addActions(new ProgressionAction(true));
        rce.addActions(new NextLevelAction(NumericAction.ModifyType.ABSOLUTE, next));


        //Adds new entity
        Event AddEntity = new Event();
        AddEntity.addConditions(new StringEqualToCondition(NameComponent.class, "one"));
        AddEntity.addInputs(KeyCode.I);
        Entity dummy7 = new Entity();
        dummy7.addComponent(new XPositionComponent(400.0));
        dummy7.addComponent(new YPositionComponent(50.0));
        dummy7.addComponent(new ZPositionComponent(0.0));
        dummy7.addComponent(new WidthComponent(40.0));
        dummy7.addComponent(new HeightComponent(40.0));
        dummy7.addComponent(new SpriteComponent("mushroom.png"));
        dummy7.addComponent(new NameComponent("two"));
        dummy7.addComponent(new XVelocityComponent(-2.0));
        dummy7.addComponent(new YVelocityComponent(0.0));
        dummy7.addComponent(new XAccelerationComponent(0.0));
        dummy7.addComponent(new YAccelerationComponent(0.2));
        dummy7.addComponent(new CollisionComponent(true));
        AddEntity.addActions(new AddEntityAction(dummy7));
        AddEntity.addActions(new SoundAction("mario_theme"));


        level1.addEvent(event);
        level1.addEvent(event2);
        level1.addEvent(levelOver);
        level1.addEvent(flappyMoveLeft);
        //level1.addEvent(flappyMoveUp);
        level1.addEvent(mushroomJump);
        level1.addEvent(oneByTwo);
        level1.addEvent(twoByOne);
        level1.addEvent(platformKnocked);
        level1.addEvent(flappyOnPlatform);
        level1.addEvent(mushroomOnPlatform);
        level1.addEvent(flappyJump);
        level1.addEvent(AddEntity);
        level1.addEvent(life);

    }

    private void addDummyEntities(Level level, Double current) {
        // this is the dummy object that authoring has to make for each game
        Entity gameObject = new Entity();
//        gameObject.addComponent(new VariablesComponent(new HashMap<>(){{
//            put("Score", 0.0);
//            put("Lives", 3.0);
//        }}));
        gameObject.addComponent(new ScoreComponent(0.0));
        gameObject.addComponent(new LivesComponent(3.0));

        Entity dummy1 = new Entity();
        Entity dummy2 = new Entity();
        Entity dummy3 = new Entity();
        Entity dummy4 = new Entity();
        Entity dummy5 = new Entity();
        Entity dummy6 = new Entity();
        Entity dummy8 = new Entity();



        //flappy double jump
        Component vc = new ValueComponent(0.0);
        dummy1.addComponent(vc);
        dummy1.addComponent(new XPositionComponent(200.0));
        dummy1.addComponent(new YPositionComponent(50.0));
        dummy1.addComponent(new ZPositionComponent(0.0));
        dummy2.addComponent(new XPositionComponent(400.0));
        dummy2.addComponent(new YPositionComponent(50.0));
        dummy2.addComponent(new ZPositionComponent(0.0));
        dummy3.addComponent(new XPositionComponent(90.0));
        dummy3.addComponent(new YPositionComponent(100.0));
        dummy3.addComponent(new ZPositionComponent(0.0));
        dummy8.addComponent(new XPositionComponent(200.0));
        dummy8.addComponent(new YPositionComponent(50.0));
        dummy8.addComponent(new ZPositionComponent(0.0));

        dummy1.addComponent(new WidthComponent(40.0));
        dummy1.addComponent(new HeightComponent(50.0));
        dummy2.addComponent(new WidthComponent(40.0));
        dummy2.addComponent(new HeightComponent(40.0));
        dummy3.addComponent(new WidthComponent(200.0));
        dummy3.addComponent(new HeightComponent(80.0));
        dummy8.addComponent(new WidthComponent(40.0));
        dummy8.addComponent(new HeightComponent(40.0));

//        dummy1.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
//        dummy2.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
//        dummy3.addComponent(new ImageViewComponent(new ImageView("basketball.png")));
        dummy1.addComponent(new SpriteComponent("flappy_bird"));
        dummy2.addComponent(new SpriteComponent("mushroom.png"));
        dummy3.addComponent(new SpriteComponent("basketball"));
        dummy8.addComponent(new SpriteComponent("mushroom.png"));

        dummy1.addComponent(new CameraComponent(true));


        dummy1.addComponent(new NameComponent("one"));
        dummy2.addComponent(new NameComponent("two"));
        dummy3.addComponent(new NameComponent("three"));
        dummy8.addComponent(new NameComponent("five"));
        gameObject.addComponent(new NameComponent("game"));
        dummy1.addComponent(new XVelocityComponent(2.0));
        dummy1.addComponent(new YVelocityComponent(0.0));
        dummy1.addComponent(new XAccelerationComponent(0.0));
        dummy1.addComponent(new YAccelerationComponent(0.2));

        dummy2.addComponent(new XVelocityComponent(-2.0));
        dummy2.addComponent(new YVelocityComponent(0.0));
        dummy2.addComponent(new XAccelerationComponent(0.0));
        dummy2.addComponent(new YAccelerationComponent(0.2));
        dummy8.addComponent(new XVelocityComponent(-2.0));
        dummy8.addComponent(new YVelocityComponent(0.0));
        dummy8.addComponent(new XAccelerationComponent(0.0));
        dummy8.addComponent(new YAccelerationComponent(0.2));

        dummy1.addComponent(new CollisionComponent(true));
        dummy2.addComponent(new CollisionComponent(true));
        dummy8.addComponent(new CollisionComponent(true));


        dummy4.addComponent(new XPositionComponent(170.0));
        dummy4.addComponent(new YPositionComponent(400.0));
        dummy4.addComponent(new ZPositionComponent(0.0));
        dummy4.addComponent(new WidthComponent(500.0));
        dummy4.addComponent(new HeightComponent(80.0));
        dummy4.addComponent(new SpriteComponent("mario_block.png"));
        dummy4.addComponent(new CollisionComponent(true));
        dummy4.addComponent(new HealthComponent(100.0));
        dummy4.addComponent(new NameComponent("four"));

        dummy5.addComponent(new XPositionComponent(250.0));
        dummy5.addComponent(new YPositionComponent(400.0));
        dummy5.addComponent(new ZPositionComponent(0.0));
        dummy5.addComponent(new WidthComponent(80.0));
        dummy5.addComponent(new HeightComponent(80.0));
        dummy5.addComponent(new SpriteComponent("mario_block.png"));
        dummy5.addComponent(new CollisionComponent(true));
        dummy5.addComponent(new NameComponent("five"));

        dummy6.addComponent(new XPositionComponent(330.0));
        dummy6.addComponent(new YPositionComponent(400.0));
        dummy6.addComponent(new ZPositionComponent(0.0));
        dummy6.addComponent(new WidthComponent(160.0));
        dummy6.addComponent(new HeightComponent(80.0));
        dummy6.addComponent(new SpriteComponent("mario_block.png"));
        dummy6.addComponent(new CollisionComponent(true));
        dummy6.addComponent(new NameComponent("six"));

        dummy1.addComponent(new NextLevelComponent(current));
        dummy1.addComponent(new ProgressionComponent(false));

        dummy1.addComponent(new AssociatedEntityComponent(gameObject));

        dummy1.addComponent(new SoundComponent("mario_theme"));

        level.addEntity(dummy1);
        level.addEntity(dummy2);
        level.addEntity(dummy3);
        level.addEntity(dummy4);
        level.addEntity(gameObject);
//        level.addEntity(dummy5);
//        level.addEntity(dummy6);
        level.addEntity(dummy8);
//        try{
//            level.addEntity((Entity) dummy2.clone());
//        }catch(CloneNotSupportedException e){
//            System.out.println("Could not clone the dummy");
//        }


    }

    /**
     * Gets dummy game
     * @param dummyString symbolizes game name in actual call
     * @return dummy Game object
     */
    public Game getGame(String dummyString){
        return myGame;
    }

    /**
     * Saves dummy game to data base
     */
    public void serializeObject(){
        DataManager dm = new DataManager();
        dm.saveGameData("game1", myGame);
    }
}

package runner.internal;

import data.external.DataManager;
import data.external.GameCenterData;
import engine.external.Entity;
import engine.external.Level;
import engine.external.actions.*;
import engine.external.component.*;
import engine.external.conditions.*;
import engine.external.events.*;
import javafx.scene.input.KeyCode;
import runner.external.Game;

import java.util.*;

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
        serializeObject();
    }

    private void initializeGame(Game dummyGame) {
        Level level1 = new Level();
        level1.setHeight(myGame.getHeight());
        level1.setWidth(10000);
        level1.setBackground("flappy_background");
        addDummyEntities(level1, 1.0);
        addDummyEvents(level1, 2.0);
        dummyGame.addLevel(level1);

    }

    private void addDummyEvents(Level level1, Double next) {

        /**
         * Event: Press Right Arrow Key to move flappy to the right
         * Actions:
         * 1. increase xPosition of flappy by 5
         */
        Event flappyMoveRightEvent = new Event();
        flappyMoveRightEvent.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        flappyMoveRightEvent.addInputs(KeyCode.RIGHT);
        flappyMoveRightEvent.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, 5.0));

        /**
         * Event: Press Left Arrow Key to:
         * 1.Move Flappy to the left
         */
        Event flappyMoveLeft = new Event();
        flappyMoveLeft.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        flappyMoveLeft.addInputs(KeyCode.LEFT);
        flappyMoveLeft.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, -5.0));
        //event.addConditions(new GreaterThanCondition(YPositionComponent.class, -50.0));

        /**
         * Event: flappy jumps only if his jump counter is less than 10 (prevents him from performing infinite consecutive jumps before landing again:
         * 1. Set flappy's YVelocity so that he cnotinues to move upwards
         * 2. set flappy's yPosition to slightly higher so that he starts jumping this frame
         * 3. increment flappy's jump counter
         * 4. Increase the score
         */
        //double jump logic (next 2 events)
        Event flappyJump = new Event();
        flappyJump.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        flappyJump.addInputs(KeyCode.UP);
        flappyJump.addConditions(new LessThanCondition(ValueComponent.class,12.0));
        flappyJump.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -5.0));
        flappyJump.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-.02));
        flappyJump.addActions(new YAccelerationAction(NumericAction.ModifyType.ABSOLUTE,0.2));
        flappyJump.addActions(new ValueAction(NumericAction.ModifyType.RELATIVE,1.0));
        flappyJump.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, 100.0));

        /**
         * Event: flappy collides with Block from the Bottom (bottom of flappy hits block):
         * 1. Reset flappy's jump counter to 0
         * 2. set flappy's yVelocity to 0
         */
        BottomCollisionEvent flappyOnPlatform = new BottomCollisionEvent("Block", false);
        flappyOnPlatform.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        //flappyOnPlatform.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,0.0));
        flappyOnPlatform.addActions(new ValueAction(NumericAction.ModifyType.ABSOLUTE,0.0));


        /**
         * When flappy falls onto a platform with both nonzero acceleration and velocity,
         * setting its velocity to zero and not modifying acceleration will allow flappy to stand above the platform
         * and trigger CollisionEvent in every game loop until the platform crumbles;
         * setting its acceleration to zero and not modifying velocity will achieve a similar effect, except that
         * flappy's oscillation will be more obvious, so for the purpose of game display I'd recommend the first option;
         * setting both acceleration and velocity to zero will allow flappy to "float" above the platform without
         * triggering any further collision -- this could probably be useful in some cases depending on user's decision
         */

//        BottomCollisionEvent Ghost1OnPlatform = new BottomCollisionEvent("Block", false);
//        Ghost1OnPlatform.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost1"));
        //Ghost1OnPlatform.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,0.0));
       // Ghost1OnPlatform.addActions(new YPositionAction(NumericAction.ModifyType.ABSOLUTE,-3.0));

        /**
         * Event: Basketball Collides with the platform:
         * 1. Make the basketball bounce by adding YVelocity and YPosition actions
         */
        BottomCollisionEvent bbOnPlatform = new BottomCollisionEvent("Block", false);
        bbOnPlatform.addConditions(new StringEqualToCondition(NameComponent.class, "bb"));
        bbOnPlatform.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,-10.0));
        bbOnPlatform.addActions(new YPositionAction(NumericAction.ModifyType.ABSOLUTE,-3.0));

        /**
         * Event: Spawn a new Mushroom when you press I:
         * 1. A Mushroom entity will spawn
         * 2. The Mario theme will play :)
         */
        Event SpawnMushroomEvent = new Event();
        SpawnMushroomEvent.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        SpawnMushroomEvent.addInputs(KeyCode.I);
        Entity Mushroom = new Entity();
        Mushroom.addComponent(new XPositionComponent(400.0));
        Mushroom.addComponent(new YPositionComponent(50.0));
        Mushroom.addComponent(new ZPositionComponent(0.0));
        Mushroom.addComponent(new WidthComponent(40.0));
        Mushroom.addComponent(new HeightComponent(40.0));
        Mushroom.addComponent(new SpriteComponent("Ghost.png"));
        Mushroom.addComponent(new NameComponent("Ghost1"));
        Mushroom.addComponent(new XVelocityComponent(-2.0));
        Mushroom.addComponent(new YVelocityComponent(0.0));
        Mushroom.addComponent(new XAccelerationComponent(0.0));
        Mushroom.addComponent(new YAccelerationComponent(0.2));
        Mushroom.addComponent(new CollisionComponent(true));
        SpawnMushroomEvent.addActions(new AddEntityInstantAction(Mushroom));
        SpawnMushroomEvent.addActions(new SoundAction("mario_theme"));

        /**
         * Event: Spawn a new Basketball when you press o:
         * 1. A SpawnBasketball entity will spawn
         */
        Event SpawnBasketball = new Event();
        SpawnBasketball.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        SpawnBasketball.addInputs(KeyCode.O);
        Entity SpawnableBasketball = new Entity();
        SpawnableBasketball.addComponent(new XPositionComponent(600.0));
        SpawnableBasketball.addComponent(new YPositionComponent(50.0));
        SpawnableBasketball.addComponent(new ZPositionComponent(0.0));
        SpawnableBasketball.addComponent(new WidthComponent(40.0));
        SpawnableBasketball.addComponent(new HeightComponent(40.0));
        SpawnableBasketball.addComponent(new SpriteComponent("basketball"));
        SpawnableBasketball.addComponent(new NameComponent("bb"));
        SpawnableBasketball.addComponent(new XVelocityComponent(-2.0));
        SpawnableBasketball.addComponent(new YVelocityComponent(0.0));
        SpawnableBasketball.addComponent(new XAccelerationComponent(0.0));
        SpawnableBasketball.addComponent(new YAccelerationComponent(0.2));
        SpawnableBasketball.addComponent(new CollisionComponent(true));
        SpawnBasketball.addActions(new AddEntityInstantAction(SpawnableBasketball));

        /**
         * Event: when the Basketball's timer reaches 0:
         * 1. Boo's will reverse yVel
         * 2. The Basketball's timer will reset to 100
         */
        Event timeEvent = new Event();
        timeEvent.addConditions(new StringEqualToCondition(NameComponent.class, "Basketball"));
        timeEvent.addConditions(new EqualToCondition(TimerComponent.class, 0.0));
        timeEvent.addActions(new YVelocityAction(NumericAction.ModifyType.SCALE, -1.0));
        timeEvent.addActions(new TimerAction(NumericAction.ModifyType.ABSOLUTE, 100.0));

        /**
         * Event: When flappy falls off the screen he will:
         * 1. Reappear at his starting position
         * 2. Reset his y velocity to 0
         * 3. Lose 10 points off score
         * 4. Lose 1 life (this requires an AssociatedEntityAction so that the LivesComponent (belongs to game) can be accessed
         */
        Event flappyFallsEvent = new Event();
        flappyFallsEvent.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        flappyFallsEvent.addConditions(new GreaterThanCondition(YPositionComponent.class, 700.0));
        flappyFallsEvent.addActions(new YPositionAction(NumericAction.ModifyType.ABSOLUTE, 100.0));
        flappyFallsEvent.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, 0.0));
        AssociatedEntityAction updateGameLivesAction = new AssociatedEntityAction();
        updateGameLivesAction.setAction(NumericAction.ModifyType.RELATIVE, -1.0, LivesComponent.class);
        flappyFallsEvent.addActions(updateGameLivesAction);
        flappyFallsEvent.addActions(new ValueAction(NumericAction.ModifyType.ABSOLUTE, 15.0));

        //Flappy on block
        CollisionEvent FlappyBounceOnBlock1 = new BottomCollisionEvent("Block", false);
        FlappyBounceOnBlock1.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        FlappyBounceOnBlock1.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -2.0));
        FlappyBounceOnBlock1.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-10.0));
        FlappyBounceOnBlock1.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,10.0));
        FlappyBounceOnBlock1.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, 1.0));
        FlappyBounceOnBlock1.addActions(new SpriteAction("flappy_bird"));

        //Top of Block on flappy
        CollisionEvent CloudMoveDown = new TopCollisionEvent("flappy", false);
        CloudMoveDown.addConditions(new StringEqualToCondition(NameComponent.class, "Block"));
        CloudMoveDown.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, 1.5));
        CloudMoveDown.addActions(new OpacityAction(NumericAction.ModifyType.ABSOLUTE, 0.2));

        //Ghost on block
        CollisionEvent GhostBounceOnBlock= new BottomCollisionEvent("Block", false);
        GhostBounceOnBlock.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost1"));
        GhostBounceOnBlock.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -2.0));
        GhostBounceOnBlock.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-10.0));
        GhostBounceOnBlock.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,10.0));

        //Ghost Collision with Ghost
        CollisionEvent GhostOnGhost= new BottomCollisionEvent("Ghost1", false);
        GhostOnGhost.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost1"));
        GhostOnGhost.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -2.0));
        GhostOnGhost.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-12.0));
        GhostOnGhost.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,3.0));

        //Flappy Collision with Ghost
        CollisionEvent FlappyOnGhost= new BottomCollisionEvent("Ghost1", false);
        FlappyOnGhost.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        FlappyOnGhost.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -2.0));
        FlappyOnGhost.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-12.0));
        FlappyOnGhost.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,3.0));

        //Flappy Collision with Ghost
        CollisionEvent FlappyRightOnGhost= new RightCollisionEvent("Ghost1", false);
        FlappyRightOnGhost.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        FlappyRightOnGhost.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, -2.0));
        FlappyRightOnGhost.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-10.0));
        FlappyRightOnGhost.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,3.0));

        //Flappy Collision with Clouds
        CollisionEvent FlappyRightOnBlock= new RightCollisionEvent("Block", false);
        FlappyRightOnBlock.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        FlappyRightOnBlock.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-10.0));
        FlappyRightOnBlock.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,3.0));

        //Flappy Cloud from Below
        CollisionEvent FlappyBelowOnBlock= new RightCollisionEvent("Block", false);
        FlappyBelowOnBlock.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        FlappyBelowOnBlock.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,10.0));
        FlappyBelowOnBlock.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,3.0));

        //ThunderChangeFlappy sprite
        CollisionEvent ThunderFlappy= new BottomCollisionEvent("THUNDER", false);
        ThunderFlappy.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        ThunderFlappy.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -2.0));
        ThunderFlappy.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-12.0));
        ThunderFlappy.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,3.0));
        ThunderFlappy.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, 1.0));


        //ThunderFlappy.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, 10.0));
        ThunderFlappy.addActions(new SpriteAction("king_flappy"));
        AssociatedEntityAction addLivesAction = new AssociatedEntityAction();
        addLivesAction.setAction(NumericAction.ModifyType.RELATIVE, 1.0, LivesComponent.class);
        ThunderFlappy.addActions(addLivesAction);

        //reset the timer and flip the boo value
        Event FlipVel = new Event();
        FlipVel.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost1"));
        FlipVel.addConditions(new EqualToCondition(TimerComponent.class, 0.0));
        FlipVel.addActions(new YVelocityAction(NumericAction.ModifyType.SCALE, -1.0));
        FlipVel.addActions(new TimerAction(NumericAction.ModifyType.ABSOLUTE, 50.0));

        //flappy hits boo and looses point
        CollisionEvent LOOSEPOINT= new BottomCollisionEvent("Ghost1", false);
        LOOSEPOINT.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        LOOSEPOINT.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -2.0));
        LOOSEPOINT.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-12.0));
        LOOSEPOINT.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,3.0));
        LOOSEPOINT.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, 1.0));

        //flappy hits boo and looses point
        CollisionEvent rightBoo= new RightCollisionEvent("Ghost1", false);
        rightBoo.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        rightBoo.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-12.0));
        rightBoo.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,3.0));
        rightBoo.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, -1.0));
        //flappy hits boo and looses point
        CollisionEvent topBoo= new TopCollisionEvent("Ghost1", false);
        topBoo.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        topBoo.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -2.0));
        topBoo.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-12.0));
        topBoo.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,3.0));
        topBoo.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, -1.0));
        /**
         * Add All Events to the Level
         */
        // level1.addEvent(flappyMoveRightEvent);
        // level1.addEvent(flappyMoveLeft);
        level1.addEvent(topBoo);
        level1.addEvent(rightBoo);
        level1.addEvent(LOOSEPOINT);
        level1.addEvent(FlipVel);
        level1.addEvent(ThunderFlappy);
        level1.addEvent(FlappyBelowOnBlock);
        level1.addEvent(timeEvent);
        level1.addEvent(FlappyRightOnBlock);
        level1.addEvent(flappyOnPlatform);
        level1.addEvent(bbOnPlatform);
        level1.addEvent(flappyJump);
        level1.addEvent(SpawnMushroomEvent);
        level1.addEvent(SpawnBasketball);
        level1.addEvent(flappyFallsEvent);
        level1.addEvent(FlappyBounceOnBlock1);
        level1.addEvent(GhostBounceOnBlock);
        level1.addEvent(GhostOnGhost);
        level1.addEvent(FlappyOnGhost);
        level1.addEvent(CloudMoveDown);



    }

    private void addDummyEntities(Level level, Double current) {
        /**
         * Define the Entities
         * Note: There needs to be a gameObject for each game
         */
        // Create the gameObject. This is the dummy object that authoring has to make for each game.
        Entity gameObject = new Entity();
        //Give the Game Object ScoreComponent and LivesComponent that will persist through the whole game (not just a level)
        gameObject.addComponent(new ScoreComponent(0.0));
        gameObject.addComponent(new LivesComponent(3.0));
        gameObject.addComponent(new NameComponent("game"));
        gameObject.addComponent(new TimerComponent(100.0));

        //Create the entities in the level
        Entity Flappy = new Entity();
        Entity Ghost1 = new Entity();
        Entity BasketBall = new Entity();
        Entity MarioBlock1 = new Entity();
        Entity MarioBlock2 = new Entity();
        Entity MarioBlock3 = new Entity();
        Entity MarioBlock4 = new Entity();
        Entity MarioBlock5 = new Entity();
        Entity MarioBlock6 = new Entity();
        Entity MarioBlock7 = new Entity();
        Entity MarioBlock8 = new Entity();

        Entity Ghost2 = new Entity();

        /**
         * Give the Entities their needed Components
         */

        //Give Flappy the needed components
        Flappy.addComponent(new NameComponent("flappy"));
        Flappy.addComponent(new SpriteComponent("flappy_bird"));
        Flappy.addComponent(new XPositionComponent(200.0));
        Flappy.addComponent(new YPositionComponent(50.0));
        Flappy.addComponent(new ZPositionComponent(0.0));
        Flappy.addComponent(new WidthComponent(40.0));
        Flappy.addComponent(new HeightComponent(50.0));
        Flappy.addComponent(new XVelocityComponent(2.0));
        Flappy.addComponent(new YVelocityComponent(0.0));
        Flappy.addComponent(new XAccelerationComponent(0.0));
        Flappy.addComponent(new YAccelerationComponent(0.2));
        Flappy.addComponent(new CollisionComponent(true));
        //Give flappy the camera component so that the camera will be centered around him.
        Flappy.addComponent(new CameraComponent(true));
        //Give flappy a ValueComponent that will limit the number of jumps he can perform. We will use this so that flappy can only double jump.
        Component flappyJumpCounter = new ValueComponent(0.0);
        Flappy.addComponent(flappyJumpCounter);
        Flappy.addComponent(new AssociatedEntityComponent(gameObject));

        //Give Ghost1 the needed components
        Ghost1.addComponent(new XPositionComponent(400.0));
        Ghost1.addComponent(new YPositionComponent(50.0));
        Ghost1.addComponent(new ZPositionComponent(0.0));
        Ghost1.addComponent(new WidthComponent(40.0));
        Ghost1.addComponent(new HeightComponent(40.0));
        Ghost1.addComponent(new SpriteComponent("ghost.png"));
        Ghost1.addComponent(new NameComponent("Ghost1"));
        Ghost1.addComponent(new XVelocityComponent(-2.0));
        Ghost1.addComponent(new YVelocityComponent(0.0));
        Ghost1.addComponent(new XAccelerationComponent(0.0));
        Ghost1.addComponent(new YAccelerationComponent(0.2));
        Ghost1.addComponent(new CollisionComponent(true));

        //Give basketball the needed components
        BasketBall.addComponent(new XPositionComponent( 1000.0));
        BasketBall.addComponent(new YPositionComponent(100.0));
        BasketBall.addComponent(new ZPositionComponent(0.0));
        BasketBall.addComponent(new WidthComponent(200.0));
        BasketBall.addComponent(new HeightComponent(80.0));
        BasketBall.addComponent(new SpriteComponent("basketball"));
        BasketBall.addComponent(new NameComponent("Basketball"));
        BasketBall.addComponent(new TimerComponent(100.0));
        BasketBall.addComponent(new OpacityComponent(0.0));
        //basketball has a timer component so that we can define an Event that will make mushrooms respawn everytime basketball's timer hits 0.

        //Give the blocks their needed components
        MarioBlock1.addComponent(new XPositionComponent(100.0));
        MarioBlock1.addComponent(new YPositionComponent(400.0));
        MarioBlock1.addComponent(new ZPositionComponent(0.0));
        MarioBlock1.addComponent(new WidthComponent(100.0));
        MarioBlock1.addComponent(new HeightComponent(100.0));
        MarioBlock1.addComponent(new SpriteComponent("cloud"));
        MarioBlock1.addComponent(new CollisionComponent(false));
        MarioBlock1.addComponent(new HealthComponent(100.0));
        MarioBlock1.addComponent(new NameComponent("Block"));


        MarioBlock2.addComponent(new XPositionComponent(410.0));
        MarioBlock2.addComponent(new YPositionComponent(300.0));
        MarioBlock2.addComponent(new ZPositionComponent(0.0));
        MarioBlock2.addComponent(new WidthComponent(100.0));
        MarioBlock2.addComponent(new HeightComponent(100.0));
        MarioBlock2.addComponent(new SpriteComponent("cloud"));
        MarioBlock2.addComponent(new CollisionComponent(false));
        MarioBlock2.addComponent(new NameComponent("Block"));


        MarioBlock3.addComponent(new XPositionComponent(720.0));
        MarioBlock3.addComponent(new YPositionComponent(200.0));
        MarioBlock3.addComponent(new ZPositionComponent(0.0));
        MarioBlock3.addComponent(new WidthComponent(100.0));
        MarioBlock3.addComponent(new HeightComponent(100.0));
        MarioBlock3.addComponent(new SpriteComponent("cloud"));
        MarioBlock3.addComponent(new CollisionComponent(false));
        MarioBlock3.addComponent(new NameComponent("Block"));


        MarioBlock4.addComponent(new XPositionComponent(900.0));
        MarioBlock4.addComponent(new YPositionComponent(400.0));
        MarioBlock4.addComponent(new ZPositionComponent(0.0));
        MarioBlock4.addComponent(new WidthComponent(100.0));
        MarioBlock4.addComponent(new HeightComponent(100.0));
        MarioBlock4.addComponent(new SpriteComponent("cloud"));
        MarioBlock4.addComponent(new CollisionComponent(false));
        MarioBlock4.addComponent(new NameComponent("Block"));

        MarioBlock5.addComponent(new XPositionComponent(1200.0));
        MarioBlock5.addComponent(new YPositionComponent(300.0));
        MarioBlock5.addComponent(new ZPositionComponent(0.0));
        MarioBlock5.addComponent(new WidthComponent(100.0));
        MarioBlock5.addComponent(new HeightComponent(100.0));
        MarioBlock5.addComponent(new SpriteComponent("cloud"));
        MarioBlock5.addComponent(new CollisionComponent(false));
        MarioBlock5.addComponent(new NameComponent("Block"));

        MarioBlock6.addComponent(new XPositionComponent(1050.0));
        MarioBlock6.addComponent(new YPositionComponent(200.0));
        MarioBlock6.addComponent(new ZPositionComponent(0.0));
        MarioBlock6.addComponent(new WidthComponent(100.0));
        MarioBlock6.addComponent(new HeightComponent(100.0));
        MarioBlock6.addComponent(new SpriteComponent("cloud"));
        MarioBlock6.addComponent(new CollisionComponent(false));
        MarioBlock6.addComponent(new NameComponent("Block"));

        MarioBlock7.addComponent(new XPositionComponent(1500.0));
        MarioBlock7.addComponent(new YPositionComponent(250.0));
        MarioBlock7.addComponent(new ZPositionComponent(0.0));
        MarioBlock7.addComponent(new WidthComponent(100.0));
        MarioBlock7.addComponent(new HeightComponent(100.0));
        MarioBlock7.addComponent(new SpriteComponent("cloud"));
        MarioBlock7.addComponent(new CollisionComponent(false));
        MarioBlock7.addComponent(new NameComponent("Block"));

        MarioBlock8.addComponent(new XPositionComponent(200.0));
        MarioBlock8.addComponent(new YPositionComponent(250.0));
        MarioBlock8.addComponent(new ZPositionComponent(0.0));
        MarioBlock8.addComponent(new WidthComponent(100.0));
        MarioBlock8.addComponent(new HeightComponent(100.0));
        MarioBlock8.addComponent(new SpriteComponent("cloud"));
        MarioBlock8.addComponent(new CollisionComponent(false));
        MarioBlock8.addComponent(new NameComponent("Block"));

        for(int i=0; i<55;i++){
            Entity aCloud = new Entity();
            aCloud.addComponent(new XPositionComponent((Math.random() * ((10000 - 0) + 1)) + 0));
            aCloud.addComponent(new YPositionComponent((Math.random() * ((400 - 200) + 1)) + 200));
            aCloud.addComponent(new ZPositionComponent(0.0));
            aCloud.addComponent(new WidthComponent(100.0));
            aCloud.addComponent(new HeightComponent(100.0));
            aCloud.addComponent(new SpriteComponent("cloud"));
            aCloud.addComponent(new CollisionComponent(false));
            aCloud.addComponent(new NameComponent("Block"));
            level.addEntity(aCloud);
        }

        for(int i=0; i<5;i++){
            Entity thunderCloud = new Entity();
            thunderCloud.addComponent(new XPositionComponent((Math.random() * ((10000 - 0) + 1)) + 0));
            thunderCloud.addComponent(new YPositionComponent((Math.random() * ((400 - 200) + 1)) + 200));
            thunderCloud.addComponent(new ZPositionComponent(0.0));
            thunderCloud.addComponent(new WidthComponent(100.0));
            thunderCloud.addComponent(new HeightComponent(100.0));
            thunderCloud.addComponent(new SpriteComponent("thunder"));
            thunderCloud.addComponent(new CollisionComponent(false));
            thunderCloud.addComponent(new NameComponent("THUNDER"));
            level.addEntity(thunderCloud);
        }

        for(int i=0;i<8;i++){
            Entity myGhost = new Entity();
            myGhost.addComponent(new XPositionComponent((Math.random() * ((10000 - 0) + 1)) + 0));
            myGhost.addComponent(new YPositionComponent((Math.random() * ((200 - 300) + 1)) + 300));
            myGhost.addComponent(new ZPositionComponent(0.0));
            myGhost.addComponent(new WidthComponent(40.0));
            myGhost.addComponent(new HeightComponent(40.0));
            myGhost.addComponent(new SpriteComponent("ghost.png"));
            myGhost.addComponent(new NameComponent("Ghost1"));
            myGhost.addComponent(new TimerComponent(50.0));
            myGhost.addComponent(new XVelocityComponent(0.0));
            myGhost.addComponent(new YVelocityComponent(1.0));
            myGhost.addComponent(new XAccelerationComponent(0.0));
            myGhost.addComponent(new YAccelerationComponent(0.0));
            myGhost.addComponent(new CollisionComponent(true));
            level.addEntity(myGhost);
        }
        //Add the entities to the level
        level.addEntity(gameObject);
        level.addEntity(Flappy);
        level.addEntity(BasketBall);
        level.addEntity(MarioBlock1);
        level.addEntity(MarioBlock2);
        level.addEntity(MarioBlock3);
        level.addEntity(MarioBlock4);
        level.addEntity(MarioBlock5);
        level.addEntity(MarioBlock6);
        level.addEntity(MarioBlock7);
        level.addEntity(MarioBlock8);

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
        dm.saveGameInfo("Flappy Cloud Jump", "Dima", new GameCenterData("Flappy Cloud Jump", "Help Flappy fly by jumping off of clouds",
                "king_flappy",
                "Dima"));
        dm.saveGameData("Flappy Cloud Jump", "Dima", myGame);
    }
}

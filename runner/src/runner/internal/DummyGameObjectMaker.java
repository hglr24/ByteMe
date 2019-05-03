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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
        level1.setBackground("galaxy");
        level1.setHeight(myGame.getHeight());
        level1.setWidth(10000);
        addDummyEntities(level1, 1.0);
        addDummyEvents(level1, 2.0);
        dummyGame.addLevel(level1);

        Level level2 = new Level();
        level2.setHeight(myGame.getHeight());
        level2.setWidth(myGame.getWidth());
        addDummyEntities(level2, 2.0);
        addDummyEvents(level2, 3.0);
        dummyGame.addLevel(level2);

        Level level3 = new Level();
        level3.setHeight(myGame.getHeight());
        level3.setWidth(myGame.getWidth());
        addDummyEntities(level3, 3.0);
        addDummyEvents(level3, 4.0);
        dummyGame.addLevel(level3);

        Level level4 = new Level();
        level4.setHeight(myGame.getHeight());
        level4.setWidth(myGame.getWidth());
        addDummyEntities(level4, 4.0);
        addDummyEvents(level4, 5.0);
        dummyGame.addLevel(level4);

    }

    private void addDummyEvents(Level level1, Double next) {

        /**
         * Event: Press L to decrement flappy's lives by 1
         * Actions:
         * 1. Decrement LivesComponent by 1
         */
        Event lifeKeyInputEvent = new Event();
        lifeKeyInputEvent.addConditions(new StringEqualToCondition(NameComponent.class, "game"));
        lifeKeyInputEvent.addInputs(KeyCode.L);
        lifeKeyInputEvent.addActions(new ChangeLivesAction(NumericAction.ModifyType.RELATIVE, 1.0));

        /**
         * Event: Press Right Arrow Key to move flappy to the right
         * Actions:
         * 1. increase xPosition of flappy by 5
         */
        Event flappyMoveRightEvent = new Event();
        flappyMoveRightEvent.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        flappyMoveRightEvent.addInputs(KeyCode.RIGHT);
        flappyMoveRightEvent.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, 5.0));


        //flip gravity
        Event gravity = new Event();
        gravity.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        gravity.addConditions(new EqualToCondition(TimerComponent.class,0.0));
        gravity.addInputs(KeyCode.SHIFT);
        gravity.addActions(new YAccelerationAction(NumericAction.ModifyType.SCALE, -1.0));
        gravity.addConditions(new LessThanCondition(TimerComponent.class,5.0));



        /**
         * Event: Flappy collides with Ghost on the Right (Right side of flappy hits ghost):
         * 1. set Ghost1 xVelocity to 0
         */
        RightCollisionEvent RightFlappyCollisionWithGhost = new RightCollisionEvent("Ghost1", false);
        RightFlappyCollisionWithGhost.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        RightFlappyCollisionWithGhost.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, 0.0));

        /**
         * Make bottom collision event between flappy and ghost1 and vice versa
         */
        CollisionEvent BottomGhostCollisionWithFlappy = BottomCollisionEvent.makeBottomBounceEvent("flappy", "Ghost1", false);
        CollisionEvent BottomFlappyCollisionWithGhost = BottomCollisionEvent.makeBottomBounceEvent("Ghost1", "flappy", false);


        /**
         * Event: Ghost collides with another Ghost from the Right (Right side of ghost hits ghost): (Ghosts colliding with each other)
         * 1. set Ghost1 xVelocity to -2
         * 2. modify Ghost1 xPosition by -5
         */
        //CollisionEvent RightGhostCollisionWithGhost = CollisionEvent.makeBounceEvent("Ghost1", "Ghost1", RightCollisionEvent.class, false);
        CollisionEvent RightGhostCollisionWithGhost = new RightCollisionEvent("Ghost", false);
        RightGhostCollisionWithGhost.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost"));
        RightGhostCollisionWithGhost.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, -2.0));
        RightGhostCollisionWithGhost.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE,-5.0));

        /**
         * Event: Ghost1 collides with another Ghost1 from the Bottom (Bottom side of ghost hits ghost): (Ghosts colliding with each other)
         * 1. set Ghost1 yVelocity to -2
         * 2. modify Ghost1 yPosition by -5
         * 3. modify Ghost1 Xposition by 10
         */
        CollisionEvent BottomGhostCollisionWithGhost = BottomCollisionEvent.makeBottomBounceEvent("Ghost", "Ghost", false);


        /**
         * Event: Basketball bb collides with Basketball "bb" from the Bottom (Bottom side of bb hits bb): (Basketballs colliding with each other)
         * 1. set bb yVelocity
         * 2. modify bb yPosition
         * 3. modify bb Xposition
         */
        CollisionEvent BottomBBCollisionWithBB = new BottomCollisionEvent("bb", false);
        BottomBBCollisionWithBB.addConditions(new StringEqualToCondition(NameComponent.class, "bb"));
        BottomBBCollisionWithBB.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -8.0));
        BottomBBCollisionWithBB.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-5.0));
        BottomBBCollisionWithBB.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,10.0));

        /**
         * Event: Ghost1 collides with Basketball "bb" from the Right (Right side of Ghost1 hits bb):
         * 1. set ghost1 xVelocity to -8
         * 3. modify ghost1 xPosition by -5
         */
        CollisionEvent RightGhostCollisionWithBB = new RightCollisionEvent("bb", false);
        RightGhostCollisionWithBB.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost"));
        RightGhostCollisionWithBB.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, -8.0));
        RightGhostCollisionWithBB.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE,-5.0));

        /**
         * Event: Ghost1 collides with Basketball "bb" from the bottom (bottom side of Ghost1 hits bb):
         * 1. set ghost1 yVelocity to -5
         * 2. modify ghost1 yPosition by -5
         * 3. modify ghost1 Xposition by 10
         */
        CollisionEvent BottomGhostCollisionWithBB = new BottomCollisionEvent("bb", false);
        BottomGhostCollisionWithBB.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost"));
        BottomGhostCollisionWithBB.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -5.0));
        BottomGhostCollisionWithBB.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-5.0));
        BottomGhostCollisionWithBB.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,10.0));

        /**
         * Event: Basketball "bb" collides with Ghost1 from the Right (Right side of Basketball hits Ghost1):
         * 1. set bb xVelocity to -5
         * 2. move bb xPosition by -5
         */
        CollisionEvent RightBBCollisionWithGhost = new RightCollisionEvent("Ghost", false);
        RightBBCollisionWithGhost.addConditions(new StringEqualToCondition(NameComponent.class, "bb"));
        RightBBCollisionWithGhost.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, -5.0));
        RightBBCollisionWithGhost.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE,-5.0));

        /**
         * Event: Basketball "bb" collides with Ghost1 from the Bottom (bottom side of Basketball hits Ghost1):
         * 1. set bb yVelocity
         * 2. move bb yPosition by -5
         * 3. modify bb xPosition
         */
        CollisionEvent BottomBBcollisionWithGhost = new BottomCollisionEvent("Ghost", false);
        BottomBBcollisionWithGhost.addConditions(new StringEqualToCondition(NameComponent.class, "bb"));
        BottomBBcollisionWithGhost.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -5.0));
        BottomBBcollisionWithGhost.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-5.0));
        BottomBBcollisionWithGhost.addActions(new XPositionAction(NumericAction.ModifyType.RANDOM,10.0));

        /**
         * Event: Ghost1 collides with flappy from the Left (Left side of Ghost1 hits flappy):
         * 1. set Ghost1 xVelocity to 1
         * 2. move Ghost1 xPosition by 80
         */
        LeftCollisionEvent LeftGhostCollisionWithFlappy = new LeftCollisionEvent("flappy", false);
        LeftGhostCollisionWithFlappy.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost"));
        LeftGhostCollisionWithFlappy.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, 1.0));
        LeftGhostCollisionWithFlappy.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE,80.0));
        //LeftGhostCollisionWithFlappy.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-50.0));

        /**
         * Event: Ghost1 collides with flappy from the Right (Right side of Ghost1 hits flappy):
         * 1. set Ghost1 xVelocity to -1
         * 2. move Ghost1 xPosition by -80
         */
        CollisionEvent GhostCollidesWithFlappy = new RightCollisionEvent("flappy", false);
        GhostCollidesWithFlappy.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost"));
        GhostCollidesWithFlappy.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, -1.0));
        GhostCollidesWithFlappy.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE,-80.0));
        //GhostCollidesWithFlappy.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-50.0));

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
         * Event: Press J to make Ghost1 jump:
         * 1. set y velocity so that ghost moves upwards (jumps)
         */
        Event Ghost1Jump = new Event();
        Ghost1Jump.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost"));
        Ghost1Jump.addInputs(KeyCode.J);
        Ghost1Jump.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -5.0));

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
        flappyJump.addConditions(new LessThanCondition(ValueComponent.class,10.0));
        flappyJump.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -5.0));
        flappyJump.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-.02));
        //flappyJump.addActions(new YAccelerationAction(NumericAction.ModifyType.ABSOLUTE,0.2));
        flappyJump.addActions(new ValueAction(NumericAction.ModifyType.RELATIVE,1.0));
        flappyJump.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, 100.0));
        //flappyJump.addActions(new SoundAction("bach_chaconne"));

        /**
         * Event: Press M to:
         * 1. Play the Mario Theme
         */
        Event music = new Event();
        music.addInputs(KeyCode.M);
        music.addActions(new SoundAction("mario_theme"));

        /**
         * Event: flappy collides with Block from the Bottom (bottom of flappy hits block):
         * 1. Reset flappy's jump counter to 0
         * 2. set flappy's yVelocity to 0
         */
        BottomCollisionEvent flappyOnPlatform = new BottomCollisionEvent("Block", false);
        flappyOnPlatform.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        flappyOnPlatform.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,0.0));
        flappyOnPlatform.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-5.0));
        flappyOnPlatform.addActions(new ValueAction(NumericAction.ModifyType.ABSOLUTE,0.0));

        /**
         * Event: and other side
         */
        TopCollisionEvent flappyOnPlatformTop = new TopCollisionEvent("Block", false);
        flappyOnPlatformTop.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        flappyOnPlatformTop.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,0.0));
        flappyOnPlatformTop.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,5.0));
        flappyOnPlatformTop.addActions(new ValueAction(NumericAction.ModifyType.ABSOLUTE,0.0));



        BottomCollisionEvent Ghost1OnPlatform = new BottomCollisionEvent("Block", false);
        Ghost1OnPlatform.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost"));
        Ghost1OnPlatform.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,0.0));
        Ghost1OnPlatform.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-2.0));

        TopCollisionEvent Ghost1OnPlatformTop = new TopCollisionEvent("Block", false);
        Ghost1OnPlatformTop.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost"));
        Ghost1OnPlatformTop.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,0.0));
        Ghost1OnPlatformTop.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,2.0));




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
        Mushroom.addComponent(new SpriteComponent("mushroom.png"));
        Mushroom.addComponent(new NameComponent("Ghost1"));
        Mushroom.addComponent(new XVelocityComponent(-2.0));
        Mushroom.addComponent(new YVelocityComponent(0.0));
        Mushroom.addComponent(new XAccelerationComponent(0.0));
        Mushroom.addComponent(new YAccelerationComponent(0.2));
        Mushroom.addComponent(new CollisionComponent(true));
        Mushroom.addComponent(new TimerComponent(100.0));
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
         * 1. A Mushroom will spawn
         * 2. The Basketball's timer will reset to 100
         */
        Event timeEvent = new Event();
        timeEvent.addConditions(new StringEqualToCondition(NameComponent.class, "Basketball"));
        timeEvent.addConditions(new EqualToCondition(TimerComponent.class, 0.0));
        timeEvent.addActions(new AddEntityInstantAction(Mushroom));
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
        flappyFallsEvent.addConditions(new GreaterThanCondition(YPositionComponent.class, 1000.0));
        flappyFallsEvent.addActions(new YPositionAction(NumericAction.ModifyType.ABSOLUTE, 200.0));
        flappyFallsEvent.addActions(new XPositionAction(NumericAction.ModifyType.ABSOLUTE, 50.0));
        flappyFallsEvent.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, 0.0));
        flappyFallsEvent.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, -10.0));
        AssociatedEntityAction updateGameLivesAction = new AssociatedEntityAction();
        updateGameLivesAction.setAction(NumericAction.ModifyType.RELATIVE, -1.0, LivesComponent.class);
        flappyFallsEvent.addActions(updateGameLivesAction);

        /**
         * Add All Events to the Level
         */
        level1.addEvent(gravity);
        level1.addEvent(flappyMoveRightEvent);
        level1.addEvent(timeEvent);
        level1.addEvent(flappyMoveLeft);
        level1.addEvent(flappyOnPlatformTop);
        level1.addEvent(flappyOnPlatform);
        level1.addEvent(flappyJump);


        level1.addEvent(Ghost1Jump);
        level1.addEvent(RightFlappyCollisionWithGhost);
        level1.addEvent(LeftGhostCollisionWithFlappy);

        level1.addEvent(GhostCollidesWithFlappy);

        level1.addEvent(Ghost1OnPlatform);
        level1.addEvent(Ghost1OnPlatformTop);
        level1.addEvent(SpawnMushroomEvent);
        level1.addEvent(SpawnBasketball);
        level1.addEvent(music);
        level1.addEvent(RightGhostCollisionWithGhost);
        level1.addEvent(BottomGhostCollisionWithGhost);


        level1.addEvent(flappyFallsEvent);
        level1.addEvent(lifeKeyInputEvent);

        List<Event> spawnEvents = getSpawnEvents(new ArrayList<>(Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0, 60.0,
                70.0, 80.0, 90.0)));
        spawnEvents.forEach(e -> level1.addEvent(e));


    }

    private List<Event> getSpawnEvents(List<Double> times) {
        var r = new Random();
        List<Event> eventlist = new ArrayList<>();
        for (double time: times) {
            Entity dummy = new Entity();
            dummy.addComponent(new SpriteComponent("basketball"));
            double xpos = (r.nextInt(5) + 1) * 100.0;
            dummy.addComponent(new XPositionComponent(xpos));
            dummy.addComponent(new YPositionComponent(0.0));
            dummy.addComponent(new ZPositionComponent(1.0));
            dummy.addComponent(new WidthComponent(100.0));
            dummy.addComponent(new HeightComponent(100.0));
            dummy.addComponent(new NameComponent("dummy"+time));
            dummy.addComponent(new XVelocityComponent(0.0));
            dummy.addComponent(new YVelocityComponent(0.0));
            dummy.addComponent(new XAccelerationComponent(0.0));
            dummy.addComponent(new YAccelerationComponent(0.2));
            //dummy.addComponent(new CollisionComponent(false));

//            BottomCollisionEvent bbOnPlatform = new BottomCollisionEvent("Block", true);
//            bbOnPlatform.addConditions(new StringEqualToCondition(NameComponent.class, "dummy"+time));
//            bbOnPlatform.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,-10.0));
//            bbOnPlatform.addActions(new YPositionAction(NumericAction.ModifyType.ABSOLUTE,-3.0));

            Event spawn = new Event();
            spawn.addConditions(new StringEqualToCondition(NameComponent.class, "game"));
            spawn.addConditions(new EqualToCondition(TimerComponent.class, time));
            spawn.addInputConditions(new InputCondition(KeyCode.Q));
            //spawn.addInputs(KeyCode.Q);
            spawn.addActions(new AddEntityInstantAction(dummy));
            //spawn.addActions(new TimerAction(NumericAction.ModifyType.ABSOLUTE, 100.0));

            eventlist.add(spawn);
            //eventlist.add(bbOnPlatform);
        }
        Event lastEvent = new Event();
        lastEvent.addConditions(new StringEqualToCondition(NameComponent.class, "game"));
        lastEvent.addConditions(new EqualToCondition(TimerComponent.class, 0.0));
        lastEvent.addActions(new TimerAction(NumericAction.ModifyType.ABSOLUTE, 100.0));
        eventlist.add(lastEvent);
        return eventlist;
    }

    private void addDummyEntities(Level level, Double current) {
        /**
         * Define the Entities
         * Note: There needs to be a gameObject for each game
         */
        // Create the gameObject. This is the dummy object that authoring has to make for each game.
        Entity gameObject = new Entity();
        //Give the Game Object ScoreComponent and LivesComponent that will persist through the whole game (not just a level)
        //gameObject.addComponent(new ScoreComponent(0.0));
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

        Entity Ghost2 = new Entity();

        /**
         * Give the Entities their needed Components
         */

        //Give Flappy the needed components
        Flappy.addComponent(new NameComponent("flappy"));
        Flappy.addComponent(new SpriteComponent("ryan.png"));
        Flappy.addComponent(new XPositionComponent(10.0));
        Flappy.addComponent(new YPositionComponent(200.0));
        Flappy.addComponent(new ZPositionComponent(0.0));
        Flappy.addComponent(new WidthComponent(40.0));
        Flappy.addComponent(new HeightComponent(50.0));
        Flappy.addComponent(new XVelocityComponent(0.0));
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
        Flappy.addComponent(new SoundComponent("mario_theme"));
        Flappy.addComponent(new TimerComponent(100.0));
        Flappy.addComponent(new XVelocityComponent(2.0));
        Flappy.addComponent(new ScoreComponent(0.0));

        //Give Ghost1 the needed components
        Ghost1.addComponent(new XPositionComponent(10.0));
        Ghost1.addComponent(new YPositionComponent(200.0));
        Ghost1.addComponent(new ZPositionComponent(0.0));
        Ghost1.addComponent(new WidthComponent(40.0));
        Ghost1.addComponent(new HeightComponent(40.0));
        Ghost1.addComponent(new SpriteComponent("ghost.png"));
        Ghost1.addComponent(new NameComponent("Ghost"));
        Ghost1.addComponent(new XVelocityComponent(2.0));
        Ghost1.addComponent(new YVelocityComponent(0.0));
        Ghost1.addComponent(new XAccelerationComponent(0.0));
        Ghost1.addComponent(new YAccelerationComponent(0.1));
        Ghost1.addComponent(new CollisionComponent(true));

        //Give basketball the needed components
        BasketBall.addComponent(new XPositionComponent(90.0));
        BasketBall.addComponent(new YPositionComponent(100.0));
        BasketBall.addComponent(new ZPositionComponent(0.0));
        BasketBall.addComponent(new WidthComponent(200.0));
        BasketBall.addComponent(new HeightComponent(80.0));
        BasketBall.addComponent(new SpriteComponent("basketball"));
        BasketBall.addComponent(new NameComponent("Basketball"));
        //BasketBall.addComponent(new TimerComponent(100.0));
        //basketball has a timer component so that we can define an Event that will make mushrooms respawn everytime basketball's timer hits 0.

        //Give Ghost2 the needed components
        Ghost2.addComponent(new XPositionComponent(200.0));
        Ghost2.addComponent(new YPositionComponent(200.0));
        Ghost2.addComponent(new ZPositionComponent(0.0));
        Ghost2.addComponent(new WidthComponent(40.0));
        Ghost2.addComponent(new HeightComponent(40.0));
        Ghost2.addComponent(new SpriteComponent("ghost.png"));
        Ghost2.addComponent(new NameComponent("Ghost"));
        Ghost2.addComponent(new XVelocityComponent(2.0));
        Ghost2.addComponent(new YVelocityComponent(0.0));
        Ghost2.addComponent(new XAccelerationComponent(0.0));
        Ghost2.addComponent(new YAccelerationComponent(0.1));
        Ghost2.addComponent(new CollisionComponent(true));

        //Give the blocks their needed components
        MarioBlock1.addComponent(new XPositionComponent(10.0));
        MarioBlock1.addComponent(new YPositionComponent(50.0));
        MarioBlock1.addComponent(new ZPositionComponent(0.0));
        MarioBlock1.addComponent(new WidthComponent(10000.0));
        MarioBlock1.addComponent(new HeightComponent(100.0));
        MarioBlock1.addComponent(new SpriteComponent("mario_block.png"));
        MarioBlock1.addComponent(new CollisionComponent(false));
        MarioBlock1.addComponent(new HealthComponent(100.0));
        MarioBlock1.addComponent(new NameComponent("Block"));

        MarioBlock2.addComponent(new XPositionComponent(10.0));
        MarioBlock2.addComponent(new YPositionComponent(400.0));
        MarioBlock2.addComponent(new ZPositionComponent(0.0));
        MarioBlock2.addComponent(new WidthComponent(10000.0));
        MarioBlock2.addComponent(new HeightComponent(100.0));
        MarioBlock2.addComponent(new SpriteComponent("mario_block.png"));
        MarioBlock2.addComponent(new CollisionComponent(false));
        MarioBlock2.addComponent(new NameComponent("Block"));

        MarioBlock3.addComponent(new XPositionComponent(320.0));
        MarioBlock3.addComponent(new YPositionComponent(400.0));
        MarioBlock3.addComponent(new ZPositionComponent(0.0));
        MarioBlock3.addComponent(new WidthComponent(100.0));
        MarioBlock3.addComponent(new HeightComponent(100.0));
        MarioBlock3.addComponent(new SpriteComponent("mario_block.png"));
        MarioBlock3.addComponent(new CollisionComponent(false));
        MarioBlock3.addComponent(new NameComponent("Block"));




        //Add the entities to the level
        level.addEntity(gameObject);
        level.addEntity(Flappy);
        level.addEntity(Ghost1);
        level.addEntity(Ghost2);
//        level.addEntity(BasketBall);
        level.addEntity(MarioBlock1);
        level.addEntity(MarioBlock2);
        level.addEntity(MarioBlock3);
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
        dm.saveGameInfo("GravGod", "lucas", new GameCenterData("GravGod", "Become Legend",
                "ryan" +
                        ".png",
                "lucas"));
        dm.saveGameData("GravGod", "lucas", myGame);
    }
}

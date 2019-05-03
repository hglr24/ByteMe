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
        flappyMoveRightEvent.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, 2.0));

        /**
         * Event: Press Left Arrow Key to:
         * 1.Move Flappy to the left
         */
        Event flappyMoveLeft = new Event();
        flappyMoveLeft.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        flappyMoveLeft.addInputs(KeyCode.LEFT);
        flappyMoveLeft.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, -2.0));

        //flip gravity
        Event gravity = new Event();
        gravity.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        gravity.addConditions(new LessThanCondition(TimerComponent.class,2.0));
        gravity.addInputs(KeyCode.A);
        gravity.addActions(new YAccelerationAction(NumericAction.ModifyType.ABSOLUTE, -.2));
        gravity.addActions(new SpriteAction("ryan2.png"));


        //flip gravity
        Event gravityf = new Event();
        gravityf.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        gravityf.addConditions(new LessThanCondition(TimerComponent.class,2.0));
        gravityf.addInputs(KeyCode.D);
        gravityf.addActions(new YAccelerationAction(NumericAction.ModifyType.ABSOLUTE, .2));
        gravityf.addActions(new SpriteAction("ryan.png"));
        //use the value component, need duplicate events

        Event gravityG = new Event();
        gravityG.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost"));
        gravityG.addConditions(new LessThanCondition(TimerComponent.class,2.0));
        gravityG.addInputs(KeyCode.A);
        gravityG.addActions(new YAccelerationAction(NumericAction.ModifyType.SCALE, -1.0));
        //gravity.addActions(new SpriteAction("ryan2.png"));



        /**
         * Event: Flappy collides with Ghost on the Right (Right side of flappy hits ghost):
         * 1. set Ghost1 xVelocity to 0
         */
        RightCollisionEvent RightFlappyCollisionWithGhost = new RightCollisionEvent("Ghost", false);
        RightFlappyCollisionWithGhost.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        AssociatedEntityAction aea = new AssociatedEntityAction();
        aea.setAction(NumericAction.ModifyType.RELATIVE,-25.0,ScoreComponent.class);
        RightFlappyCollisionWithGhost.addActions(aea);

        RightCollisionEvent RightFlappyCollisionWithM = new RightCollisionEvent("mushroom", false);
        RightFlappyCollisionWithM.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        AssociatedEntityAction aeam = new AssociatedEntityAction();
        aeam.setAction(NumericAction.ModifyType.RELATIVE,25.0,ScoreComponent.class);
        RightFlappyCollisionWithM.addActions(aea);


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
        //CollisionEvent BottomGhostCollisionWithGhost = BottomCollisionEvent.makeBottomBounceEvent("Ghost", "Ghost", false);


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
        LeftGhostCollisionWithFlappy.addActions(new DestroyAction(true));


        /**
         * Event: Ghost1 collides with flappy from the Right (Right side of Ghost1 hits flappy):
         * 1. set Ghost1 xVelocity to -1
         * 2. move Ghost1 xPosition by -80
         */
        CollisionEvent GhostCollidesWithFlappy = new RightCollisionEvent("flappy", false);
        GhostCollidesWithFlappy.addConditions(new StringEqualToCondition(NameComponent.class, "Ghost"));
        GhostCollidesWithFlappy.addActions(new DestroyAction(true));

        //GhostCollidesWithFlappy.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-50.0));


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
        flappyJump.addConditions(new LessThanCondition(ValueComponent.class,10.0));
        flappyJump.addConditions(new GreaterThanCondition(YAccelerationComponent.class,.1));
        flappyJump.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, -5.0));
        flappyJump.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-.02));
        flappyJump.addActions(new ValueAction(NumericAction.ModifyType.RELATIVE,1.0));
        //flappyJump.addActions(new YAccelerationAction(NumericAction.ModifyType.ABSOLUTE,0.2));
        //flappyJump.addActions(new ChangeScoreAction(NumericAction.ModifyType.RELATIVE, 100.0));
        flappyJump.addActions(new SoundAction("jump"));

        Event flappyJumpf = new Event();
        flappyJumpf.addConditions(new StringEqualToCondition(NameComponent.class, "flappy"));
        flappyJumpf.addInputs(KeyCode.DOWN);
        flappyJump.addConditions(new LessThanCondition(ValueComponent.class,10.0));
        flappyJumpf.addConditions(new LessThanCondition(YAccelerationComponent.class,-.1));
        flappyJumpf.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE, 5.0));
        flappyJumpf.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,.02));
        flappyJump.addActions(new ValueAction(NumericAction.ModifyType.RELATIVE,1.0));
        flappyJumpf.addActions(new SoundAction("jump"));



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


        BottomCollisionEvent MOnPlatform = new BottomCollisionEvent("Block", false);
        MOnPlatform.addConditions(new StringEqualToCondition(NameComponent.class, "mushroom"));
        MOnPlatform.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,0.0));
        MOnPlatform.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,-2.0));

        TopCollisionEvent MOnPlatformTop = new TopCollisionEvent("Block", false);
        MOnPlatformTop.addConditions(new StringEqualToCondition(NameComponent.class, "mushroom"));
        MOnPlatformTop.addActions(new YVelocityAction(NumericAction.ModifyType.ABSOLUTE,0.0));
        MOnPlatformTop.addActions(new YPositionAction(NumericAction.ModifyType.RELATIVE,2.0));


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
        level1.addEvent(gravityf);
        level1.addEvent(gravityG);
        level1.addEvent(flappyMoveRightEvent);
        level1.addEvent(flappyMoveLeft);
        level1.addEvent(flappyOnPlatformTop);
        level1.addEvent(flappyOnPlatform);
        level1.addEvent(flappyJump);
        level1.addEvent(flappyJumpf);


        level1.addEvent(RightFlappyCollisionWithGhost);
        level1.addEvent(LeftGhostCollisionWithFlappy);

        level1.addEvent(GhostCollidesWithFlappy);

        level1.addEvent(Ghost1OnPlatform);
        level1.addEvent(Ghost1OnPlatformTop);
        level1.addEvent(RightGhostCollisionWithGhost);

        level1.addEvent(MOnPlatform);
        level1.addEvent(MOnPlatformTop);
        level1.addEvent(RightFlappyCollisionWithM);



        level1.addEvent(flappyFallsEvent);
        level1.addEvent(lifeKeyInputEvent);
        addGhosts(level1);
        addMushrooms(level1);


    }

    private void addGhosts(Level level) {
        var r = new Random();
        for (int i =0;i<30;i++) {
            Entity Ghost1 = new Entity();
            double xpos = (r.nextInt(80) + 1) * 100.0+2000.0;
            boolean top = r.nextBoolean();
            double ypos = top ? 70.0 : 200.0;
            Ghost1.addComponent(new XPositionComponent(xpos));
            Ghost1.addComponent(new YPositionComponent(ypos));
            Ghost1.addComponent(new ZPositionComponent(0.0));
            Ghost1.addComponent(new WidthComponent(40.0));
            Ghost1.addComponent(new HeightComponent(40.0));
            Ghost1.addComponent(new SpriteComponent("ghost.png"));
            Ghost1.addComponent(new NameComponent("Ghost"));
            Ghost1.addComponent(new XVelocityComponent(-2.0));
            Ghost1.addComponent(new YVelocityComponent(0.0));
            Ghost1.addComponent(new XAccelerationComponent(0.0));
            Ghost1.addComponent(new YAccelerationComponent(0.1));
            Ghost1.addComponent(new CollisionComponent(true));
            Ghost1.addComponent(new TimerComponent(150.0));

            level.addEntity(Ghost1);

        }

    }

    private void addMushrooms(Level level) {
        var r = new Random();
        for (int i =0;i<30;i++) {
            Entity Ghost1 = new Entity();
            double xpos = (r.nextInt(80) + 1) * 100.0+2000.0;
            boolean top = r.nextBoolean();
            double ypos = top ? 70.0 : 200.0;
            Ghost1.addComponent(new XPositionComponent(xpos));
            Ghost1.addComponent(new YPositionComponent(ypos));
            Ghost1.addComponent(new ZPositionComponent(0.0));
            Ghost1.addComponent(new WidthComponent(40.0));
            Ghost1.addComponent(new HeightComponent(40.0));
            Ghost1.addComponent(new SpriteComponent("mushroom.png"));
            Ghost1.addComponent(new NameComponent("mushroom"));
            Ghost1.addComponent(new XVelocityComponent(-2.0));
            Ghost1.addComponent(new YVelocityComponent(0.0));
            Ghost1.addComponent(new XAccelerationComponent(0.0));
            Ghost1.addComponent(new YAccelerationComponent(0.1));
            Ghost1.addComponent(new CollisionComponent(true));
            Ghost1.addComponent(new TimerComponent(100.0));

            level.addEntity(Ghost1);

        }

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
        Entity MarioBlock1 = new Entity();
        Entity MarioBlock2 = new Entity();
        Entity MarioBlock3 = new Entity();

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
        Flappy.addComponent(new SoundComponent("song2"));
        Flappy.addComponent(new TimerComponent(100.0));
        Flappy.addComponent(new XVelocityComponent(3.0));
        Flappy.addComponent(new ScoreComponent(10.0));
        Flappy.addComponent(new AssociatedEntityComponent(gameObject));


        //Give the blocks their needed components
        MarioBlock1.addComponent(new XPositionComponent(10.0));
        MarioBlock1.addComponent(new YPositionComponent(50.0));
        MarioBlock1.addComponent(new ZPositionComponent(0.0));
        MarioBlock1.addComponent(new WidthComponent(10000.0));
        MarioBlock1.addComponent(new HeightComponent(25.0));
        MarioBlock1.addComponent(new SpriteComponent("mario_block.png"));
        MarioBlock1.addComponent(new CollisionComponent(false));
        MarioBlock1.addComponent(new HealthComponent(100.0));
        MarioBlock1.addComponent(new NameComponent("Block"));

        MarioBlock2.addComponent(new XPositionComponent(10.0));
        MarioBlock2.addComponent(new YPositionComponent(400.0));
        MarioBlock2.addComponent(new ZPositionComponent(0.0));
        MarioBlock2.addComponent(new WidthComponent(10000.0));
        MarioBlock2.addComponent(new HeightComponent(25.0));
        MarioBlock2.addComponent(new SpriteComponent("mario_block.png"));
        MarioBlock2.addComponent(new CollisionComponent(false));
        MarioBlock2.addComponent(new NameComponent("Block"));

        MarioBlock3.addComponent(new XPositionComponent(10.0));
        MarioBlock3.addComponent(new YPositionComponent(225.0));
        MarioBlock3.addComponent(new ZPositionComponent(0.0));
        MarioBlock3.addComponent(new WidthComponent(10000.0));
        MarioBlock3.addComponent(new HeightComponent(25.0));
        MarioBlock3.addComponent(new SpriteComponent("mario_block.png"));
        MarioBlock3.addComponent(new CollisionComponent(false));
        MarioBlock3.addComponent(new NameComponent("Block"));




        //Add the entities to the level
        level.addEntity(gameObject);
        level.addEntity(Flappy);
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

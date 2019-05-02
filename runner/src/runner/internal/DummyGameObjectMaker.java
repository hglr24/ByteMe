package runner.internal;

import data.external.DataManager;
import data.external.GameCenterData;
import engine.external.Entity;
import engine.external.Level;
import engine.external.actions.*;
import engine.external.component.*;
import engine.external.conditions.*;
import engine.external.events.Event;
import javafx.scene.input.KeyCode;
import runner.external.Game;

import java.util.*;

/**
 * Makes a game object for use in Runner Test
 *
 * @author Louis Jensen, Feroze Mohideen
 */
public class DummyGameObjectMaker {
    private Game myGame;

    /**
     * Constructor that creates a dummy Game
     */
    public DummyGameObjectMaker() {
        myGame = new Game();
        initializeGame(myGame);
        serializeObject();
    }

    private void initializeGame(Game dummyGame) {
        Level level1 = new Level();
        level1.setHeight(myGame.getHeight());
        level1.setWidth(myGame.getWidth());
        List<Entity> gameObjects = addDummyEntities(level1, 1.0);
        addDummyEvents(level1, 2.0, gameObjects);
        dummyGame.addLevel(level1);

//                Level level2 = new Level();
//                level2.setHeight(myGame.getHeight());
//                level2.setWidth(myGame.getWidth());
//                addDummyEntities(level2, 2.0);
//                addDummyEvents(level2, 3.0, gameObjects);
//                dummyGame.addLevel(level2);
//
//                Level level3 = new Level();
//                level3.setHeight(myGame.getHeight());
//                level3.setWidth(myGame.getWidth());
//                addDummyEntities(level3, 3.0);
//                addDummyEvents(level3, 4.0, gameObjects);
//                dummyGame.addLevel(level3);
//
//                Level level4 = new Level();
//                level4.setHeight(myGame.getHeight());
//                level4.setWidth(myGame.getWidth());
//                addDummyEntities(level4, 4.0);
//                addDummyEvents(level4, 5.0, gameObjects);
//                dummyGame.addLevel(level4);

    }

    private void addDummyEvents(Level level1, Double next, List<Entity> gameObjects) {

        for (Entity e : gameObjects) {
            Event temp = new Event();
            temp.addConditions(new StringEqualToCondition(NameComponent.class,
                    (String) e.getComponent(NameComponent.class).getValue()));
            temp.addConditions(new GreaterThanCondition(ValueComponent.class, 0.0));
            AssociatedEntityAction updateGlobalScore = new AssociatedEntityAction();

            updateGlobalScore.setRelativeAction(
                    100.0, ScoreComponent.class);
            temp.addActions(updateGlobalScore);
            temp.addActions(new ValueAction(NumericAction.ModifyType.ABSOLUTE, 0.0));

            Event decScore = new Event();
            decScore.addConditions(new StringEqualToCondition(NameComponent.class,
                    (String) e.getComponent(NameComponent.class).getValue()));
            decScore.addConditions(new LessThanCondition(ValueComponent.class, -1.0));
            AssociatedEntityAction decreaseGlobalScore = new AssociatedEntityAction();
            decreaseGlobalScore.setRelativeAction(-100.0, ScoreComponent.class);
            decScore.addActions(decreaseGlobalScore);
            decScore.addActions(new ValueAction(NumericAction.ModifyType.ABSOLUTE, 0.0));
            level1.addEvent(temp);
            level1.addEvent(decScore);
        }

        List<Integer> notes = new ArrayList<>(Arrays.asList(

                3,4,3,4,3,4,3,1,4,2,1,2,3,2,3,4,
                3,4,3,4,3,4,3,1,4,2,1,2,3,2,3,4,
                3,4,3,4,3,4,3,1,4,2,1,2,3,2,3,4,
                3,4,3,4,3,4,3,1,4,2,1,2,3,2,3,4,
                //1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,

                4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2,
                3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1,
                4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2,
                3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1));
                //1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1




        List<Double> times = new ArrayList<>();
        for (double i = 0; i < 2048; i += 16) {
            times.add(i);
        }

        List<Double> introTimes = new ArrayList<>();
        for (double i = 2112; i < 2368; i += 64) {
            introTimes.add(i);
        }
        //System.out.println(notes.size() == times.size());

        List<Event> introEvents = getSpawnEvents(introTimes, gameObjects, new ArrayList<>(Arrays.asList(0,0,0,0)));
        List<Event> spawnEvents = getSpawnEvents(times, gameObjects,
                notes);

        spawnEvents.forEach(e -> level1.addEvent(e));
        introEvents.forEach(e-> level1.addEvent(e));
        //System.out.println(spawnEvents.size());

        Entity soundEntity = new Entity();
        soundEntity.addComponent(new NameComponent("sound"));
        soundEntity.addComponent(new ValueComponent(0.0));
        level1.addEntity(soundEntity);

        Event makeSound = new Event();
        makeSound.addConditions(new StringEqualToCondition(NameComponent.class, "sound"));
        makeSound.addConditions(new EqualToCondition(ValueComponent.class, 0.0));
        makeSound.addActions(new SoundAction("guitar_hero"));
        makeSound.addActions(new ValueAction(NumericAction.ModifyType.ABSOLUTE, 1.0));
        level1.addEvent(makeSound);


    }

    private List<Event> getSpawnEvents(List<Double> times, List<Entity> gameObjects, List<Integer> notes) {
        var locs = new ArrayList<>(Arrays.asList(100.0, 210.0, 320.0, 430.0, 540.0));
        List<Event> eventlist = new ArrayList<>();
        for (int z = 0; z < times.size(); z++) {
            Entity dummy = new Entity();
            int index = notes.get(z);
            System.out.println(index);
            double xpos = locs.get(index);
            dummy.addComponent(new XPositionComponent(xpos));
            dummy.addComponent(new YPositionComponent(0.0));
            dummy.addComponent(new ZPositionComponent(1.0));
            dummy.addComponent(new WidthComponent(100.0));
            dummy.addComponent(new HeightComponent(100.0));
            String name = getSaltString();

            String imageName = null;
            String imageFailName = null;

            dummy.addComponent(new NameComponent(name));
            dummy.addComponent(new XVelocityComponent(0.0));
            dummy.addComponent(new YVelocityComponent(8.0));
            dummy.addComponent(new XAccelerationComponent(0.0));
            dummy.addComponent(new YAccelerationComponent(0.0));
            //dummy.addComponent(new CollisionComponent(false));

            Event spawn = new Event();
            spawn.addConditions(new StringEqualToCondition(NameComponent.class, "game"));
            spawn.addConditions(new EqualToCondition(TimerComponent.class, times.get(z)));
            //spawn.addInputConditions(new InputCondition(KeyCode.Q));
            //spawn.addInputs(KeyCode.Q);
            spawn.addActions(new AddEntityInstantAction(dummy));

            KeyCode key = null;

            switch (index) {
                case 0:
                    key = KeyCode.Q;
                    imageName = "gh1.png";
                    imageFailName = "gh1b.png";
                    break;
                case 1:
                    key = KeyCode.W;
                    imageName = "gh2.png";
                    imageFailName = "gh2b.png";
                    break;
                case 2:
                    key = KeyCode.E;
                    imageName = "gh3.png";
                    imageFailName = "gh3b.png";
                    break;
                case 3:
                    key = KeyCode.R;
                    imageName = "gh4.png";
                    imageFailName = "gh4b.png";
                    break;
                case 4:
                    key = KeyCode.ENTER;
                    imageName = "gh1.png";
                    imageFailName = "gh5b.png";
                    break;
            }

            dummy.addComponent(new AssociatedEntityComponent(gameObjects.get(index)));
            dummy.addComponent(new DestroyComponent(false));
            dummy.addComponent(new SoundComponent("coin"));
            dummy.addComponent(new SpriteComponent(imageName));


            /**
             * When ALL the conditions of this event are met, it adds a    component to the
             */
            Event scoreEvent = new Event();
            scoreEvent.addConditions(new StringEqualToCondition(NameComponent.class, name));
            scoreEvent.addConditions(new GreaterThanCondition(YPositionComponent.class, 360.0));
            scoreEvent.addConditions(new LessThanCondition(YPositionComponent.class, 450.0));
            scoreEvent.addInputConditions(new InputCondition(key));
            AssociatedEntityAction updateScore = new AssociatedEntityAction();
            updateScore.setAction(NumericAction.ModifyType.RELATIVE, 100.0, ValueComponent.class);
            scoreEvent.addActions(updateScore);
            AssociatedEntityStringAction changeSpriteBack = new AssociatedEntityStringAction();
            changeSpriteBack.setAction("red_circle.png", SpriteComponent.class);
            scoreEvent.addActions(new DestroyAction(true));
            scoreEvent.addActions(changeSpriteBack);

            Event reallyBackToRed = new Event();
            reallyBackToRed.addConditions(new StringEqualToCondition(NameComponent.class,
                    (String) gameObjects.get(index).getComponent(NameComponent.class).getValue()));
            reallyBackToRed.addInputConditions(new InputCondition(key));
            reallyBackToRed.addActions(new SpriteAction("red_circle.png"));

            Event changeColorEvent = new Event();
            changeColorEvent.addConditions(new StringEqualToCondition(NameComponent.class, name));
            changeColorEvent.addConditions(new GreaterThanCondition(YPositionComponent.class, 370.0));
            changeColorEvent.addConditions(new LessThanCondition(YPositionComponent.class, 440.0));
            AssociatedEntityStringAction changeSprite = new AssociatedEntityStringAction();
            changeSprite.setAction("green_circle.png", SpriteComponent.class);
            changeColorEvent.addActions(changeSprite);

            Event missedItEvent = new Event();
            missedItEvent.addConditions(new StringEqualToCondition(NameComponent.class, name));
            missedItEvent.addConditions(new GreaterThanCondition(YPositionComponent.class, 440.0));
            AssociatedEntityStringAction changeSpriteBackRed = new AssociatedEntityStringAction();
            changeSpriteBackRed.setAction("red_circle.png", SpriteComponent.class);
            missedItEvent.addActions(changeSpriteBackRed);
            missedItEvent.addActions(new SpriteAction(imageFailName));


            Event killScore = new Event();
            killScore.addConditions(new StringEqualToCondition(NameComponent.class, name));
            killScore.addConditions(new EqualToCondition(YPositionComponent.class, 600.0));
            AssociatedEntityAction decrementScore = new AssociatedEntityAction();
            decrementScore.setRelativeAction(-100.0, ValueComponent.class);
            killScore.addActions(decrementScore);

            eventlist.add(spawn);
            eventlist.add(scoreEvent);
            eventlist.add(changeColorEvent);
            eventlist.add(missedItEvent);
            eventlist.add(reallyBackToRed);
            eventlist.add(killScore);

        }
        Event lastEvent = new Event();
        lastEvent.addConditions(new StringEqualToCondition(NameComponent.class, "game"));
        lastEvent.addConditions(new EqualToCondition(TimerComponent.class, 0.0));
        lastEvent.addActions(new TimerAction(NumericAction.ModifyType.ABSOLUTE, 2048.0));
        eventlist.add(lastEvent);
        return eventlist;
    }

    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    private List<Entity> addDummyEntities(Level level, Double current) {
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
        gameObject.addComponent(new TimerComponent(2368.0));

        //Create the entities in the level

        Entity MarioBlock1 = new Entity();
        Entity MarioBlock2 = new Entity();
        Entity MarioBlock3 = new Entity();
        Entity MarioBlock4 = new Entity();
        Entity MarioBlock5 = new Entity();




        //Give the blocks their needed components
        MarioBlock1.addComponent(new XPositionComponent(100.0));
        MarioBlock1.addComponent(new YPositionComponent(400.0));
        MarioBlock1.addComponent(new ZPositionComponent(0.0));
        MarioBlock1.addComponent(new WidthComponent(100.0));
        MarioBlock1.addComponent(new HeightComponent(100.0));
        MarioBlock1.addComponent(new SpriteComponent("red_circle.png"));
        MarioBlock1.addComponent(new CollisionComponent(false));
        MarioBlock1.addComponent(new HealthComponent(100.0));
        MarioBlock1.addComponent(new NameComponent("Block1"));
        MarioBlock1.addComponent(new GroupComponent("Block"));
        MarioBlock1.addComponent(new ValueComponent(0.0));

        //MarioBlock1.addComponent(new WidthComponent(500.0));

        MarioBlock2.addComponent(new XPositionComponent(210.0));
        MarioBlock2.addComponent(new YPositionComponent(400.0));
        MarioBlock2.addComponent(new ZPositionComponent(0.0));
        MarioBlock2.addComponent(new WidthComponent(100.0));
        MarioBlock2.addComponent(new HeightComponent(100.0));
        MarioBlock2.addComponent(new SpriteComponent("red_circle.png"));
        MarioBlock2.addComponent(new CollisionComponent(false));
        MarioBlock2.addComponent(new NameComponent("Block2"));
        MarioBlock2.addComponent(new GroupComponent("Block"));
        MarioBlock2.addComponent(new ValueComponent(0.0));


        MarioBlock3.addComponent(new XPositionComponent(320.0));
        MarioBlock3.addComponent(new YPositionComponent(400.0));
        MarioBlock3.addComponent(new ZPositionComponent(0.0));
        MarioBlock3.addComponent(new WidthComponent(100.0));
        MarioBlock3.addComponent(new HeightComponent(100.0));
        MarioBlock3.addComponent(new SpriteComponent("red_circle.png"));
        MarioBlock3.addComponent(new CollisionComponent(false));
        MarioBlock3.addComponent(new NameComponent("Block3"));
        MarioBlock3.addComponent(new GroupComponent("Block"));
        MarioBlock3.addComponent(new ValueComponent(0.0));


        MarioBlock4.addComponent(new XPositionComponent(430.0));
        MarioBlock4.addComponent(new YPositionComponent(400.0));
        MarioBlock4.addComponent(new ZPositionComponent(0.0));
        MarioBlock4.addComponent(new WidthComponent(100.0));
        MarioBlock4.addComponent(new HeightComponent(100.0));
        MarioBlock4.addComponent(new SpriteComponent("red_circle.png"));
        MarioBlock4.addComponent(new CollisionComponent(false));
        MarioBlock4.addComponent(new NameComponent("Block4"));
        MarioBlock4.addComponent(new GroupComponent("Block"));
        MarioBlock4.addComponent(new ValueComponent(0.0));


        MarioBlock5.addComponent(new XPositionComponent(540.0));
        MarioBlock5.addComponent(new YPositionComponent(400.0));
        MarioBlock5.addComponent(new ZPositionComponent(0.0));
        MarioBlock5.addComponent(new WidthComponent(100.0));
        MarioBlock5.addComponent(new HeightComponent(100.0));
        MarioBlock5.addComponent(new SpriteComponent("red_circle.png"));
        MarioBlock5.addComponent(new CollisionComponent(false));
        MarioBlock5.addComponent(new NameComponent("Block5"));
        MarioBlock5.addComponent(new GroupComponent("Block"));
        MarioBlock5.addComponent(new ValueComponent(0.0));


        MarioBlock1.addComponent(new AssociatedEntityComponent(gameObject));
        MarioBlock2.addComponent(new AssociatedEntityComponent(gameObject));
        MarioBlock3.addComponent(new AssociatedEntityComponent(gameObject));
        MarioBlock4.addComponent(new AssociatedEntityComponent(gameObject));
        MarioBlock5.addComponent(new AssociatedEntityComponent(gameObject));


        //Add the entities to the level
        level.addEntity(gameObject);

        level.addEntity(MarioBlock1);
        level.addEntity(MarioBlock2);
        level.addEntity(MarioBlock3);
        level.addEntity(MarioBlock4);
        level.addEntity(MarioBlock5);

        return Arrays.asList(MarioBlock1, MarioBlock2, MarioBlock3, MarioBlock4, MarioBlock5);

    }

    /**
     * Gets dummy game
     *
     * @param dummyString symbolizes game name in actual call
     * @return dummy Game object
     */
    public Game getGame(String dummyString) {
        return myGame;
    }

    /**
     * Saves dummy game to data base
     */

    public void serializeObject() {
        DataManager dm = new DataManager();
        dm.saveGameInfo("shoot those hoops", "fzero", new GameCenterData("shoot those hoops",
                "Press Q W E R and T when the balls go through the rings to score",
                "basketball",
                "fzero"));
        dm.saveGameData("shoot those hoops", "fzero", myGame);
    }
}

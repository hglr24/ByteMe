package factory;

import actions.Action;
import actions.NumericAction;
import actions.XPositionAction;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import data.external.DataManager;
import engine.external.Entity;
import engine.external.component.HealthComponent;
import runner.external.Game;
import runner.external.GameCenterData;

import java.io.FileNotFoundException;


public class SerializationTester {
    private XStream mySerializer;
    private Action deserialized;

    public SerializationTester(){
        mySerializer = new XStream(new DomDriver());
    }

    public void saveAndMakeMario(){
        Entity myMario = new Entity();
        String mySavedEnemy = mySerializer.toXML(myMario);
        Entity mySecondMario = (Entity)mySerializer.fromXML(mySavedEnemy);
        mySecondMario.printMyComponents();
    }

    public void saveAndMakeNewGameWithObject() {
        DataManager dm = new DataManager();
        dm.createGameFolder("TestGameName");
        Entity myMario = new Entity();
        dm.createGameFolder("RyanGame");
        dm.saveGameData("RyanGame", myMario);
        //Entity mySecondMario = (Entity)dm.loadGameData("RyanGame");
        //mySecondMario.printMyComponents();
    }

    public void testObjectReferences(){
        DataManager dm = new DataManager();
        Game game = new Game();
        Entity mario = new Entity();
        HealthComponent health = new HealthComponent(20.0D);
        Entity ryan = new Entity();
        dm.createGameFolder("LucasGame");
        dm.saveGameData("LucasGame", game);
    }

    public void testGameCenterInfo() {
        GameCenterData test = new GameCenterData();
        test.setTitle("TESTING GAME!!!!");
        test.setDescription("this is just a simple test");
        DataManager dm = new DataManager();
        dm.createGameFolder("testingGameInfo");
        dm.saveGameInfo("testingGameInfo", test);
    }

    public void createYeet(){
        DummyGameMaker dummy = new DummyGameMaker();
        Game game = dummy.getGame("game1");
        DataManager dm = new DataManager();
        dm.createGameFolder("YeetGame1");
        dm.saveGameData("YeetGame1", game);
    }

    public void testActions(){
        DataManager dm = new DataManager();
        XPositionAction myPos = new XPositionAction(NumericAction.ModifyType.RELATIVE, 10.0);
        dm.createGameFolder("ActionTest");
        dm.saveGameData("ActionTest", myPos);

        try {
            deserialized = (Action) dm.loadGameData("ActionTest");
            System.out.println("Here");
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load game data");
            e.printStackTrace();
        }
    }
}


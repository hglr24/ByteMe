package factory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import data.external.DataManager;
import engine.external.Component;
import engine.external.Entity;
import engine.external.HealthComponent;
import engine.external.VisibilityComponent;
import runner.external.Game;


public class SerializationTester {
    private XStream mySerializer;

    public SerializationTester(){
        mySerializer = new XStream(new DomDriver());
    }

    public void saveAndMakeMario(){
        Entity myMario = new Entity();
        myMario.addComponent(new HealthComponent<>(10.0));
        myMario.addComponent(new VisibilityComponent<>(true));
        String mySavedEnemy = mySerializer.toXML(myMario);
        Entity mySecondMario = (Entity)mySerializer.fromXML(mySavedEnemy);
        mySecondMario.printMyComponents();
    }

    public void saveAndMakeNewGameWithObject() {
        DataManager dm = new DataManager();
        dm.createGameFolder("TestGameName");
        Entity myMario = new Entity();
        myMario.addComponent(new HealthComponent<>(20.0));
        myMario.addComponent(new VisibilityComponent<>(false));
        dm.createGameFolder("RyanGame");
        dm.saveGameData("RyanGame", myMario);
//        dm.saveObjectToXML("TestGameName", myMario);
        Entity mySecondMario = (Entity)dm.loadGameData("RyanGame");
        mySecondMario.printMyComponents();
    }

    public void testObjectReferences(){
        DataManager dm = new DataManager();
        Game game = new Game();
        Entity mario = new Entity();
        HealthComponent health = new HealthComponent(20.0D);
        Entity ryan = new Entity();
        mario.addComponent(health);
        ryan.addComponent(health);
//        game.addEntity(mario);
//        game.addEntity(ryan);
        dm.createGameFolder("LucasGame");
        dm.saveGameData("LucasGame", game);
    }
}

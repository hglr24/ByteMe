package factory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import engine.external.Entity;
import engine.external.HealthComponent;
import engine.external.VisibilityComponent;



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

}

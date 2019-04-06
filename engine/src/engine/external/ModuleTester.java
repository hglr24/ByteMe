package engine.external;

import engine.external.component.NameComponent;
import engine.external.component.PositionComponent;

public class ModuleTester {
    public static void main(String[] args){
        Entity myEntity = new Entity();
        myEntity.addComponent(new NameComponent("Doovall"));
//        myEntity.addComponent(new XPositionComponent<>(10.0));
//        myEntity.addComponent(new PositionComponent(10.0));

    }
}

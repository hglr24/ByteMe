package engine.external;

public class ModuleTester {
    public static void main(String[] args){
        Entity myEntity = new Entity();
        myEntity.addComponent(new NameComponent<>("Doovall"));
        //myEntity.addComponent(new XPositionComponent<>(10.0));

    }
}

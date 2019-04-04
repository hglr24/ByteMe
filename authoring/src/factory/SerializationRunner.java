package factory;

public class SerializationRunner {
    public static void main(String[] args){
        SerializationTester myTester = new SerializationTester();
        myTester.saveAndMakeMario();
        System.out.println("In the SerializationRunner");
        myTester.saveAndMakeNewGameWithObject();
        myTester.testObjectReferences();
    }
}

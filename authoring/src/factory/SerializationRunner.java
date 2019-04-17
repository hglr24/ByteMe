package factory;

import java.io.IOException;

public class SerializationRunner {
    public static void main(String[] args){
        SerializationTester myTester = new SerializationTester();
//        myTester.saveAndMakeMario();
//        System.out.println("Is this printed");
//        myTester.saveAndMakeNewGameWithObject();
//        myTester.testObjectReferences();
//        myTester.testGameCenterInfo();
//        myTester.testDatabaseConnection();
//        myTester.testSavingImages();
//
//        try {
//            myTester.testLoadingImages();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        myTester.testSavingSounds();

        try {
            myTester.testLoadingSounds();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//https://www.youtube.com/watch?v=hqHyCZkon34
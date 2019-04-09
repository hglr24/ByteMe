package factory;

import data.external.DataManager;
import data.external.DatabaseEngine;
import runner.external.Game;

public class SerializationRunner {
    public static void main(String[] args){
//        SerializationTester myTester = new SerializationTester();
//        myTester.saveAndMakeMario();
//        System.out.println("Is this printed");
//        myTester.saveAndMakeNewGameWithObject();
//        myTester.testObjectReferences();
        Game game = new Game();
        Game gameTwo = new Game();
//        DatabaseEngine de =new DatabaseEngine();
//        if (! de.open()){
//            System.out.println("Can't open");
//        };
//        de.close();
//        DataManager dataManager = new DataManager();
//        dataManager.createGameFolder("FolderNameDatabases", "DatabaseGame");
//        dataManager.saveGameData("DatabaseGame", game);
//        dataManager.saveGameInfo("DatabaseGame", gameTwo);
        DataManager dm = new DataManager();
        dm.createGameFolder("TestName");
        dm.saveGameData("TestName", game);
    }
}

package runner.external;

import data.external.DataManager;
import engine.external.Entity;

public class ModuleTester {
    public static void main(String[] args) {
        DataManager dm = new DataManager();
        Game game = (Game)dm.loadGameData("DatabaseGame");
        System.out.println("Successfully created: "+game);
    }
}

package runner.external;

import data.external.DataManager;
import engine.external.Entity;

public class ModuleTester {
    public static void main(String[] args) {
        System.out.println("Actually in this class");
        DataManager dm = new DataManager();
        Entity marioReloaded = (Entity)dm.loadGameData("RyanGame");
        marioReloaded.printMyComponents();
    }
}

package runner.external;

import engine.external.Entity;
import engine.external.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Level> myLevels;
    private Map<Entity, String> myUserCreatedTypes;
    private int myWidth;
    private int myHeight;

    public Game(){
        myLevels = new ArrayList<>();

        //Temporary defaults
        myHeight = 700;
        myWidth = 700;
    }

    public void addLevel(Level level){
        myLevels.add(level);
    }

    public List<Level> getLevels(){
        return myLevels;
    }

    public int getWidth(){
        return myWidth;
    }

    public int getHeight(){
        return myHeight;
    }

    public void addUserCreatedTypes(Map<Entity, String> userCreatedTypes){
        myUserCreatedTypes = userCreatedTypes;
    }

    public Map<Entity, String> getUserCreatedTypes() {
        return myUserCreatedTypes;
    }
}

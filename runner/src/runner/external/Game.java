package runner.external;

import engine.external.Entity;
import engine.external.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Level> myLevels;
    private Map<String, Entity> myUserCreatedTypes;
    private String myIconName;
    private String myGameName;
    private String myGameDescription;
    private int myWidth;
    private int myHeight;

    public Game(){
        myLevels = new ArrayList<Level>();

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

    public void addUserCreatedTypes(Map<String, Entity> userCreatedTypes){
        myUserCreatedTypes = userCreatedTypes;
    }
}

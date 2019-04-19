package EngineExampleCode;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;

/**
 * Example code file for GameEngine
 */
public class GameEngine {
    List<GameObject> myGameObjects;
    private HashSet<GameObject> myUpdatedGameObjects;
    private CollisionManager myCollisionManager;
    private MovementManager myMovementManager;
    private TouchManager myTouchManager;

    /**
     * Constructor of GameEngine
     * Initialize manager classes
     * @param gameObjectList list of GameObjects passed in by GameRunner or Authoring
     */
    public GameEngine(List<GameObject> gameObjectList){
        this.myGameObjects = gameObjectList;
        this.myCollisionManager = new CollisionManager(this);
        this.myMovementManager = new MovementManager(this);
        this.myTouchManager = new TouchManager(this);
    }

    /**
     * To be called by GameRunner for updating GameObject properties in every frame
     * @return HashSet of GameObjects whose properties have been changed since the last updateState() call
     * @param input user input received on the front end by GameRunner
     * TODO: integrated error handling
     */
    public HashSet<GameObject> updateState(String[] input) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        myUpdatedGameObjects = new HashSet<>();
        myMovementManager.update(myGameObjects);
        myCollisionManager.update(myGameObjects);
        myTouchManager.update(myGameObjects);
        return myUpdatedGameObjects;
    }

    public CollisionManager getMyCollisionManager(){
        return myCollisionManager;
    }

    /**
     * Allows managers to update the set of GameObjects to be returned to GameRunner
     * @param o A GameObject whose properties have been changed by some engine.external.events/engine.external.actions
     */
    public void addUpdatedGameObject(GameObject o){
        myUpdatedGameObjects.add(o);
    }
}

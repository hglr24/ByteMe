package EngineExampleCode;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Manager class that checks for touching engine.external.events between GameObjects and invokes CollisionComponents to perform engine.external.actions
 */
public class TouchManager {
    private GameEngine myEngine;
    private Long myManagerID;
    private final Integer myComponentIndex = 1;
    private List<GameObject> myGameObjects;
    private HashMap<GameObject, GameObject> myTouchingPairs;


    public TouchManager(GameEngine engine){
        this.myEngine = engine;
        this.myManagerID = ((long)1)<<myComponentIndex;
    }

    /**
     * Expected to be called by GameEngine for updating GameObject properties in each frame
     * TODO: error handling
     */
    public void update(List<GameObject> list) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        myGameObjects.clear();
        // only cares about collidable (i.e. touchable) GameObjects
        for(GameObject o:list){
            if((o.getMyEntityID()&myManagerID)==myManagerID){
                myGameObjects.add(o);
            }
        }
        // check for new touching engine.external.events that might have occurred
        for(int i = 0; i<myGameObjects.size();i++){
            GameObject o1 = myGameObjects.get(i);
            for(int j = i; j<myGameObjects.size(); j++){
                GameObject o2 = myGameObjects.get(j);
                if(o1.getMyNode().intersects(o2.getMyNode().getBoundsInLocal())){
                    myTouchingPairs.put(o1,o2);
                    myTouchingPairs.put(o2,o1);
                }
            }
        }
        // invoke EngineExampleCode.CollisionComponent to perform engine.external.actions on pairs of GameObjects that are still touching
        // or remove pairs of no-longer-touching GameObjects
        for(Map.Entry<GameObject,GameObject> pair:myTouchingPairs.entrySet()){
            if(pair.getKey().getMyNode().intersects(pair.getValue().getMyNode().getBoundsInLocal())){
                ((CollisionComponent)pair.getKey().getComponent(myComponentIndex)).touch(pair.getValue());
                myEngine.addUpdatedGameObject(pair.getKey());
            }else{
                myTouchingPairs.remove(pair);
            }
        }
    }
}

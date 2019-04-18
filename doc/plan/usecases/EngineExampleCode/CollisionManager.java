package EngineExampleCode;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager class (System in the ECS design) that checks for collisions between GameObjects
 * and invoke CollisionComponents of collided GameObjects to perform engine.external.actions
 * myComponentIndex indicates the bit index of collision component as stored in a GameObject's entityID field
 */
public class CollisionManager {

    private GameEngine myEngine;
    private final Integer myComponentIndex = 0;
    private Long myManagerID;
    private List<GameObject> myMovedGameObjectList;


    public CollisionManager(GameEngine engine){
        this.myEngine = engine;
        this.myMovedGameObjectList = new ArrayList<>();
        this.myManagerID = ((long)1)<<myComponentIndex;
    }

    /**
     * To be called by GameEngine to check for collisions that could occur between collidable GameObjects
     * TODO: error handling
     */
    public void update(List<GameObject> list) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for(int i = 0; i<myMovedGameObjectList.size();i++){
            GameObject o1 = myMovedGameObjectList.get(i);
            for(GameObject o2:list){
                if(o1.getMyNode().intersects(o2.getMyNode().getBoundsInLocal())){
                    ((CollisionComponent)o1.getComponent(myComponentIndex)).collide(o2);
                    ((CollisionComponent)o2.getComponent(myComponentIndex)).collide(o1);
                    // notify Engine that the GameObjects' properties might have changed after the collision event
                    myEngine.addUpdatedGameObject(o1);
                    myEngine.addUpdatedGameObject(o2);
                }
            }
        }
        myMovedGameObjectList.clear();
    }

    /**
     * Allow MovementManager to inform CollisionManager of GameObjects whose positions have changed since the last frame
     * and could possibly collide with the other objects
     * @param object a GameObject whose position has been updated by MovementManager
     */
    public void addMovedGameObjects(GameObject object){
        if((object.getMyEntityID()&myManagerID)==myManagerID){
            myMovedGameObjectList.add(object);
        }
    }

}

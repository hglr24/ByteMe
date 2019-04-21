package EngineExampleCode;

/**
 * Place where functions for executing certain engine.external.actions reside
 * Will be able to access a certain GameObject and its related Component associated to some engine.external.actions triggered by engine.external.events
 */
public class ActionManager {

    private GameObject myTargetGameObject;
    private Component myTargetComponent;

    public ActionManager(GameObject o, Component c){
        myTargetGameObject = o;
        myTargetComponent = c;
    }

    public void subtractLife(){
        myTargetGameObject.subtractLife();
    }

    public void flipXDirection(){
        ((MovementComponent)myTargetComponent).setMyXSpeed(((MovementComponent) myTargetComponent).getMyXSpeed()*-1);
    }

}

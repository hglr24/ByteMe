package engine.external.events;

import engine.external.actions.Action;
import engine.external.conditions.CollisionCondition;
import engine.external.component.BottomCollidedComponent;
import engine.external.conditions.Condition;

/**
 * @author Dima Fayyad
 */
public class BottomCollisionEvent extends Event {
    private String myCollisionWithEntity;

    public BottomCollisionEvent(String name, String collideWithEntity) {
        super(name);
        myCollisionWithEntity = collideWithEntity;
        makeBottomCollisionCondition();
    }

    /**
     * Adds a condition to the Event that verifies entity has a collidedComponent containing the correct entity collided with
     * Adds a condition to the Event that verifies the collision is below entity
     */
    private void makeBottomCollisionCondition() {
        CollisionCondition containsCollidedComponentCondition = new CollisionCondition(BottomCollidedComponent.class, myCollisionWithEntity);
        super.addConditions(containsCollidedComponentCondition);
    }

    public void removeActions(Action actionToRemove){
        super.removeActions(actionToRemove);
    }

    public void addActions(Action actionToAdd){
        super.addActions(actionToAdd);
    }

    public void addConditions(Condition addCondition){
        if (addCondition.getClass().equals(CollisionCondition.class)){
            return;
        }
        super.addConditions(addCondition);
    }

    public void removeConditions(Condition removeCondition){
        if (removeCondition.getClass().equals(CollisionCondition.class)){
            return;
        }
        super.removeConditions(removeCondition);
    }

}

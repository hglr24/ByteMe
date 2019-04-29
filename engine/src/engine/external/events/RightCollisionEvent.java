package engine.external.events;


import engine.external.actions.*;
import engine.external.component.NameComponent;
import engine.external.conditions.CollisionCondition;
import engine.external.component.RightCollidedComponent;
import engine.external.conditions.Condition;
import engine.external.conditions.StringEqualToCondition;

/**
 * @author Dima Fayyad
 */

public class RightCollisionEvent extends CollisionEvent {


    public RightCollisionEvent(String collideWithEntity, boolean grouped) {

        super(collideWithEntity, grouped);
        makeRightCollisionCondition(grouped);
    }
    public static CollisionEvent makeRightBounceEvent(String entityName1, String entityName2, Boolean grouped){
        CollisionEvent collisionEvent = new RightCollisionEvent(entityName2, grouped);
        collisionEvent.addConditions(new StringEqualToCondition(NameComponent.class, entityName1));
        collisionEvent.addActions(new XVelocityAction(NumericAction.ModifyType.ABSOLUTE, -2.0));
        collisionEvent.addActions(new XPositionAction(NumericAction.ModifyType.RELATIVE, -5.0));
        collisionEvent.addActions(new YPositionAction(NumericAction.ModifyType.RANDOM, 10.0));
        return collisionEvent;
    }

    /**
     * Adds a condition to the Event that verifies entity has a collidedComponent containing the correct entity collided with
     * Adds a condition to the Event that verifies the collision is on the right of entity
     */
    private void makeRightCollisionCondition(boolean grouped) {
        CollisionCondition containsCollidedComponentCondition = new CollisionCondition(RightCollidedComponent.class,
                getCollisionWithEntity(), grouped);
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

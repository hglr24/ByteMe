package engine.external.events;

import engine.external.actions.Action;
import engine.external.conditions.CollisionCondition;
import engine.external.component.RightCollidedComponent;
import engine.external.conditions.Condition;

/**
 * @author Dima Fayyad
 */

public class RightCollisionEvent extends CollisionEvent {


    public RightCollisionEvent(String collideWithEntity, boolean grouped) {
        setCollisionWithEntity(collideWithEntity);
        setGrouped(grouped);
        makeRightCollisionCondition(grouped);
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



}

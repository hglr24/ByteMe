package engine.external.events;

import engine.external.actions.Action;
import engine.external.conditions.CollisionCondition;
import engine.external.component.LeftCollidedComponent;
import engine.external.conditions.Condition;

/**
 * @author Dima Fayyad
 */
public class LeftCollisionEvent extends CollisionEvent {

    public LeftCollisionEvent(String collideWithEntity, boolean grouped) {
        setCollisionWithEntity(collideWithEntity);
        setGrouped(grouped);
        makeLeftCollisionCondition(grouped);
    }

    /**
     * Adds a condition to the Event that verifies entity has a collidedComponent containing the correct entity collided with
     * Adds a condition to the Event that verifies the collision is on the left of entity
     */
    private void makeLeftCollisionCondition(boolean grouped) {
        CollisionCondition containsCollidedComponentCondition = new CollisionCondition(LeftCollidedComponent.class,
                getCollisionWithEntity(), grouped);
        super.addConditions(containsCollidedComponentCondition);
    }



}

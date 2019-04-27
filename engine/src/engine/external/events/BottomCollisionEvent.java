package engine.external.events;

import engine.external.component.BottomCollidedComponent;
import engine.external.conditions.CollisionCondition;

/**
 * @author Dima Fayyad
 */
public class BottomCollisionEvent extends CollisionEvent {

    public BottomCollisionEvent(String collideWithEntity, boolean grouped) {
        setCollisionWithEntity(collideWithEntity);
        setGrouped(grouped);
        makeBottomCollisionCondition(grouped);
    }

    /**
     * Adds a condition to the Event that verifies entity has a collidedComponent containing the correct entity collided with
     * Adds a condition to the Event that verifies the collision is below entity
     */
    private void makeBottomCollisionCondition(boolean grouped) {
        CollisionCondition containsCollidedComponentCondition = new CollisionCondition(BottomCollidedComponent.class,
                getCollisionWithEntity(), grouped);
        super.addConditions(containsCollidedComponentCondition);
    }



}

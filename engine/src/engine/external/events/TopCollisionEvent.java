package engine.external.events;

import engine.external.actions.Action;
import engine.external.conditions.CollisionCondition;
import engine.external.component.TopCollidedComponent;
import engine.external.conditions.Condition;

/**
 * @author Dima Fayyad
 */
public class TopCollisionEvent extends CollisionEvent {


    public TopCollisionEvent(String collideWithEntity, boolean grouped) {
        setCollisionWithEntity(collideWithEntity);
        setGrouped(grouped);

        makeTopCollisionCondition(grouped);
    }

    /**
     * Adds a condition to the Event that verifies entity has a collidedComponent containing the correct entity collided with
     * Adds a condition to the Event that verifies the collision is on top of entity
     */
    private void makeTopCollisionCondition(boolean grouped) {
        CollisionCondition containsCollidedComponentCondition = new CollisionCondition(TopCollidedComponent.class,
                getCollisionWithEntity(), grouped);
        super.addConditions(containsCollidedComponentCondition);
    }



}

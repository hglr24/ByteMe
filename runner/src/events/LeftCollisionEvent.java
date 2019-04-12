package events;

import conditions.CollisionCondition;
import engine.external.component.LeftCollidedComponent;

public class LeftCollisionEvent extends Event {

    private String myCollisionWithEntity;

    public LeftCollisionEvent(String name, String collideWithEntity){
        super(name);
        myCollisionWithEntity = collideWithEntity;

        makeLeftCollisionCondition();
    }
    /**
     * Adds a condition to the Event that verifies entity has a collidedComponent containing the correct entity collided with
     * Adds a condition to the Event that verifies the collision is on the left of entity
     */
    private void makeLeftCollisionCondition(){
        CollisionCondition containsCollidedComponentCondition = new CollisionCondition(LeftCollidedComponent.class, myCollisionWithEntity);
        addConditions(containsCollidedComponentCondition);
    }

}

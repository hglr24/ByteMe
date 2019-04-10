package runner.Testing.events;

import runner.Testing.actions.*;
import runner.Testing.conditions.*;
import engine.external.component.RightCollidedComponent;

public class RightCollisionEvent extends Event {

    private String myCollisionWithEntity;

    public RightCollisionEvent(String name, String collideWithEntity){
        super(name);
        myCollisionWithEntity = collideWithEntity;

        makeRightCollisionCondition();
    }
    /**
     * Adds a condition to the Event that verifies entity has a collidedComponent containing the correct entity collided with
     * Adds a condition to the Event that verifies the collision is on the right of entity
     */
    private void makeRightCollisionCondition(){
        CollisionCondition containsCollidedComponentCondition = new CollisionCondition(RightCollidedComponent.class, myCollisionWithEntity);
        addConditions(containsCollidedComponentCondition);
    }
}

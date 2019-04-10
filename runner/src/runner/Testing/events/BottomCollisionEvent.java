package runner.Testing.events;

import runner.Testing.actions.*;
import runner.Testing.conditions.*;
import engine.external.component.BottomCollidedComponent;

public class BottomCollisionEvent extends Event{
    private String myCollisionWithEntity;

    public BottomCollisionEvent(String name, String collideWithEntity){
        super(name);
        myCollisionWithEntity = collideWithEntity;

        makeBottomCollisionCondition();
    }
    /**
     * Adds a condition to the Event that verifies entity has a collidedComponent containing the correct entity collided with
     * Adds a condition to the Event that verifies the collision is below entity
     */
    private void makeBottomCollisionCondition(){
        CollisionCondition containsCollidedComponentCondition = new CollisionCondition(BottomCollidedComponent.class, myCollisionWithEntity);
        addConditions(containsCollidedComponentCondition);
    }
}

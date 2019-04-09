package events;

import conditions.CollisionCondition;
import conditions.RightUpCollisionCondition;
import engine.external.component.CollidedComponent;
import engine.external.component.XPositionComponent;

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
        CollisionCondition containsCollidedComponentCondition = new RightUpCollisionCondition(CollidedComponent.class, myCollisionWithEntity, XPositionComponent.class);
        addConditions(containsCollidedComponentCondition);
    }
}

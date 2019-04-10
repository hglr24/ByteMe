package runner.Testing.events;


import engine.external.component.TopCollidedComponent;
import runner.Testing.conditions.CollisionCondition;

public class TopCollisionEvent extends Event {

    private String myCollisionWithEntity;

    public TopCollisionEvent(String name, String collideWithEntity){
        super(name);
        myCollisionWithEntity = collideWithEntity;

        makeTopCollisionCondition();
    }
    /**
     * Adds a condition to the Event that verifies entity has a collidedComponent containing the correct entity collided with
     * Adds a condition to the Event that verifies the collision is on top of entity
     */
    private void makeTopCollisionCondition(){
        CollisionCondition containsCollidedComponentCondition = new CollisionCondition(TopCollidedComponent.class, myCollisionWithEntity);
        addConditions(containsCollidedComponentCondition);
    }
}

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
        super(collideWithEntity, grouped);
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

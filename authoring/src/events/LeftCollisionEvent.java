package events;

import actions.VelocityAction;
import engine.external.Entity;
import engine.external.component.PositionComponent;
import javafx.geometry.Point3D;


import java.util.List;
import java.util.function.Consumer;

public class LeftCollisionEvent extends Event {

    private Entity myCollisionWithEntity;

    public LeftCollisionEvent(String name, Entity collideWithEntity){
        super(name);
        myCollisionWithEntity = collideWithEntity;

        //Example: adding actions and conditions:
        makeActions();
        makeConditions();
    }

    private void makeActions(){
        /*
        //Here is an Example: Add an action that stops the movement of the entity
        setVelocityAction velocityAction = new setVelocityAction();
        setActions(List.of(velocityAction.makeVelocityAction(0,0)));
         */
    }

    private void makeConditions(){
        //TODO: set a condition that checks entity's x position is less than entityCollidedWith's position
        /*
        //X position of entity collided with
        Point3D position =((PositionComponent) myCollisionWithEntity.getComponent(PositionComponent.class)).getValue();
        LessThanCondition condition = new LessThanCondition(PositionComponent.class, position.getX());
        */
    }


}

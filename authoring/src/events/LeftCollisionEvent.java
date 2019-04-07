package events;

public class LeftCollisionEvent extends Event {

    private String myCollisionWithEntity;

    public LeftCollisionEvent(String name, String collideWithEntity){
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
        Double XPosition =((XPositionComponent) myCollisionWithEntity.getComponent(XPositionComponent.class)).getValue();
        LessThanCondition condition = new LessThanCondition(XPositionComponent.class, XPosition);
        */
    }


}

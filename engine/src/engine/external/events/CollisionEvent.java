package engine.external.events;

public abstract class CollisionEvent extends Event{
    public static final double BounceVelocityValue = 2.0;
    public static final double XBouncePositionValue = 5.0;
    public static final double YBouncePositionValue = 10.0;
    private String myCollisionWithEntity;
    private boolean myGrouped;

    public CollisionEvent(String collisionEntity, boolean grouped) {
        myCollisionWithEntity = collisionEntity;
        myGrouped = grouped;
    }
    public String getCollisionWithEntity() {
        return myCollisionWithEntity;
    }
    public boolean getGroupStatus() {
        return myGrouped;
    }

    //TODO: Figure out why this doesn't work
//    public void removeActions(Action actionToRemove){
//        super.removeActions(actionToRemove);
//    }
//
//    public void addActions(Action actionToAdd){
//        super.addActions(actionToAdd);
//    }
//
//    public void addConditions(Condition addCondition){
//        if (addCondition.getClass().equals(CollisionCondition.class)){
//            return;
//        }
//        super.addConditions(addCondition);
//    }
//
//    public void removeConditions(Condition removeCondition){
//        if (removeCondition.getClass().equals(CollisionCondition.class)){
//            return;
//        }
//        super.removeConditions(removeCondition);
//    }


}

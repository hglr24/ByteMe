package conditions;

import engine.external.Entity;
import engine.external.component.Component;

public class LeftDownCollisionCondition extends CollisionCondition {

    public LeftDownCollisionCondition(Class<? extends Component> component, String entityType, Class<? extends Component> PositionComponent){
        super(component, entityType, PositionComponent);
    }

    @Override
     protected boolean matchOrientation(Entity entity, Entity entity2, Class<? extends Component> PositionComponent){
        return new LessThanCondition(PositionComponent, (Double) entity.getComponent(PositionComponent).getValue()).getPredicate().test(entity2);
    }
}

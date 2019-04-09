package conditions;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.NameComponent;

import java.util.Collection;

public abstract class CollisionCondition extends Condition<Collection<Entity>> {
    public CollisionCondition(Class<? extends Component> component, String entityType, Class<? extends Component> PositionComponent) {
        setPredicate(entity ->
                ((Collection<Entity>)entity.getComponent(component).getValue()).stream().anyMatch(entity2 ->
                        matchNames(entityType, entity2) && matchOrientation(entity, entity2, PositionComponent)
                ));
    }

    private boolean matchNames(String entityType, Entity entity) {
        return new StringEqualToCondition(NameComponent.class, entityType).getPredicate().test(entity);
    }

    protected abstract boolean matchOrientation(Entity entity, Entity entity2, Class<? extends Component> PositionComponent);

}

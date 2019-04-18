package engine.external.conditions;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.NameComponent;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Predicate;

public class CollisionCondition extends Condition {
    String myDirection;
    public CollisionCondition(Class<? extends Component> directionalCollidedComponent, String entityType) {
        setPredicate((Predicate<Entity> & Serializable) entity ->
                ((Collection<Entity>)entity.getComponent(directionalCollidedComponent).getValue()).stream().anyMatch((Predicate<Entity> & Serializable) entity2 ->
                        matchNames(entityType, entity2)
                ));

        myDirection = directionalCollidedComponent.getSimpleName();
    }

    private boolean matchNames(String entityType, Entity entity) {
        return new StringEqualToCondition(NameComponent.class, entityType).getPredicate().test(entity);
    }
    @Override
    public String toString(){
        return myDirection;
    }

}

package engine.external.conditions;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.GroupComponent;
import engine.external.component.NameComponent;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Lucas Liu
 * @author Dima Fayyad
 * @author Anna Darwish
 * <p>
 * Collision Condition for an Event. Gets a DirecitonalCollidedComponent and checks to see if it matches the correct entity, indicating a collision
 * with a particular type of game element.
 */
public class CollisionCondition extends Condition {
    private String myDirection;
    private String myEntity;
    private static final String COLLIDEDCLASS = "CollidedComponent";
    private static final String COLLISION = " Collision with ";

    /**
     * Create predicate that returns true if there is a match with the desired entityType
     * @param directionalCollidedComponent
     * @param entityType
     */
    public CollisionCondition(Class<? extends Component> directionalCollidedComponent, String entityType,
                              boolean grouped) {
        Class<? extends Component> clazz = grouped ? GroupComponent.class : NameComponent.class;
        setPredicate((Predicate<Entity> & Serializable) (entity ->
                ((Collection<Entity>) entity.getComponent(directionalCollidedComponent).getValue()).stream().anyMatch((Predicate<Entity> & Serializable) entity2 ->
                        matchNames(entityType, entity2, clazz)
                )));
        myEntity = entityType;
        myDirection = directionalCollidedComponent.getSimpleName();
    }

    private boolean matchNames(String entityType, Entity entity, Class<? extends Component> clazz) {
        return new StringEqualToCondition(clazz, entityType).getPredicate().test(entity);
    }

    @Override
    public String toString() {
        return myDirection.replace(COLLIDEDCLASS, "") + COLLISION + myEntity;
    }
}

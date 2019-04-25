package engine.external.component;

import engine.external.Entity;

import java.util.Collection;

/**
 * @author Hsingchih Tang
 * Record the Entities currently colliding with the owner Entity from the left
 */
public class LeftCollidedComponent extends Component<Collection<Entity>> {
    public LeftCollidedComponent(Collection<Entity> collideEntities) {
        super(collideEntities);
    }

}

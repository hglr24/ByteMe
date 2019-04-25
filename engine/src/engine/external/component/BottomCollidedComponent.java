package engine.external.component;

import engine.external.Entity;

import java.util.Collection;

/**
 * @author Hsingchih Tang
 * Record the Entities currently colliding with the owner Entity from the bottom
 */
public class BottomCollidedComponent extends Component<Collection<Entity>> {

    public BottomCollidedComponent(Collection<Entity> collideEntities) {
        super(collideEntities);
    }

}

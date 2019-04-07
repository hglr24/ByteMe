package engine.external.component;

import engine.external.Entity;

import java.util.Collection;

/**
 * Record the Entities currently colliding with the owner Entity
 */
public class CollidedComponent extends Component<Collection<Entity>> {

    public CollidedComponent(Collection<Entity> collideEntities){
        super(collideEntities);
    }

}

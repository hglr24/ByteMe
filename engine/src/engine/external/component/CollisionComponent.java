package engine.external.component;

/**
 * Indicates whether the owner Entity is collidable
 */
public class CollisionComponent extends Component<Boolean> {

    public CollisionComponent(Boolean collidable){
        super(collidable);
    }
}

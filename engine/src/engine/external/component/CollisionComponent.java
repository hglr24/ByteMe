package engine.external.component;

/**
 * @author Hsingchih Tang
 * @author Feroze Mohideen
 * Indicates whether the owner Entity is collidable
 */
public class CollisionComponent extends Component<Boolean> {
    private final static boolean DEFAULT = false;

    public CollisionComponent(Boolean collidable) {
        super(collidable);
    }
    public CollisionComponent() {
        super(DEFAULT);
    }
}

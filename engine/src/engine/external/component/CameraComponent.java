package engine.external.component;

/**
 * @author Hsingchih Tang
 */
public class CameraComponent extends Component<Boolean> {
    private final static boolean DEFAULT = false;
    public CameraComponent(Boolean cameraOn) {
        super(cameraOn);
    }
    public CameraComponent(){
        super(DEFAULT);
    }
}

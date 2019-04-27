package engine.external.component;

public class ProgressionComponent extends Component<Boolean> {
    private final static boolean DEFAULT = false;
    public ProgressionComponent(){
        super(DEFAULT);
    }
    public ProgressionComponent(Boolean progress) {
        super(progress);
    }
}

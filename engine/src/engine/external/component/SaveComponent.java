package engine.external.component;

public class SaveComponent extends Component<Boolean> {

    private final static boolean DEFAULT = true;

    public SaveComponent(Boolean save) {
        super(save);
    }
    public SaveComponent() {
        super(DEFAULT);
    }
}

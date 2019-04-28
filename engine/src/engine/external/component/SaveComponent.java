package engine.external.component;

public class SaveComponent extends Component<Boolean> {

    private final static boolean DEFAULT = true;

    public SaveComponent() {
        super(DEFAULT);
    }
    public SaveComponent(Boolean save) {
        super(save);
    }

}

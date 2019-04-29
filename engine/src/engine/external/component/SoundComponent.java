package engine.external.component;

public class SoundComponent extends Component<String> {

    private final static String DEFAULT = "coin";

    public SoundComponent() {
        super(DEFAULT);
    }

    public SoundComponent(String pathname) {
        super(pathname);
    }
}

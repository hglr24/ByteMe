package engine.external.component;

public class NameComponent extends Component<String> {
    private final static String DEFAULT = "NoName";
    public NameComponent(String name) {
        super(name);
    }
    public NameComponent(){
        super(DEFAULT);
    }

}

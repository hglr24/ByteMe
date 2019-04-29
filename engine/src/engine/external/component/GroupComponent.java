package engine.external.component;

public class GroupComponent extends Component<String> {
    private final static String DEFAULT = "NoGroup";

    public GroupComponent(String name) {
        super(name);
    }
    public GroupComponent(){
        super(DEFAULT);
    }
}

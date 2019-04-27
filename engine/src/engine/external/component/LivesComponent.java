package engine.external.component;

public class LivesComponent extends Component<Double> {
    private final static double DEFAULT = 2.0;

    public LivesComponent(){
        super(DEFAULT);
    }
    public LivesComponent(Double value) {
        super(value);
    }
}

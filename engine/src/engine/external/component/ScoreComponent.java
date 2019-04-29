package engine.external.component;

public class ScoreComponent extends Component<Double> {
    private final static double DEFAULT = 1.0;

    public ScoreComponent() {
        super(DEFAULT);
    }

    public ScoreComponent(Double value) {
        super(value);
    }
}

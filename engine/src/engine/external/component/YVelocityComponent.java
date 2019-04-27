package engine.external.component;

public class YVelocityComponent extends Component<Double> {

    private final static double DEFAULT = 10.0;

    public YVelocityComponent(Double value) {
        super(value);
    }

    public YVelocityComponent() {
        super(DEFAULT);
    }
}

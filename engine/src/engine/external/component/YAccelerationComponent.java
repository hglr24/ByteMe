package engine.external.component;

public class YAccelerationComponent extends Component<Double> {

    private final static double DEFAULT = 0.0;

    public YAccelerationComponent(Double value) {
        super(value);
    }

    public YAccelerationComponent() {
        super(DEFAULT);
    }
}

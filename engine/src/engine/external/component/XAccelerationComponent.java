package engine.external.component;

public class XAccelerationComponent extends Component<Double> {

    private final static double DEFAULT = 0.0;

    public XAccelerationComponent(Double value) {
        super(value);
    }

    public XAccelerationComponent() {
        super(DEFAULT);
    }
}

package engine.external.component;

public class ZPositionComponent extends Component<Double> {

    private final static double DEFAULT = 0.0;

    public ZPositionComponent(Double value) {
        super(value);
    }
    public ZPositionComponent() {
        super(DEFAULT);
    }
}

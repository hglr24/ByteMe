package engine.external.component;

public class XVelocityComponent extends Component<Double> {

    private final static double DEFAULT = 1.0;

    public XVelocityComponent() {
        super(DEFAULT);
    }

    public XVelocityComponent(Double value) {
        super(value);
    }

}

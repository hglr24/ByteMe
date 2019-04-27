package engine.external.component;

public class WidthComponent extends Component<Double> {

    private final static double DEFAULT = 10.0;

    public WidthComponent() {
        super(DEFAULT);
    }

    public WidthComponent(Double value) {
        super(value);
    }


}

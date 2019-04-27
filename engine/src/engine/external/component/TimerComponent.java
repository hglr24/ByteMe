package engine.external.component;

public class TimerComponent extends Component<Double> {
    private final static double DEFAULT = 10.0;

    public TimerComponent() {
        super(DEFAULT);
    }

    public TimerComponent(Double value) {
        super(value);
    }
}

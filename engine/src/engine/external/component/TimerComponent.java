package engine.external.component;

public class TimerComponent extends Component<Double> {
    private final static double DEFAULT = 10.0;

    public TimerComponent(Double value) {
        super(value);
    }
    public TimerComponent() {
        super(DEFAULT);
    }
}

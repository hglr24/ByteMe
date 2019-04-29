package engine.external.component;

public class HealthComponent extends Component<Double> {
    private final static double DEFAULT = 2.0;

    public HealthComponent(Double value) {
        super(value);
    }
    public HealthComponent(){
        super(DEFAULT);
    }
}

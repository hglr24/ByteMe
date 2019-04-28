package engine.external.component;

public class HealthComponent extends Component<Double> {
    private final static double DEFAULT = 2.0;

    public HealthComponent(){
        super(DEFAULT);
    }
    public HealthComponent(Double value) {
        super(value);
    }
}

package engine.external.component;

public class DirectionComponent extends Component<Double> {
    private final static double DEFAULT = 2.0;

    public DirectionComponent(){
        super(DEFAULT);
    }
    public DirectionComponent(Double value) {
        super(value);
    }
}

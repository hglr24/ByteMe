package engine.external.component;

/**
 * @author Lucas Liu
 * <p>
 * Component with one-step history
 */
public class XPositionComponent extends Component<Double> {
    private final static double DEFAULT = 0.0;
    private Double oldValue;

    public XPositionComponent() {
        super(DEFAULT);
    }

    public XPositionComponent(Double value) {
        super(value);
        oldValue = value;
    }

    @Override
    public void setValue(Double value) {
        oldValue = Double.valueOf(myValue);
        myValue = value;
    }

    public Double getOldValue() {
        return oldValue;
    }

    public void revertValue(Double value) {
        myValue = Double.valueOf(value);
        oldValue = Double.valueOf(value);
    }

}

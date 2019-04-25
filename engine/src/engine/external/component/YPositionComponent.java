package engine.external.component;

/**
 * @author Lucas Liu
 *
 * Component with one-step history
 */
public class YPositionComponent extends Component<Double> {

    private Double oldValue;

    public YPositionComponent(Double value) {
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
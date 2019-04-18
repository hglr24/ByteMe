package engine.external.component;

/**
 *
 */
public class XPositionComponent extends Component<Double> {

    private Double oldValue;

    public XPositionComponent (Double value){
        super(value);
        oldValue = value;
    }

    @Override
    public void setValue(Double value){
        oldValue = Double.valueOf(myValue);
        myValue = value;
    }

    public Double getOldValue(){
        return oldValue;
    }

    public void revertValue(){
        myValue = Double.valueOf(oldValue);
    }
}

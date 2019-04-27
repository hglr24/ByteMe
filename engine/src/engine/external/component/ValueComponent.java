package engine.external.component;

/**
 * @author Lucas Liu
 * @param <T>
 *     Useful component for storing a value which is custom to a particular game. One could store a double, String, or even another Entity
 */
public class ValueComponent extends Component<Double> {

    private final static double DEFAULT = 1.0;
    public ValueComponent(){
        super(DEFAULT);
    }
    public ValueComponent(Double t) {
        super(t);
    }
}

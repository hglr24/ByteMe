package actions;

import engine.external.component.Component;

/**
 * A NumericAction can alter the Double value of a component in three different ways:
 * - setting the existing value to a new value
 * - scaling the existing value by a new value
 * - adding a new value to the existing value
 *
 * @param <Double>
 * @author Feroze
 * @author Lucas
 */
public abstract class NumericAction<Double> extends Action<Double> {
    /**
     * This method is used when subclass objects are constructed in order to specify what kind of
     * operation is being done to the existing value
     *
     * @param type           either an absolute set, a scaling, or an addition
     * @param newValue       value used in the operation
     * @param componentClass component which this action affects, whose getValue() method should return a DOUBLE
     */
    public void setAction(ModifyType type, Double newValue, Class<? extends Component<Double>> componentClass) {
        switch (type) {
            case ABSOLUTE:
                super.setAbsoluteAction(newValue, componentClass);
                break;
            case SCALE:
                setScaledAction((Number) newValue, componentClass);
                break;
            case RELATIVE:
                setRelativeAction((Number) newValue, componentClass);
                break;
        }
    }


    /**
     * This method scales the current value of a component by a scaleFactor
     *
     * @param scaleFactor
     * @param componentClass
     */
    @SuppressWarnings("unchecked")
    protected void setScaledAction(Number scaleFactor, Class<? extends Component<Double>> componentClass) {
        super.setAction((entity) -> {
            double oldValue = ((Number) entity.getComponent(componentClass).getValue()).doubleValue();
            Component component = entity.getComponent(componentClass);
            component.setValue(oldValue * scaleFactor.doubleValue());
        });
    }

    /**
     * This method adds a displacementFactor to the current value of a component
     *
     * @param displacementFactor
     * @param componentClass
     */
    protected void setRelativeAction(Number displacementFactor, Class<? extends Component<Double>> componentClass) {
        setAction((entity) -> {
            Component component = entity.getComponent(componentClass);
            component.setValue((double) component.getValue() + displacementFactor.doubleValue());
        });
    }

    /**
     * This enum is used in subclass construction to specify what kind of operation should be done to the component
     * value
     */
    enum ModifyType {
        ABSOLUTE,
        RELATIVE,
        SCALE,
    }
}

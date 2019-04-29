package engine.external.actions;

import engine.external.Entity;
import engine.external.component.Component;

import java.io.Serializable;
import java.util.Random;
import java.util.function.Consumer;

/**
 * A NumericAction can alter the Double value of a component in three different ways:
 * - setting the existing value to a new value
 * - scaling the existing value by a new value
 * - adding a new value to the existing value
 *
 * @author Feroze
 * @author Lucas
 */
public abstract class NumericAction extends Action<Double> {
    private ModifyType myModifier;
    private Double myArgument;
    private static final String COMPONENT = "Component";

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
                setScaledAction(newValue, componentClass);
                break;
            case RELATIVE:
                setRelativeAction(newValue, componentClass);
                break;
            case RANDOM:
                setRandomAction(newValue, componentClass);
                break;
        }
        myModifier = type;
        myArgument = newValue;
        myComponentClass = componentClass;
        super.setMyComponentClass(componentClass);
    }


    /**
     * This method scales the current value of a component by a scaleFactor
     *
     * @param scaleFactor
     * @param componentClass
     */
    @SuppressWarnings("unchecked")
    protected void setScaledAction(Number scaleFactor, Class<? extends Component<Double>> componentClass) {
        super.setAction((Consumer<Entity> & Serializable) entity -> {
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
        setAction((Consumer<Entity> & Serializable) entity -> {
            Component component = entity.getComponent(componentClass);
            component.setValue((double) component.getValue() + displacementFactor.doubleValue());
        });
    }

    /**
     * This method adds a random amount to the current value of a component
     *
     * @param maxRandom
     * @param componentClass
     */
    protected void setRandomAction(Number maxRandom, Class<? extends Component<Double>> componentClass) {
        setAction((Consumer<Entity> & Serializable) entity -> {
            Component component = entity.getComponent(componentClass);
            Random r = new Random();
            component.setValue((double) component.getValue() + (r.nextDouble() * 2 * maxRandom.doubleValue())-maxRandom.doubleValue());
        });
    }

    /**
     * This enum is used in subclass construction to specify what kind of operation should be done to the component
     * value
     */
    public enum ModifyType {
        ABSOLUTE("Set", "to"),
        RELATIVE("Change", "by"),
        SCALE("Scale", "by"),
        RANDOM("Random", "by");

        private final String displayName;
        private final String modifierName;

        ModifyType(String displayName, String modifierName) {

            this.displayName = displayName;
            this.modifierName = modifierName;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public String getModifierName() {
            return this.modifierName;
        }
    }

    public String toString() {
        return myModifier.getDisplayName() +
                " " + myComponentClass.getSimpleName().replaceAll(COMPONENT, "") + " " + myModifier.modifierName + " " + myArgument.toString();


    }
}

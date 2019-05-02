package engine.external.actions;

import engine.external.Entity;
import engine.external.component.AssociatedEntityComponent;
import engine.external.component.Component;

import java.io.Serializable;
import java.util.function.Consumer;

public class AssociatedEntityAction extends NumericAction {
    /**
     * This method scales the current value of a component of an associated entity by a scaleFactor
     *
     * @param scaleFactor
     * @param componentClass
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setScaledAction(Number scaleFactor, Class<? extends Component<Double>> componentClass) {
        //myComponentClass = componentClass;

        super.setAction((Consumer<Entity> & Serializable) entity -> {
            double oldValue =
                    ((Number) ((Entity) entity.getComponent(AssociatedEntityComponent.class)
                            .getValue())
                            .getComponent(componentClass)
                            .getValue())
                            .doubleValue();
            Component component = ((Entity) entity.getComponent(AssociatedEntityComponent.class)
                    .getValue()).getComponent(componentClass);
            component.setValue(oldValue * scaleFactor.doubleValue());
        });
    }

    /**
     * This method adds a displacementFactor to the current value of a component of an associated entity
     *
     * @param displacementFactor
     * @param componentClass
     */
    public void setRelativeAction(Number displacementFactor, Class<? extends Component<Double>> componentClass) {
        //myComponentClass = componentClass;

        setAction((Consumer<Entity> & Serializable) entity -> {
            Component component = ((Entity) entity.getComponent(AssociatedEntityComponent.class)
                    .getValue())
                    .getComponent(componentClass);
            component.setValue((double) component.getValue() + displacementFactor.doubleValue());
        });
    }
}


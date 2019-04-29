package engine.external.actions;

import engine.external.Entity;
import engine.external.component.AssociatedEntityComponent;
import engine.external.component.Component;

import java.io.Serializable;
import java.util.function.Consumer;

public class AssociatedEntityStringAction extends StringAction {
    /**
     * This method used by subclass objects when they are constructed specifies the lambda, which
     * performs a set operation on this new value.
     *
     * @param newValue       new String to change the component value to
     * @param componentClass the class of the specified component
     */
    @Override
    public void setAction(String newValue, Class<? extends Component<String>> componentClass) {
        super.setAction((Consumer<Entity> & Serializable) entity -> {
            Component component = ((Entity) entity.getComponent(AssociatedEntityComponent.class)
                    .getValue())
                    .getComponent(componentClass);
            component.setValue(newValue);
        });
        myComponentClass = componentClass;
    }
}

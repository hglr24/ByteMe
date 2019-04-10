package runner.Testing.actions;

import engine.external.component.Component;

/**
 * A BooleanAction is a variant of an Action, and can only change the boolean value of a
 * component to the new boolean value.
 *
 * @author Feroze
 */
public abstract class BooleanAction extends Action<Boolean>  {
    public void setAction(Boolean newValue, Class<? extends Component<Boolean>> componentClass) {
        super.setAbsoluteAction(newValue, componentClass);
    }
}

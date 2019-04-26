package engine.external.actions;

import engine.external.component.VisibilityComponent;
@Deprecated
public class VisibilityAction extends BooleanAction {
    public VisibilityAction(Boolean isVisible) {
        setAction(isVisible, VisibilityComponent.class);
    }
}

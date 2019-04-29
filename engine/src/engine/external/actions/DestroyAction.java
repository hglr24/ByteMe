package engine.external.actions;

import engine.external.component.DestroyComponent;

public class DestroyAction extends BooleanAction {
    public DestroyAction(Boolean destroy) {
        setAction(destroy, DestroyComponent.class);
    }
}

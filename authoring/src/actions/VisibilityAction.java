package actions;

import engine.external.Entity;
import engine.external.component.VisibilityComponent;

import java.util.function.Consumer;

public class VisibilityAction extends Action{
    public VisibilityAction(){ super();}

    public Consumer<Entity> makeVisibilityAction(boolean isVisible) {
        return makeValueAction(isVisible, VisibilityComponent.class);
    }
}

package actions;

import engine.external.Entity;
import engine.external.component.DestroyComponent;

import java.util.function.Consumer;

public class DestroyAction extends Action {
    public DestroyAction(){ super();}

    public Consumer<Entity> makeDestroyAction(Boolean destroy) {
        return makeValueAction(destroy, DestroyComponent.class);
    }
}

package actions;

import engine.external.Entity;
import engine.external.component.GravityComponent;

import java.util.function.Consumer;

public class GravityAction extends Action{
    public GravityAction(){ super();}

    public Consumer<Entity> makeGravityAction(double gravity) {
        return makeValueAction(gravity, GravityComponent.class);
    }
}

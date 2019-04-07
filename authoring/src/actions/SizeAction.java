package actions;

import engine.external.Entity;
import engine.external.component.SizeComponent;

import java.util.function.Consumer;

public class SizeAction extends Action{
    public SizeAction(){ super();}

    public Consumer<Entity> makeSizeAction(double gravity) {
        return makeValueAction(gravity, SizeComponent.class);
    }
}

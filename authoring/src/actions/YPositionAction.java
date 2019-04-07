package actions;

import engine.external.Entity;
import engine.external.component.YPositionComponent;

import java.util.function.Consumer;

public class YPositionAction extends Action {

    public YPositionAction(){ super();}

    public Consumer<Entity> makePositionAction(double yPosition) {
        return makeValueAction(yPosition, YPositionComponent.class);
    }
}

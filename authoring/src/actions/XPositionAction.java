package actions;

import engine.external.Entity;
import engine.external.component.XPositionComponent;


import java.util.function.Consumer;

public class XPositionAction extends Action {

    public XPositionAction(){ super();}

    public Consumer<Entity> makePositionAction(double xPosition) {
        return makeValueAction(xPosition, XPositionComponent.class);
    }
}

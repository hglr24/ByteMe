package actions;

import engine.external.Entity;
import engine.external.component.XVelocityComponent;

import java.util.function.Consumer;

public class XVelocityAction extends Action {

    public XVelocityAction(){ super();}

    public Consumer<Entity> makeVelocityAction(double xVelocity) {
        return (Consumer<Entity>) (entity) -> {
            XVelocityComponent component = (XVelocityComponent) entity.getComponent(XVelocityComponent.class);
            component.setValue(xVelocity);
        };
    }
}

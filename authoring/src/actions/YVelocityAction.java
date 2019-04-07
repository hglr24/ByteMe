package actions;

import engine.external.Entity;
import engine.external.component.YVelocityComponent;

import java.util.function.Consumer;

public class YVelocityAction extends Action {

    public YVelocityAction(){ super();}

    public Consumer<Entity> makeVelocityAction(double yVelocity) {
        return (Consumer<Entity>) (entity) -> {
            YVelocityComponent component = (YVelocityComponent) entity.getComponent(YVelocityComponent.class);
            component.setValue(yVelocity);
        };
    }
}

package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.*;
import engine.internal.Engine;

import java.util.Collection;

public class MovementSystem extends System {

    public MovementSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    protected void run() {
        for (Entity e: getEntities()) {
            XPositionComponent XPositionComponent = (XPositionComponent) e.getComponent(X_POSITION_COMPONENT_CLASS);
            YPositionComponent YPositionComponent = (YPositionComponent) e.getComponent(Y_POSITION_COMPONENT_CLASS);
            ZPositionComponent ZPositionComponent = (ZPositionComponent) e.getComponent(Z_POSITION_COMPONENT_CLASS);
            XVelocityComponent XVelocityComponent = (XVelocityComponent) e.getComponent(X_VELOCITY_COMPONENT_CLASS);
            YVelocityComponent YVelocityComponent = (YVelocityComponent) e.getComponent(Y_VELOCITY_COMPONENT_CLASS);

            double x = XPositionComponent.getValue() + XVelocityComponent.getValue();
            double y = YPositionComponent.getValue() + YVelocityComponent.getValue();
            double z = ZPositionComponent.getValue();

            XPositionComponent.setValue(x);
            YPositionComponent.setValue(y);
            ZPositionComponent.setValue(z);
        }
    }
}

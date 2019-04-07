package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.PositionComponent;
import engine.external.component.VelocityComponent;
import engine.internal.Engine;
import javafx.geometry.Point3D;

import java.util.Collection;

public class MovementSystem extends System {

    public MovementSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    protected void run() {
        for (Entity e: getEntities()) {
            //XPositionComponent XPositionComponent = (XPositionComponent) e.getComponent(X_POSITION_COMPONENT_CLASS);
            //YPositionComponent YPositionComponent = (YPositionComponent) e.getComponent(Y_POSITION_COMPONENT_CLASS);
            //ZPositionComponent ZPositionComponent = (ZPositionComponent) e.getComponent(Z_POSITION_COMPONENT_CLASS);
            VelocityComponent velocityComponent = (VelocityComponent) e.getComponent(VELOCITY_COMPONENT_CLASS);

            double x = XPositionComponent.getValue() + velocityComponent.getValue().getX();
            double y = YPositionComponent.getValue() + velocityComponent.getValue().getY();
            double z = ZPositionComponent.getValue();

            XpositionComponent.setValue(x);
            YpositionComponent.setValue(y);
            ZpositionComponent.setValue(z);
        }
    }
}

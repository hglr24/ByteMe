package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.*;
import engine.external.Engine;

import java.util.Collection;

/**
 * @author Hsingchih Tang
 * Updates the PositionComponents for every movable Entity on each game loop based on its position and velocity values
 */
public class MovementSystem extends VoogaSystem {

    public MovementSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    protected void run() {
        for (Entity e: getEntities()) {
            double x = calcPosition(getDoubleComponentValue(X_POSITION_COMPONENT_CLASS,e),getDoubleComponentValue(X_VELOCITY_COMPONENT_CLASS,e),getDoubleComponentValue(X_ACCELERATION_COMPONENT_CLASS,e));
            double y = calcPosition(getDoubleComponentValue(Y_POSITION_COMPONENT_CLASS,e),getDoubleComponentValue(Y_VELOCITY_COMPONENT_CLASS,e),getDoubleComponentValue(Y_ACCELERATION_COMPONENT_CLASS,e));
            ((XPositionComponent)e.getComponent(X_POSITION_COMPONENT_CLASS)).setValue(x);
            ((YPositionComponent)e.getComponent(Y_POSITION_COMPONENT_CLASS)).setValue(y);
        }
    }

    private double calcPosition(double position, double velocity, double acceleration){
        return position+velocity+(1/2)*acceleration;
    }
}

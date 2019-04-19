package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.*;
import engine.external.Engine;

import java.util.Arrays;
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
            double x = calcPosition(getDoubleComponentValue(X_POSITION_COMPONENT_CLASS,e),
                    e.hasComponents(X_VELOCITY_COMPONENT_CLASS)?
                            getDoubleComponentValue(X_VELOCITY_COMPONENT_CLASS,e):0.0,
                    e.hasComponents(X_ACCELERATION_COMPONENT_CLASS)?
                            getDoubleComponentValue(X_ACCELERATION_COMPONENT_CLASS,e):0.0);
            double y = calcPosition(getDoubleComponentValue(Y_POSITION_COMPONENT_CLASS,e),
                    e.hasComponents(Y_VELOCITY_COMPONENT_CLASS)?
                            getDoubleComponentValue(Y_VELOCITY_COMPONENT_CLASS,e):0.0,
                    e.hasComponents(Y_ACCELERATION_COMPONENT_CLASS)?
                            getDoubleComponentValue(Y_ACCELERATION_COMPONENT_CLASS,e):0.0);

            double vX = calcVelocity(e.hasComponents(X_VELOCITY_COMPONENT_CLASS)?
                            getDoubleComponentValue(X_VELOCITY_COMPONENT_CLASS,e):0.0,
                    e.hasComponents(X_ACCELERATION_COMPONENT_CLASS)?
                            getDoubleComponentValue(X_ACCELERATION_COMPONENT_CLASS,e):0.0);
            double vY = calcVelocity(e.hasComponents(Y_VELOCITY_COMPONENT_CLASS)?
                            getDoubleComponentValue(Y_VELOCITY_COMPONENT_CLASS,e):0.0,
                    e.hasComponents(Y_ACCELERATION_COMPONENT_CLASS)?
                            getDoubleComponentValue(Y_ACCELERATION_COMPONENT_CLASS,e):0.0);
            ((XPositionComponent)e.getComponent(X_POSITION_COMPONENT_CLASS)).setValue(x);
            ((YPositionComponent)e.getComponent(Y_POSITION_COMPONENT_CLASS)).setValue(y);
            ((XVelocityComponent)e.getComponent(X_VELOCITY_COMPONENT_CLASS)).setValue(vX);
            ((YVelocityComponent)e.getComponent(Y_VELOCITY_COMPONENT_CLASS)).setValue(vY);
//            if(e.getComponent(SpriteComponent.class).getValue().equals("flappy_bird.png")){
//                System.out.println(e.getComponent(SpriteComponent.class).getValue()+" x pos = "+x+ " y pos = "+y);
//                System.out.println(e.getComponent(SpriteComponent.class).getValue()+" x vel = "+vX+ " y vel = "+vY);
//            }
        }
    }

    private double calcPosition(double position, double velocity, double acceleration){
        return position+velocity+acceleration/2.0;
    }

    private double calcVelocity(double velocity, double acceleration){
        return velocity+acceleration;
    }


}

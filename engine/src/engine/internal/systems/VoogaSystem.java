package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.*;
import engine.external.Engine;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Collection;

public abstract class VoogaSystem {
    protected final Class<? extends Component> X_POSITION_COMPONENT_CLASS = XPositionComponent.class;
    protected final Class<? extends Component> Y_POSITION_COMPONENT_CLASS = YPositionComponent.class;
    protected final Class<? extends Component> Z_POSITION_COMPONENT_CLASS = ZPositionComponent.class;
    protected final Class<? extends Component> X_VELOCITY_COMPONENT_CLASS = XVelocityComponent.class;
    protected final Class<? extends Component> Y_VELOCITY_COMPONENT_CLASS = YVelocityComponent.class;
    protected final Class<? extends Component> COLLIDED_COMPONENT_CLASS = CollidedComponent.class;
    protected final Class<? extends Component> COLLISION_COMPONENT_CLASS = CollisionComponent.class;
    protected final Class<? extends Component> DESTROY_COMPONENT_CLASS = DestroyComponent.class;
    protected final Class<? extends Component> DIRECTION_COMPONENT_CLASS = DirectionComponent.class;
    protected final Class<? extends Component> GRAVITY_COMPONENT_CLASS = GravityComponent.class;
    protected final Class<? extends Component> HEALTH_COMPONENT_CLASS = HealthComponent.class;
    protected final Class<? extends Component> IMAGEVIEW_COMPONENT_CLASS = ImageViewComponent.class;
    protected final Class<? extends Component> NAME_COMPONENT_CLASS = NameComponent.class;
    protected final Class<? extends Component> WIDTH_COMPONENT_CLASS = WidthComponent.class;
    protected final Class<? extends Component> HEIGHT_COMPONENT_CLASS = HeightComponent.class;
    protected final Class<? extends Component> SOUND_COMPONENT_CLASS = SoundComponent.class;
    protected final Class<? extends Component> SPRITE_COMPONENT_CLASS = SpriteComponent.class;
    protected final Class<? extends Component> TIMER_COMPONENT_CLASS = TimerComponent.class;
    protected final Class<? extends Component> VALUE_COMPONENT_CLASS = ValueComponent.class;
    protected final Class<? extends Component> VISIBILITY_COMPONENT_CLASS = VisibilityComponent.class;


    private Collection<Class<? extends Component>> myRequiredComponents;
    private Collection<Entity> myEntities;
    private Collection<KeyCode> myInputs;
    protected Engine myEngine;


    public VoogaSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        myInputs = new ArrayList<>();
        myRequiredComponents = requiredComponents;
        myEngine = engine;
    }

    public void update(Collection<Entity> entities, Collection<KeyCode> inputs) {
        myEntities = new ArrayList<>();
        myInputs = inputs;
        for (Entity e: entities) {
            if (filter(e)) {
                myEntities.add(e);
            }
        }
        run();
        myInputs.clear();
    }



    protected boolean filter(Entity e) {
        return e.hasComponents(myRequiredComponents);
    }

    protected abstract void run();

    protected Collection<Entity> getEntities() {
        return myEntities;
    }

    protected Collection<KeyCode> getKeyCodes(){
        return myInputs;
    }

}

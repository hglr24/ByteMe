package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.*;
import engine.internal.Engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.jar.Attributes;

public abstract class System {
    protected final Class<? extends Component> POSITION_COMPONENT_CLASS = PositionComponent.class;
    protected final Class<? extends Component> VELOCITY_COMPONENT_CLASS = VelocityComponent.class;
    protected final Class<? extends Component> COLLIDED_COMPONENT_CLASS = CollidedComponent.class;
    protected final Class<? extends Component> COLLISION_COMPONENT_CLASS = CollisionComponent.class;
    protected final Class<? extends Component> DESTROY_COMPONENT_CLASS = DestroyComponent.class;
    protected final Class<? extends Component> DIRECTION_COMPONENT_CLASS = DirectionComponent.class;
    protected final Class<? extends Component> GRAVITY_COMPONENT_CLASS = GravityComponent.class;
    protected final Class<? extends Component> HEALTH_COMPONENT_CLASS = HealthComponent.class;
    protected final Class<? extends Component> IMAGEVIEW_COMPONENT_CLASS = ImageViewComponent.class;
    protected final Class<? extends Component> NAME_COMPONENT_CLASS = NameComponent.class;
    protected final Class<? extends Component> SIZE_COMPONENT_CLASS = SizeComponent.class;
    protected final Class<? extends Component> SOUND_COMPONENT_CLASS = SoundComponent.class;
    protected final Class<? extends Component> SPRITE_COMPONENT_CLASS = SpriteComponent.class;
    protected final Class<? extends Component> TIMER_COMPONENT_CLASS = TimerComponent.class;
    protected final Class<? extends Component> VALUE_COMPONENT_CLASS = ValueComponent.class;
    protected final Class<? extends Component> VISIBILITY_COMPONENT_CLASS = VisibilityComponent.class;




    private Collection<Class<? extends Component>> myRequiredComponents;
    private Collection<Entity> myEntities;
    protected Engine myEngine;

    public System(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        myRequiredComponents = requiredComponents;
        myEngine = engine;
    }

    public void update(Collection<Entity> entities) {
        myEntities = new ArrayList<>();
        for (Entity e: entities) {
            if (filter(e)) {
                myEntities.add(e);
            }
        }
        run();
    }



    protected boolean filter(Entity e) {
        return e.hasComponents(myRequiredComponents);
    }

    protected abstract void run();

    protected Collection<Entity> getEntities() {
        return myEntities;
    }

}

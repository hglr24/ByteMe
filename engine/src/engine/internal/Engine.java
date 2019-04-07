package engine.internal;

import engine.external.Entity;
import engine.external.IEvent;
import engine.external.Level;
import engine.external.component.*;
import engine.internal.systems.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Engine {
    //TODO: Read from external source file instead of hardcoding final variables for system initialization
    private final Collection<Class<? extends Component>> MOVEMENT_SYSTEM_COMPONENTS = Arrays.asList(XVelocityComponent.class,YVelocityComponent.class, XPositionComponent.class, YPositionComponent.class, ZPositionComponent.class);
    private final Collection<Class<? extends Component>> CONTACT_SYSTEM_COMPONENTS = Arrays.asList(CollisionComponent.class, ImageViewComponent.class);
    private final Collection<Class<? extends Component>> COLLISION_SYSTEM_COMPONENTS = Arrays.asList(CollisionComponent.class, ImageViewComponent.class);
    private final Collection<Class<? extends Component>> HEALTH_SYSTEM_COMPONENTS = Arrays.asList(HealthComponent.class);
    private final Collection<Class<? extends Component>> CLEANUP_SYSTEM_COMPONENTS = Arrays.asList(DestroyComponent.class);
    private final Collection<Class<? extends Component>> IMAGEVIEW_SYSTEM_COMPONENTS = Arrays.asList(SpriteComponent.class, XPositionComponent.class, YPositionComponent.class, ZPositionComponent.class);
    private final Collection<Class<? extends Component>> EVENTHANDLER_SYSTEM_COMPONENTS = new ArrayList<>();

    protected Collection<Entity> myEntities;
    protected Collection<IEvent> myEvents;
    private MovementSystem myMovementSystem;
    private ImageViewSystem myImageViewSystem;
    private CollisionSystem myCollisionSystem;
    private HealthSystem myHealthSystem;
    private EventHandlerSystem myEventHandlerSystem;
    private CleanupSystem myCleanupSystem;


    public Engine(Level level){
        myEntities = level.getEntities();
        myEvents = level.getEvents();
        initSystems();
    }

    // TODO: decide how inputs will be passed in from Runner
    public Collection<Entity> updateState(){
        myMovementSystem.update(myEntities);
        myImageViewSystem.update(myEntities);
        myCollisionSystem.update(myEntities);
        myHealthSystem.update(myEntities);
        myCleanupSystem.update(myEntities);
        myEventHandlerSystem.update(myEntities);

        return this.getEntities();
    }

    public Collection<Entity> getEntities(){
        return Collections.unmodifiableCollection(myEntities);
    }

    private void initSystems(){
        myMovementSystem = new MovementSystem(MOVEMENT_SYSTEM_COMPONENTS, this);
        myImageViewSystem = new ImageViewSystem(IMAGEVIEW_SYSTEM_COMPONENTS, this);
        myCollisionSystem = new CollisionSystem(COLLISION_SYSTEM_COMPONENTS, this);
        myHealthSystem = new HealthSystem(HEALTH_SYSTEM_COMPONENTS, this);
        myCleanupSystem = new CleanupSystem(CLEANUP_SYSTEM_COMPONENTS, this);
        myEventHandlerSystem = new EventHandlerSystem(EVENTHANDLER_SYSTEM_COMPONENTS, this, myEvents);
    }
}

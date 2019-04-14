package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.*;
import engine.external.Engine;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Hsingchih Tang
 * Abstract super class of all internal Systems of Engine
 * Every concrete System stores a different set of Component classes required from an Entity
 * such that its relevant Component values could be managed by the System
 */
public abstract class VoogaSystem {
    protected final Class<? extends Component> X_POSITION_COMPONENT_CLASS = XPositionComponent.class;
    protected final Class<? extends Component> Y_POSITION_COMPONENT_CLASS = YPositionComponent.class;
    protected final Class<? extends Component> Z_POSITION_COMPONENT_CLASS = ZPositionComponent.class;
    protected final Class<? extends Component> X_VELOCITY_COMPONENT_CLASS = XVelocityComponent.class;
    protected final Class<? extends Component> Y_VELOCITY_COMPONENT_CLASS = YVelocityComponent.class;
    protected final Class<? extends Component> X_ACCELERATION_COMPONENT_CLASS = XAccelerationComponent.class;
    protected final Class<? extends Component> Y_ACCELERATION_COMPONENT_CLASS = XAccelerationComponent.class;
    protected final Class<? extends Component> BOTTOM_COLLIDED_COMPONENT_CLASS = BottomCollidedComponent.class;
    protected final Class<? extends Component> TOP_COLLIDED_COMPONENT_CLASS = TopCollidedComponent.class;
    protected final Class<? extends Component> RIGHT_COLLIDED_COMPONENT_CLASS = RightCollidedComponent.class;
    protected final Class<? extends Component> LEFT_COLLIDED_COMPONENT_CLASS = LeftCollidedComponent.class;
    protected final Class<? extends Component> ANY_COLLIDED_COMPONENT_CLASS = AnyCollidedComponent.class;
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


    /**
     * Every System (regardless of its functionality) stores the same main Engine in its instance field,
     * and requires different sets of Component classes from an Entity so that it could be processed by the System
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public VoogaSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        myInputs = new ArrayList<>();
        myRequiredComponents = requiredComponents;
        myEngine = engine;
    }

    /**
     * Generic call expected to be made from Engine on every game loop
     * Receives the most up-to-date collection of Entities currently existing in the Game and user input KeyCodes
     * received on the frontend, filters the Entities to only interact with those equipped with required Components
     * to prepare for next-step processing. Call run() to execute own special operations on the Entities and user
     * input KeyCodes. Clear up the input KeyCodes after this System is done within current game loop.
     * @param entities Collection of Entities passed in from Engine
     * @param inputs Collection of keyCodes received by Runner and then passed in by Engine
     */
    public void update(Collection<Entity> entities, Collection<KeyCode> inputs) {
        myEntities = new ArrayList<>();
        myInputs = inputs;

        for (Entity e: entities) {
            if (filter(e)) {
                myEntities.add(e);
            }
        }
        run();
        // myInputs.clear();
    }

    /**
     * Verify whether an Entity is equipped with all the required Components in order to be handled by a System
     * @param e Entity to verify
     * @return boolean value indicating whether the Entity is a match to the System
     */
    protected boolean filter(Entity e) {
        return e.hasComponents(myRequiredComponents);
    }

    /**
     * Abstract method in which every concrete System interacts with a matching Entity in its own way
     */
    protected abstract void run();

    /**
     * Allow concrete Systems to retrieve the private Collection of Entities stored in the super System
     * @return Collection of Entities held in the System
     */
    protected Collection<Entity> getEntities() {
        return myEntities;
    }

    /**
     * Allow concrete Systems to retrieve the private Collection of KeyCodes (user inputs) stored in the super System
     * @return Collection of Keycodes held in the System
     */
    protected Collection<KeyCode> getKeyCodes(){
        return myInputs;
    }


    protected Double getDoubleComponentValue(Class<? extends Component> componentClazz,Entity entity){
        return (Double)entity.getComponent(componentClazz).getValue();
    }

    protected Boolean getBooleanComponentValue(Class<? extends Component> componentClazz,Entity entity){
        return (Boolean)entity.getComponent(componentClazz).getValue();
    }

    protected String getStringComponentValue(Class<? extends Component> componentClazz,Entity entity){
        return (String)entity.getComponent(componentClazz).getValue();
    }

    protected ImageView getImageViewComponentValue(Class<? extends Component> componentClazz, Entity entity){
        return (ImageView) entity.getComponent(componentClazz).getValue();
    }
}

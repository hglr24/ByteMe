package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.Engine;
import javafx.scene.image.ImageView;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * @author Hsingchih Tang
 * Responsible for detecting collisions between the ImageView of two collidable Entities via JavaFX Node.intersects(),
 * and register the two parties of every collision in each other's BottomCollidedComponent, such that certain actions (defined
 * in the Event tied to an Entity) could be triggered by the execute() call fied from EventHandlerSystem
 */
public class CollisionSystem extends VoogaSystem {

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public CollisionSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    /**
     * loop through all collidable Entities and check for collision situations
     * and record the other party in each collided Entity's Collided Component
     */
    protected void run() {
        this.getEntities().forEach(e1->this.getEntities().forEach(e2->{
            if(seemColliding(e1,e2)&& e1!=e2){
//                System.out.println("colliding");
                registerCollidedEntity(horizontalCollide(e2,e1),e1,e2);
                registerCollidedEntity(verticalCollide(e2,e1),e1,e2);
                registerCollidedEntity(ANY_COLLIDED_COMPONENT_CLASS,e1,e2);

            }
        }));
    }

    private void registerCollidedEntity(Class<? extends Component> componentClazz, Entity e1, Entity e2){
        if(!e1.hasComponents(componentClazz)){
            try {
                e1.addComponent((Component<?>) Class.forName(this.getClass().getModule(),
                        Engine.COMPONENTS_PACKAGE_PATH + componentClazz.getSimpleName()).getConstructor(new Class[]{Collection.class}).newInstance(new HashSet<>()));
            } catch (InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
                System.out.println("Invalid reflection instantiation call in CollisionSystem: "+componentClazz.getSimpleName());
            }
        }
        ((Collection<Entity>)e1.getComponent(componentClazz).getValue()).add(e2);
    }


    private Class<? extends Component> horizontalCollide(Entity e1, Entity e2){
        if(getDoubleComponentValue(X_POSITION_COMPONENT_CLASS,e1)<getDoubleComponentValue(Y_POSITION_COMPONENT_CLASS,e2)){
            return LEFT_COLLIDED_COMPONENT_CLASS;
        }
        return RIGHT_COLLIDED_COMPONENT_CLASS;
    }


    private Class<? extends Component> verticalCollide(Entity e1, Entity e2){
        if(getDoubleComponentValue(Y_POSITION_COMPONENT_CLASS,e1)<getDoubleComponentValue(Y_POSITION_COMPONENT_CLASS,e2)){
            return TOP_COLLIDED_COMPONENT_CLASS;
        }
        return BOTTOM_COLLIDED_COMPONENT_CLASS;
    }


    private boolean seemColliding(Entity e1, Entity e2){
        return (getImageViewComponentValue(IMAGEVIEW_COMPONENT_CLASS,e1)).intersects((getImageViewComponentValue(IMAGEVIEW_COMPONENT_CLASS,e2)).getBoundsInLocal());
    }

}

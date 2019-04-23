package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.LeftCollidedComponent;
import engine.external.component.XPositionComponent;
import engine.external.component.YPositionComponent;
import engine.external.Engine;
import javafx.geometry.Point2D;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Hsingchih Tang
 * Responsible for detecting collisions between the ImageView of two collidable Entities via JavaFX Node.intersects(),
 * and register the two parties of every collision in each other's BottomCollidedComponent, such that certain engine.external.actions (defined
 * in the Event tied to an Entity) could be triggered by the execute() call fired from EventHandlerSystem
 */
public class CollisionSystem extends VoogaSystem {

    private Map<Entity, Point2D> collidedEntities;

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public CollisionSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    public void adjustCollidedEntities(){
        for (Map.Entry<Entity,Point2D> entry:collidedEntities.entrySet()){
            Entity entity = entry.getKey();
            if(entity.hasComponents(LEFT_COLLIDED_COMPONENT_CLASS)||entity.hasComponents(RIGHT_COLLIDED_COMPONENT_CLASS)){
                ((XPositionComponent)entity.getComponent(X_POSITION_COMPONENT_CLASS)).revertValue(entry.getValue().getX());
            }
            if(entity.hasComponents(TOP_COLLIDED_COMPONENT_CLASS)||entity.hasComponents(BOTTOM_COLLIDED_COMPONENT_CLASS)){
                ((YPositionComponent)entity.getComponent(Y_POSITION_COMPONENT_CLASS)).revertValue(entry.getValue().getY());
            }
            entity.removeComponent(Arrays.asList(LEFT_COLLIDED_COMPONENT_CLASS,RIGHT_COLLIDED_COMPONENT_CLASS,TOP_COLLIDED_COMPONENT_CLASS,BOTTOM_COLLIDED_COMPONENT_CLASS,ANY_COLLIDED_COMPONENT_CLASS));
        }
    }


    @Override
    /**
     * loop through all collidable Entities and check for collision situations
     * and record the other party in each collided Entity's Collided Component
     */
    protected void run() {
        collidedEntities = new HashMap<>();
        this.getEntities().forEach(e1->this.getEntities().forEach(e2->{
            if(seemColliding(e1,e2)&& e1!=e2){
//                System.out.println(e1.getComponent(SpriteComponent.class).getValue()+" collided by "+e2.getComponent(SpriteComponent.class).getValue());
                Class horizontal = horizontalCollide(e1,e2);
                Class vertical = verticalCollide(e1,e2);
                registerCollidedEntity(horizontal,e1,e2);
                registerCollidedEntity(vertical,e1,e2);
                if(horizontal!=null||vertical!=null){
                    registerCollidedEntity(ANY_COLLIDED_COMPONENT_CLASS,e1,e2);
                    Double oldX = ((XPositionComponent)e1.getComponent(X_POSITION_COMPONENT_CLASS)).getOldValue();
                    Double oldY = ((YPositionComponent)e1.getComponent(Y_POSITION_COMPONENT_CLASS)).getOldValue();
                    collidedEntities.put(e1,new Point2D(oldX,oldY));
                }
            }
        }));
    }


    /**
     * Register Entity e2 in a CollidedComponent class of Entity e1
     * @param componentClazz defines the side on which e1 is collided by e2
     * @param e1 Entity being collided
     * @param e2 Entity colliding the other
     */
    private void registerCollidedEntity(Class<? extends Component> componentClazz, Entity e1, Entity e2){
        if(componentClazz==null){
            return;
        }
        if(!e1.hasComponents(componentClazz)){
            try {
                e1.addComponent((Component<?>) Class.forName(componentClazz.getName()).getConstructor(new Class[]{Collection.class}).newInstance(new HashSet<>()));
            } catch (InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException e) {
                System.out.println("Invalid reflection instantiation call in CollisionSystem: "+componentClazz.getSimpleName());
                return;
            }
        }
        ((Collection<Entity>)e1.getComponent(componentClazz).getValue()).add(e2);
    }


    /**
     * Classify Entity e2's horizontal collision behavior towards Entity e1
     * @param e1 Entity being collided
     * @param e2 Entity colliding the other
     * @return LeftCollidedComponent.class if e2 is colliding on the left of e1
     *         RightCollidedComponent.class if e2 is colliding on the right of e1
     *         null if not e1, e2 are not performing collision behaviors on horizontal axis
     */
    private Class<? extends Component> horizontalCollide(Entity e1, Entity e2){
        if(isLeftTo(e2,e1)&&(isMovingRight(e2)||isMovingLeft(e1))){
//            System.out.println(e2.getComponent(SpriteComponent.class).getValue()+" left collides on "+e1.getComponent(SpriteComponent.class).getValue());
            return LEFT_COLLIDED_COMPONENT_CLASS;
        }else if(isRightTo(e2,e1)&&(isMovingLeft(e2)||isMovingRight(e1))){
//            System.out.println(e2.getComponent(SpriteComponent.class).getValue()+" right collides on "+e1.getComponent(SpriteComponent.class).getValue());
            return RIGHT_COLLIDED_COMPONENT_CLASS;
        }
//        System.out.println("no horizontal collision on "+e1.getComponent(SpriteComponent.class).getValue());
        return null;
    }


    /**
     * Classify Entity e2's vertical collision behavior towards Entity e1
     * @param e1 Entity being collided
     * @param e2 Entity colliding the other
     * @return TopCollidedComponent.class if e2 is colliding on the top of e1
     *         BottomCollidedComponent.class if e2 is colliding on the bottom of e1
     *         null if not e1, e2 are not performing collision behaviors on vertical axis
     */
    private Class<? extends Component> verticalCollide(Entity e1, Entity e2){
        if(isAbove(e2,e1)&&(isMovingDown(e2)||isMovingUp(e1))){
//            System.out.println(e2.getComponent(SpriteComponent.class).getValue()+" top collides on "+e1.getComponent(SpriteComponent.class).getValue());
            return TOP_COLLIDED_COMPONENT_CLASS;
        }else if(isBelow(e2,e1)&&(isMovingUp(e2)||isMovingDown(e1))){
//            System.out.println(e2.getComponent(SpriteComponent.class).getValue()+" bottom collides on "+e1.getComponent(SpriteComponent.class).getValue());
            return BOTTOM_COLLIDED_COMPONENT_CLASS;
        }
//        System.out.println("no vertical collision on "+e1.getComponent(SpriteComponent.class).getValue());
        return null;
    }



    private boolean seemColliding(Entity e1, Entity e2){
        return (getImageViewComponentValue(IMAGEVIEW_COMPONENT_CLASS,e1)).intersects((getImageViewComponentValue(IMAGEVIEW_COMPONENT_CLASS,e2)).getBoundsInLocal());
    }

    private boolean isLeftTo(Entity e1, Entity e2){
        return (((XPositionComponent)e1.getComponent(X_POSITION_COMPONENT_CLASS)).getOldValue()+(Double) e1.getComponent(WIDTH_COMPONENT_CLASS).getValue())<((XPositionComponent)e2.getComponent(X_POSITION_COMPONENT_CLASS)).getOldValue();
    }

    private boolean isRightTo(Entity e1, Entity e2){
        return ((XPositionComponent)e1.getComponent(X_POSITION_COMPONENT_CLASS)).getOldValue()>(((XPositionComponent)e2.getComponent(X_POSITION_COMPONENT_CLASS)).getOldValue()+(Double) e2.getComponent(WIDTH_COMPONENT_CLASS).getValue());
    }

    private boolean isAbove(Entity e1, Entity e2){
        return (((YPositionComponent)e1.getComponent(Y_POSITION_COMPONENT_CLASS)).getOldValue()+(Double) e1.getComponent(HEIGHT_COMPONENT_CLASS).getValue())<((YPositionComponent)e2.getComponent(Y_POSITION_COMPONENT_CLASS)).getOldValue();
    }

    private boolean isBelow(Entity e1, Entity e2){
        return ((YPositionComponent)e1.getComponent(Y_POSITION_COMPONENT_CLASS)).getOldValue()>(((YPositionComponent)e2.getComponent(Y_POSITION_COMPONENT_CLASS)).getOldValue()+(Double)e2.getComponent(HEIGHT_COMPONENT_CLASS).getValue());
    }

    private boolean isMovingLeft(Entity entity){
        return ((XPositionComponent)entity.getComponent(X_POSITION_COMPONENT_CLASS)).getOldValue()>getDoubleComponentValue(X_POSITION_COMPONENT_CLASS,entity);
    }

    private boolean isMovingRight(Entity entity){
        return ((XPositionComponent)entity.getComponent(X_POSITION_COMPONENT_CLASS)).getOldValue()<getDoubleComponentValue(X_POSITION_COMPONENT_CLASS,entity);
    }

    private boolean isMovingUp(Entity entity){
        return ((YPositionComponent)entity.getComponent(Y_POSITION_COMPONENT_CLASS)).getOldValue()>getDoubleComponentValue(Y_POSITION_COMPONENT_CLASS,entity);
    }

    private boolean isMovingDown(Entity entity){
        return ((YPositionComponent)entity.getComponent(Y_POSITION_COMPONENT_CLASS)).getOldValue()<getDoubleComponentValue(Y_POSITION_COMPONENT_CLASS,entity);
    }


}

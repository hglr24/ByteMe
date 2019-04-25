package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.XPositionComponent;
import engine.external.component.YPositionComponent;
import engine.external.Engine;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import voogasalad.util.reflection.Reflection;
import voogasalad.util.reflection.ReflectionException;

import java.util.Collection;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;

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

    /**
     * Revert the positions of collided Entities in this game loop in the directions where collisions happened
     * and then remove all CollidedComponents from the Entities
     */
    public void adjustCollidedEntities(){
        for (Map.Entry<Entity,Point2D> entry:collidedEntities.entrySet()){
            Entity entity = entry.getKey();
            if(horizontallyCollided(entity)){
                ((XPositionComponent)entity.getComponent(X_POSITION_COMPONENT_CLASS)).revertValue(entry.getValue().getX());
            }
            if(verticallyCollided(entity)){
                ((YPositionComponent)entity.getComponent(Y_POSITION_COMPONENT_CLASS)).revertValue(entry.getValue().getY());
            }
            removeCollidedComponents(entity);
        }
    }


    @Override
    /**
     * Loop through all collidable Entities to check for collisions and record CollidedComponents for each pair;
     * Also record the past positions of collided Entities for adjusting their positions at the end of game loop.
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
                    Double oldX = (Double)getComponentValue(X_POSITION_COMPONENT_CLASS,e1,GET_OLD_VALUE);
                    Double oldY = (Double)getComponentValue(Y_POSITION_COMPONENT_CLASS,e1,GET_OLD_VALUE);
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
    private void registerCollidedEntity(Class<? extends Component> componentClazz, Entity e1, Entity e2) throws ReflectionException{
        if(componentClazz==null){
            return;
        }
        if(!e1.hasComponents(componentClazz)){
            try {
                e1.addComponent((Component<?>) Reflection.createInstance(componentClazz.getName(),new HashSet<>()));
            } catch (ReflectionException e) {
                throw new ReflectionException(e,"Cannot create "+componentClazz.getName()+" for Entity "+getComponentValue(NAME_COMPONENT_CLASS,e1));
            }
        }
        ((Collection<Entity>)getComponentValue(componentClazz,e1)).add(e2);
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
        if(wasLeftTo(e2,e1)&&(isMovingRight(e2)||isMovingLeft(e1))){
//            System.out.println(e2.getComponent(SpriteComponent.class).getValue()+" left collides on "+e1.getComponent(SpriteComponent.class).getValue());
            return LEFT_COLLIDED_COMPONENT_CLASS;
        }else if(wasRightTo(e2,e1)&&(isMovingLeft(e2)||isMovingRight(e1))){
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
        if(wasAbove(e2,e1)&&(isMovingDown(e2)||isMovingUp(e1))){
//            System.out.println(e2.getComponent(SpriteComponent.class).getValue()+" top collides on "+e1.getComponent(SpriteComponent.class).getValue());
            return TOP_COLLIDED_COMPONENT_CLASS;
        }else if(wasBelow(e2,e1)&&(isMovingUp(e2)||isMovingDown(e1))){
//            System.out.println(e2.getComponent(SpriteComponent.class).getValue()+" bottom collides on "+e1.getComponent(SpriteComponent.class).getValue());
            return BOTTOM_COLLIDED_COMPONENT_CLASS;
        }
//        System.out.println("no vertical collision on "+e1.getComponent(SpriteComponent.class).getValue());
        return null;
    }



    private boolean seemColliding(Entity e1, Entity e2){
        return ((ImageView) getComponentValue(IMAGEVIEW_COMPONENT_CLASS,e1)).intersects(((ImageView) getComponentValue(IMAGEVIEW_COMPONENT_CLASS,e2)).getBoundsInLocal());
    }

    private boolean wasLeftTo(Entity e1, Entity e2){
        return ((Double)getComponentValue(X_POSITION_COMPONENT_CLASS,e1,GET_OLD_VALUE)+(Double)getComponentValue(WIDTH_COMPONENT_CLASS,e1))<(Double)getComponentValue(X_POSITION_COMPONENT_CLASS,e2,GET_OLD_VALUE);
    }

    private boolean wasRightTo(Entity e1, Entity e2){
        return (Double)getComponentValue(X_POSITION_COMPONENT_CLASS,e1,GET_OLD_VALUE)>((Double)getComponentValue(X_POSITION_COMPONENT_CLASS,e2,GET_OLD_VALUE)+(Double)getComponentValue(WIDTH_COMPONENT_CLASS,e2));
    }

    private boolean wasAbove(Entity e1, Entity e2){
        return ((Double)getComponentValue(Y_POSITION_COMPONENT_CLASS,e1,GET_OLD_VALUE)+(Double)getComponentValue(HEIGHT_COMPONENT_CLASS,e1))<(Double)getComponentValue(Y_POSITION_COMPONENT_CLASS,e2,GET_OLD_VALUE);
    }

    private boolean wasBelow(Entity e1, Entity e2){
        return (Double)getComponentValue(Y_POSITION_COMPONENT_CLASS,e1,GET_OLD_VALUE)>((Double)getComponentValue(Y_POSITION_COMPONENT_CLASS,e2,GET_OLD_VALUE)+(Double)getComponentValue(HEIGHT_COMPONENT_CLASS,e2));
    }

    private boolean isMovingLeft(Entity entity){
        return (Double)getComponentValue(X_POSITION_COMPONENT_CLASS,entity,GET_OLD_VALUE)>(Double) getComponentValue(X_POSITION_COMPONENT_CLASS,entity);
    }

    private boolean isMovingRight(Entity entity){
        return (Double)getComponentValue(X_POSITION_COMPONENT_CLASS,entity,GET_OLD_VALUE)<(Double) getComponentValue(X_POSITION_COMPONENT_CLASS,entity);
    }

    private boolean isMovingUp(Entity entity){
        return (Double)getComponentValue(Y_POSITION_COMPONENT_CLASS,entity,GET_OLD_VALUE)>(Double)getComponentValue(Y_POSITION_COMPONENT_CLASS,entity);
    }

    private boolean isMovingDown(Entity entity){
        return (Double)getComponentValue(Y_POSITION_COMPONENT_CLASS,entity,GET_OLD_VALUE)<(Double) getComponentValue(Y_POSITION_COMPONENT_CLASS,entity);
    }

    private boolean verticallyCollided(Entity entity){
        return entity.hasComponents(TOP_COLLIDED_COMPONENT_CLASS)||entity.hasComponents(BOTTOM_COLLIDED_COMPONENT_CLASS);
    }

    private boolean horizontallyCollided(Entity entity){
        return entity.hasComponents(LEFT_COLLIDED_COMPONENT_CLASS)||entity.hasComponents(RIGHT_COLLIDED_COMPONENT_CLASS);
    }

    private void removeCollidedComponents(Entity entity){
        entity.removeComponent(Arrays.asList(LEFT_COLLIDED_COMPONENT_CLASS,RIGHT_COLLIDED_COMPONENT_CLASS,TOP_COLLIDED_COMPONENT_CLASS,BOTTOM_COLLIDED_COMPONENT_CLASS,ANY_COLLIDED_COMPONENT_CLASS));
    }

}

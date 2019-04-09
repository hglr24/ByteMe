package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.CollidedComponent;
import engine.external.component.Component;
import engine.external.Engine;
import javafx.scene.image.ImageView;

import java.util.Collection;
import java.util.HashSet;

public class CollisionSystem extends VoogaSystem {

    public CollisionSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    /**
     * loop through all collidable Entities and check for collision situations
     * and record the other party in each collided Entity's Collided Component
     */
    protected void run() {
        for(Entity e1:this.getEntities()){
            for(Entity e2:this.getEntities()){
                if(e1!=e2 && seemColliding(e1,e2)){
                    registerCollidedEntity(e1,e2);
                    registerCollidedEntity(e2,e1);
                }
            }
        }
    }

    private void registerCollidedEntity(Entity e1, Entity e2){
        if(!e1.hasComponents(COLLIDED_COMPONENT_CLASS)){
            e1.addComponent(new CollidedComponent(new HashSet<>()));
        }
        ((Collection<Entity>)e1.getComponent(COLLIDED_COMPONENT_CLASS).getValue()).add(e2);
    }

    private boolean seemColliding(Entity e1, Entity e2){
        return ((ImageView)e1.getComponent(IMAGEVIEW_COMPONENT_CLASS).getValue()).intersects(((ImageView)e2.getComponent(IMAGEVIEW_COMPONENT_CLASS).getValue()).getBoundsInLocal());
    }
}

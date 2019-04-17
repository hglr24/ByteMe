package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.DirectionComponent;
import engine.external.component.ImageViewComponent;
import engine.external.Engine;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.util.Collection;

/**
 * @author Hsingchih Tang
 * Updates the ImageView visualization and positions for Entities that have a SpriteComponent and PositionComponents
 */
public class ImageViewSystem extends VoogaSystem {

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     *
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine             the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public ImageViewSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    /**
     * Assign or update the ImageViewComponents for each eligible Entity
     */
    protected void run() {
        for (Entity entity : this.getEntities()) {
            entity.addComponent(new ImageViewComponent(generateImageView(entity)));
        }
    }

    private ImageView generateImageView(Entity entity){
        ImageView imageView;
        if (!entity.hasComponents(IMAGEVIEW_COMPONENT_CLASS)) {
            imageView = new ImageView(new Image(getStringComponentValue(SPRITE_COMPONENT_CLASS, entity)));
        } else {
            imageView = (ImageView) entity.getComponent(IMAGEVIEW_COMPONENT_CLASS).getValue();
        }
        return setImgViewHeight(setImgViewWidth(setImgViewY(setImgViewX(imageView,entity),entity),entity),entity);

    }

    private ImageView setImgViewX(ImageView m, Entity e){
        m.setX(getDoubleComponentValue(X_POSITION_COMPONENT_CLASS, e));
        return m;
    }

    private ImageView setImgViewY(ImageView m, Entity e){
        m.setY(getDoubleComponentValue(Y_POSITION_COMPONENT_CLASS, e));
        return m;
    }

    private ImageView setImgViewWidth(ImageView m, Entity e){
        m.setFitWidth(getDoubleComponentValue(WIDTH_COMPONENT_CLASS, e));
        return m;
    }

    private ImageView setImgViewHeight(ImageView m, Entity e){
        m.setFitHeight(getDoubleComponentValue(HEIGHT_COMPONENT_CLASS, e));
        return m;
    }


}

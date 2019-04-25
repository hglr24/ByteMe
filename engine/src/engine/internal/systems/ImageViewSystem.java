package engine.internal.systems;

import data.external.DataManager;
import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.ImageViewComponent;
import engine.external.Engine;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import voogasalad.util.reflection.Reflection;
import voogasalad.util.reflection.ReflectionException;


import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Hsingchih Tang
 * Updates the ImageView visualization and positions for Entities that have a SpriteComponent and PositionComponents
 */
public class ImageViewSystem extends VoogaSystem {

    DataManager myDataManager;
    HashMap<Entity,String> myEntityPastSprite;

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public ImageViewSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine){
        super(requiredComponents, engine);
        myDataManager = new DataManager();
        myEntityPastSprite = new HashMap<>();
    }

    @Override
    /**
     * Assign or update the ImageViewComponents for each eligible Entity.
     * Sizes and positions of each Entity's Imageview are updated on every game loop.
     * The system keeps track of the Sprites of Entities and creates a new ImageView if an Entity's Sprite has changed.
     */
    protected void run() throws ReflectionException{
        for(Entity entity:this.getEntities()){
            try{
                entity.addComponent(new ImageViewComponent(generateImageView(entity)));
            }catch (ReflectionException e){
                throw e;
            }
        }
    }

    private ImageView generateImageView(Entity entity) throws ReflectionException{
        ImageView imageView;
        String imageName = (String)getComponentValue(SPRITE_COMPONENT_CLASS, entity);
        if (!myEntityPastSprite.containsKey(entity)||!myEntityPastSprite.get(entity).equals(imageName)) {
            InputStream imageStream = myDataManager.loadImage(imageName);
            if(imageStream==null){
                throw new ReflectionException("Image file "+imageName+" not found in database.");
            }
            imageView = new ImageView(new Image(imageStream));
            myEntityPastSprite.put(entity,imageName);
        } else {
            imageView = (ImageView) entity.getComponent(IMAGEVIEW_COMPONENT_CLASS).getValue();
        }
        return setImgViewHeight(setImgViewWidth(setImgViewY(setImgViewX(imageView,entity),entity),entity),entity);

    }

    private ImageView setImgViewX(ImageView m, Entity e){
        m.setX((Double) getComponentValue(X_POSITION_COMPONENT_CLASS, e));
        return m;
    }

    private ImageView setImgViewY(ImageView m, Entity e){
        m.setY((Double) getComponentValue(Y_POSITION_COMPONENT_CLASS, e));
        return m;
    }

    private ImageView setImgViewWidth(ImageView m, Entity e){
        m.setFitWidth((Double) getComponentValue(WIDTH_COMPONENT_CLASS, e));
        return m;
    }

    private ImageView setImgViewHeight(ImageView m, Entity e){
        m.setFitHeight((Double) getComponentValue(HEIGHT_COMPONENT_CLASS, e));
        return m;
    }

}

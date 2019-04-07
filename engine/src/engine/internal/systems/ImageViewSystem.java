package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.ImageViewComponent;
import engine.internal.Engine;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Collection;

public class ImageViewSystem extends System {
    public ImageViewSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine){
        super(requiredComponents, engine);
    }

    @Override
    /**
     * Assign or update the ImageViewComponents for each eligible Entity
     */
    protected void run() {
        for(Entity entity:this.getEntities()){
            entity.addComponent(new ImageViewComponent(generateImageView(entity)));
        }
    }

    private ImageView generateImageView(Entity entity){
        Image image = new Image((String)entity.getComponent(SPRITE_COMPONENT_CLASS).getValue());
        Point3D position = (Point3D) entity.getComponent(POSITION_COMPONENT_CLASS).getValue();
        ImageView imageView = new ImageView(image);
        imageView.setX(position.getX());
        imageView.setY(position.getY());
        return imageView;
    }

}

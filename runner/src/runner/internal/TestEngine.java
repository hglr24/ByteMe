package runner.internal;

import engine.external.Entity;
import engine.external.Level;
import engine.external.component.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import java.util.Collection;

/**
 * Original "engine" used to test Runner functionality
 * @author Louis Jensen
 */
public class TestEngine {
    private Collection<Entity> myEntities;
    private int x = 1;

    /**
     * TestEngine constructor
     * @param level - Current Level
     */
    public TestEngine(Level level){
        myEntities = level.getEntities();
        addImageViews(myEntities);
    }

    private void addImageViews(Collection<Entity> entities) {
        for(Entity e : entities){
            SpriteComponent spriteComponent = (SpriteComponent) e.getComponent(SpriteComponent.class);
            String sprite = spriteComponent.getValue();
            WidthComponent widthComponent = (WidthComponent) e.getComponent(WidthComponent.class);
            Double width = widthComponent.getValue();
            HeightComponent heightComponent = (HeightComponent) e.getComponent(HeightComponent.class);
            Double height = heightComponent.getValue();
            XPositionComponent xPositionComponent = (XPositionComponent) e.getComponent(XPositionComponent.class);
            Double xPosition = xPositionComponent.getValue();
            YPositionComponent yPositionComponent = (YPositionComponent) e.getComponent(YPositionComponent.class);
            Double yPosition = yPositionComponent.getValue();

            System.out.println(sprite);
            ImageView image = new ImageView(sprite);

            image.setFitWidth(width);
            image.setFitHeight(height);
            image.setSmooth(false);
            image.setLayoutY(yPosition);
            image.setLayoutX(xPosition);
            e.addComponent(new ImageViewComponent(image));
        }
    }

    /**
     * Updates engine called in game loop
     * @param keys - current keys being pressed down
     * @return - Collection of entities with updated values
     */
    public Collection<Entity> updateState(Collection<KeyCode> keys){
        moveStuffRight();
        return myEntities;
    }

    private void moveStuffRight(){
        for(Entity entity : myEntities){
            XPositionComponent xPositionComponent = (XPositionComponent) entity.getComponent(XPositionComponent.class);
            Double xPosition = (Double) xPositionComponent.getValue();
            xPositionComponent.setValue(xPosition+1);
            ImageViewComponent imageViewComponent = (ImageViewComponent) entity.getComponent(ImageViewComponent.class);
            ImageView imageView = imageViewComponent.getValue();
            System.out.println(imageView.getX());
            System.out.println(imageView.getX()+1);
        }
    }

    private ImageView modifyX(ImageView image){
        image.setLayoutX(image.getX()+1);
        return image;
    }

}
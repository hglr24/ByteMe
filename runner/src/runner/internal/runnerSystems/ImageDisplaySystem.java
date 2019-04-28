package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.ImageViewComponent;
import engine.external.component.NameComponent;
import engine.external.component.TimerComponent;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import runner.internal.LevelRunner;
import java.util.Collection;

/**
 * System that shows the ImageViews
 * @author Louis Jensen
 */
public class ImageDisplaySystem extends RunnerSystem {
    private Group myGroup;

    /**
     * Constructor for ImageDisplaySystem
     * @param requiredComponents - all components an entity needs to have to be affected by the system
     * @param levelRunner - LevelRunner object so that system can modify the level
     * @param group - Group so that system can add and remove ImageViews
     */
    public ImageDisplaySystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner, Group group) {
        super(requiredComponents, levelRunner);
        myGroup = group;
    }

    /**
     * Displays images of desired entities
     */
    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(ImageViewComponent.class)){
                displayImage(entity);
            }
        }
    }

    private void displayImage(Entity entity) {
        ImageView image = (ImageView) entity.getComponent(ImageViewComponent.class).getValue();
        myGroup.getChildren().add(image);
    }
}
package engine.external.component;

import javafx.scene.image.ImageView;

/**
 * For storing the visualization for Entities
 * Not serializable; needs to be created in Engine
 */
public class ImageViewComponent extends Component<ImageView>{

    public ImageViewComponent(ImageView imageView){
        super(imageView);
    }

}

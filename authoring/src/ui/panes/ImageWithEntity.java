package ui.panes;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.AuthoringEntity;

import java.io.FileInputStream;

public class ImageWithEntity extends ImageView {
    private AuthoringEntity myAuthoringEntity;

    public ImageWithEntity(FileInputStream s, AuthoringEntity authoringEntity, double width, double height) {
        super(new Image(s, width, height, false, false));
        myAuthoringEntity = authoringEntity;
        //myAuthoringEntity.getPropertyMap().addListener((MapChangeListener<Enum, String>) change ->
                //propagateChanges(change.getKey(),  change.getValueRemoved(), change.getValueAdded()));
    }

    /**
     * Returns AuthoringEntity associated with this object
     * @return AuthoringEntity
     */
    public AuthoringEntity getAuthoringEntity(){
        return myAuthoringEntity;
    }

}

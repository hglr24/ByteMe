package frontend.ratings;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class Star {
    private boolean mySelected;
    private static final String OFF_LOCATION = "center/data/white-star.jpg";
    private static final String ON_LOCATION = "center/data/gold-star.jpg";

    private int myIndex;
    private ImageView myImageView;

    public Star(int index) {
        myIndex = index;
        setSelected(false);
    }

    public ImageView getImageDisplay() {
        return myImageView;
    }

    public void setSelected(boolean selected) {
        mySelected = selected;
        try {
            if(selected) {
                myImageView = new ImageView(new Image(new FileInputStream(ON_LOCATION)));
            } else {
                myImageView = new ImageView(new Image(new FileInputStream(OFF_LOCATION)));
            }
            myImageView.setPreserveRatio(true);
            myImageView.setFitWidth(25);
        } catch(Exception e) {
            // todo: handle this
        }
    }

    public int getIndex() {
        return myIndex;
    }

}

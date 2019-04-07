package ui.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Carrie Hunner
 * This class creates a small pane consisting of an ImageView
 * and then text acting as a title below it.
 * The text can be no longer than a set length and will be cut short
 * before being displayed to prevent formatting problems.
 */
public class AssetImageSubPane extends VBox {
    private static final int IMAGE_OFFSET = 10;
    private int myImageSize;
    private int myPaneSize;
    private static final int MAX_NUM_CHARS = 6;
    private static final int SPACING_SCALE = 10;
    /**
     * Creates a SubPane that consists of an ImageView and
     * then text acting as a title below it
     * @param title String of the name associated with the image
     * @param image ImageView to be displayed
     * @param paneSize Desired size of the pane
     */
    public AssetImageSubPane(String title, ImageView image, int paneSize){

        myPaneSize = paneSize;
        myImageSize = paneSize - IMAGE_OFFSET;


        Label text = new Label(cutText(title));
        text.getStyleClass().add("asset-manager-labels");

        //text.setWrappingWidth(myImageSize);


        System.out.println(text.getText());
        this.getChildren().add(image);
        this.getChildren().add(text);
        double spacing = paneSize/SPACING_SCALE;
        this.setPadding(new Insets(spacing, spacing, spacing, spacing));
        formatPaneAndImage(image);
    }

    private String cutText(String input){
        String result;
        if(input.length() >= MAX_NUM_CHARS){
            result = input.substring(0, MAX_NUM_CHARS);
        }
        else{
            result = input;
        }
        return result;
    }

    private void formatPaneAndImage(ImageView image) {
        this.setAlignment(Pos.CENTER);
        this.setMaxSize(myPaneSize, myPaneSize);
        this.setPrefSize(myImageSize, myImageSize);
        image.setFitWidth(myImageSize);
        image.setFitHeight(myImageSize);
    }
}

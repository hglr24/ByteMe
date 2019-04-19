package ui.windows;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import ui.EntityField;
import ui.LevelField;
import ui.Propertable;
import ui.panes.AssetImageSubPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Carrie Hunner
 * This class extends AssetManager and provides a
 * window for users to both upload and select previously
 * uploaded images
 */
public class ImageManager extends AssetManager {
    private ImageView mySelectedImageView;
    private Propertable myProp;
    private GridPane myGridPane;
    private int myRow;
    private int myCol;
    private static final int IMAGE_SUBPANE_SIZE = 60;
    private static final int MAX_NUM_COLS = 5;
    private static final String EXTENSION_KEY = "AcceptableImageExtensions";
    private static final String TITLE_KEY = "ImagesTitle";
    private static final String ASSET_IMAGE_FOLDER_PATH = GENERAL_RESOURCES.getString("images_filepath");

    /**
     * This default Constructor is needed when the user is choosing an image for
     * a new type before the entity actually needs to be made and kept track of
     */
    public ImageManager(){
        super(ASSET_IMAGE_FOLDER_PATH, TITLE_KEY, EXTENSION_KEY);
        mySelectedImageView = null;
        myProp = null;
        myRow = 0;
        myCol = 0;
    }

    /**
     * This constructor is needed when a user has already created an object and the image
     * is tied to a properties object
     * @param prop
     */
    public ImageManager(Propertable prop){
        this();
        myProp = prop;
    }

    /**
     * Gets the ImageView associated with the selected image
     * @return ImageView of the selected image
     */
    public ImageView getImageView(){
        ImageView imageView = mySelectedImageView;
        return imageView;
    }

    /**
     * Makes sure the selected asset is null so
     * other classes cannot access the selected
     * because the user closed and didn't apply it
     */
    @Override
    protected void handleClose(){
        mySelectedImageView = null;
        mySelectedAssetName = null;
        this.close();
    }

    private AssetImageSubPane createSubPane(File temp) {
        ImageView imageView = createImageView(temp);
        AssetImageSubPane subPane = new AssetImageSubPane(temp.getName().split("\\.")[0], imageView);
        subPane.setOnMouseClicked(mouseEvent -> {
            mySelectedAssetName = temp.getName();
            mySelectedImageView = imageView;
        });
        return subPane;
    }

    /**
     * Method that adds a file to the manager
     * @param file File to be added
     */
    @Override
    protected void addAsset(File file) {
        if(myCol == MAX_NUM_COLS){
            myCol = 0;
            myRow += 1;
        }
        myGridPane.add(createSubPane(file), myCol, myRow);
        myCol++;
    }

    /**
     * Method that should create and format a pane that
     * will be the content of the Manger's scrollpane
     * @return Pane of the desired content
     */
    @Override
    protected Pane createAndFormatScrollPaneContent() {
        myGridPane = new GridPane();
        myGridPane.setPadding(INSETS);
        ColumnConstraints colRestraint = new ColumnConstraints(SPACING + IMAGE_SUBPANE_SIZE);
        myGridPane.getColumnConstraints().add(colRestraint);
        RowConstraints rowRestraint = new RowConstraints(SPACING + IMAGE_SUBPANE_SIZE);
        myGridPane.getRowConstraints().add(rowRestraint);
        myGridPane.setAlignment(Pos.CENTER);
        return myGridPane;
    }


    /**
     * Sets the appropriate variables based on the selected Asset
     * before closing the window
     */
    @Override
    protected void handleApply() {
        if(!mySelectedAssetName.equals("")){
            if(myProp != null && myProp.getPropertyMap().containsKey(EntityField.IMAGE)){
                myProp.getPropertyMap().put(EntityField.IMAGE, mySelectedAssetName);
            }
            else if(myProp != null && myProp.getPropertyMap().containsKey(LevelField.BACKGROUND)){
                myProp.getPropertyMap().put(LevelField.BACKGROUND, mySelectedAssetName);
            }
            this.close();
        }
        else{
            handleNoAssetSelected();
        }
    }

    /**
     * This needs to be here for reflection purposes. In creating the buttons the
     * method must exist in the subclass
     */
    @Override
    protected void handleBrowse(){
        super.handleBrowse();
    }

    private ImageView createImageView(File temp) {
        ImageView imageView = new ImageView();
        try {
            Image image = new Image( new FileInputStream(temp.getPath()));
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            //The program is iterating through files found in the Assets folder
            //If a file is not found, it shouldn't be included so returning a blank ImageView is ok
        }
        return imageView;
    }


}

package ui.windows;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import ui.EntityField;
import ui.LevelField;
import ui.Propertable;
import ui.Utility;
import ui.panes.AssetImageSubPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Carrie Hunner
 * This class extends AssetManager and provides a
 * window for users to both upload and select previously
 * uploaded images
 */
public class ImageManager extends AssetManager {
    private ImageView mySelectedImageView;
    private Propertable myProp;
    private static final String EXTENSION_KEY = "AcceptableImageExtensions";
    private static final String TITLE_KEY = "ImagesTitle";
    private static final String ASSET_IMAGE_FOLDER_PATH = GENERAL_RESOURCES.getString("images_filepath");
    private Map<Pane, FlowPane> myMap;

    /**
     * This default Constructor is needed when the user is choosing an image for
     * a new type before the entity actually needs to be made and kept track of
     */
    public ImageManager(){
        super(ASSET_IMAGE_FOLDER_PATH, TITLE_KEY, EXTENSION_KEY);
        mySelectedImageView = null;
        myProp = null;
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
    protected void addAsset(File file, Pane pane) {
        myMap.putIfAbsent(pane, createAndFormatNewGridPane());
        FlowPane flowpane = myMap.get(pane);
        flowpane.setColumnHalignment(HPos.LEFT);
        flowpane.setRowValignment(VPos.TOP);
        flowpane.getChildren().add(createSubPane(file));
        if(pane.getChildren().contains(flowpane)){
            pane.getChildren().remove(flowpane);
        }
        pane.getChildren().add(flowpane);
    }

    @Override
    protected void initializeSubClassVariables() {
        myMap = new HashMap<>();
    }

    /**
     * Method that should create and format a pane that
     * will be the content of the Manger's scrollpane
     * @return Pane of the desired content
     */
    private FlowPane createAndFormatNewGridPane() {
        FlowPane flow = new FlowPane();
        flow.setPadding(INSETS);
        flow.setAlignment(Pos.CENTER);
        return flow;
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
            FileInputStream fileInputStream = new FileInputStream(temp.getPath());  //closed
            Image image = new Image( fileInputStream);  //closed
            imageView.setImage(image);
            Utility.closeInputStream(fileInputStream);  //closed
        } catch (FileNotFoundException e) {
            //The program is iterating through files found in the Assets folder
            //If a file is not found, it shouldn't be included so returning a blank ImageView is ok
        }
        return imageView;
    }


}

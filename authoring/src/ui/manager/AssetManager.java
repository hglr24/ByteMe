package ui.manager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.EntityField;
import ui.ErrorBox;
import ui.Propertable;
import ui.Utility;
import ui.panes.AssetImageSubPane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Carrie Hunner
 * This Class creates an AssetManger Stage
 * It displays all of the Images available in the Assets/Images folders
 */
public class AssetManager extends Stage {
    private ResourceBundle myResources;
    private Set<String> myImageExtensions;
    private ScrollPane myImageScrollPane;
    private HBox myButtonHBox;
    private String mySelectedImageName;
    private ImageView mySelectedImageView;
    private TitledPane myImageTitledPane;
    private VBox myOuterVBox;

    private static final String EXTENSION_RESOURCE_KEY = "AcceptableImageExtensions";
    private static final String BUNDLE_NAME = "AssetManager";
    private static final String ASSET_IMAGE_FOLDER_PATH = "authoring/Assets/Images";
    private static final String IMAGES_TITLE_KEY = "ImagesTitle";
    private static final String BUTTON_INFO = "Buttons";
    private static final String IO_ERROR = "IOError";
    private static final String ERROR_HEADER = "ErrorHeader";
    private static final String EXTENSION_PREFIX = "*.";
    private static final String DEFAULT_STYLE_SHEET = "default.css";
    private static final String ASSET_SPECIFIC_SHEET = "asset-manager";
    private static final double SPACING = 10;
    private static final int STAGE_WIDTH = 400;
    private static final int STAGE_HEIGHT = 300;
    private static final int MAX_NUM_COLS = 4;
    private static final int IMAGE_SUBPANE_SIZE = 60;
    private static final int BUTTON_SPACING = 20;
    private static final Insets INSETS = new Insets(SPACING, SPACING, SPACING, SPACING);

    private Propertable myProp;

    /**
     * This default Constructor is needed when the user is choosing an image for
     * a new type before the entity actually needs to be made and kept track of
     */
    public AssetManager(){
        mySelectedImageName = "";
        initializeVariables();
        initializeStage();
        fillImageExtensionSet();
        drawImageScrollPane();
        createButtonPane();
        fillVBox();
    }

    public AssetManager(Propertable prop) {
        super();
        //TODO: integrate in entity
        myProp = prop;

    }



    private void fillVBox() {
        myOuterVBox.getChildren().add(myImageTitledPane);
        myOuterVBox.getChildren().add(myButtonHBox);
    }


    private void createButtonPane() {
        String buttonString = myResources.getString(BUTTON_INFO);
        String[] buttonInfo = buttonString.split(",");
        formatButtonHBox();
        for(String s : buttonInfo){
            String[] textAndMethod = s.split(" ");
            Button button = Utility.makeButton(this, textAndMethod[1], textAndMethod[0]);
            myButtonHBox.getChildren().add(button);
        }

    }

    private void formatButtonHBox() {
        myButtonHBox.setFillHeight(true);
        myButtonHBox.setSpacing(BUTTON_SPACING);
        myButtonHBox.setAlignment(Pos.CENTER);
    }

    private void drawImageScrollPane() {
        GridPane gridPane = createAndFormatGridPane();
        myImageScrollPane.setPadding(INSETS);
        myImageScrollPane.setContent(gridPane);
        File assetFolder = new File(ASSET_IMAGE_FOLDER_PATH);
        int row = 0;
        int col = 0;
        for(File temp : assetFolder.listFiles()){
            try {
                String extension = temp.getName().split("\\.")[1];
                String lowerCaseExtension = extension.toLowerCase();
                if(myImageExtensions.contains(lowerCaseExtension)){
                    ImageView imageView = createImageView(temp);
                    AssetImageSubPane subPane = new AssetImageSubPane(temp.getName().split("\\.")[0], imageView);
                    subPane.setOnMouseClicked(mouseEvent -> {
                        mySelectedImageName = temp.getName();
                        mySelectedImageView = imageView;
                    });
                    if(col > MAX_NUM_COLS){
                        col = 0;
                        row++;
                    }
                    gridPane.add(subPane, col, row);
                    col++;
                }
            }
            catch (IndexOutOfBoundsException e){
                //this occurs when there is no file extension
                //this file should be skipped over and nothing should happen
                //this catch should be empty
            }
        }
    }

    private GridPane createAndFormatGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(INSETS);
        ColumnConstraints colRestraint = new ColumnConstraints(SPACING + IMAGE_SUBPANE_SIZE);
        gridPane.getColumnConstraints().add(colRestraint);
        RowConstraints rowRestraint = new RowConstraints(SPACING + IMAGE_SUBPANE_SIZE);
        gridPane.getRowConstraints().add(rowRestraint);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    private ImageView createImageView(File temp) {
        ImageView imageView = new ImageView();
        try {
            Image image = new Image( new FileInputStream(temp.getPath()));
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            //TODO DEAL WITH THIS
        }
        return imageView;
    }

    private void initializeVariables(){
        myResources = ResourceBundle.getBundle(BUNDLE_NAME);
        myImageExtensions = new HashSet<>();
        myOuterVBox = new VBox();
        myOuterVBox.setPrefHeight(STAGE_HEIGHT);
        myImageScrollPane = new ScrollPane();
        myButtonHBox = new HBox();
        myImageTitledPane = new TitledPane();
        myImageTitledPane.setContent(myImageScrollPane);
        myImageTitledPane.setText(myResources.getString(IMAGES_TITLE_KEY));
    }

    private void initializeStage(){
        this.setResizable(false);
        this.setWidth(STAGE_WIDTH);
        this.setHeight(STAGE_HEIGHT);
        Scene scene = new Scene(myOuterVBox);
        scene.getStylesheets().add(DEFAULT_STYLE_SHEET);
        myImageTitledPane.getStyleClass().add(ASSET_SPECIFIC_SHEET);
        this.setScene(scene);
    }

    private void fillImageExtensionSet(){
        for(String s: myResources.getString(EXTENSION_RESOURCE_KEY).split(",")){
            myImageExtensions.add(s);
        }
    }

    private void handleBrowse(){
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        for(String s : myImageExtensions){
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(s,EXTENSION_PREFIX + s);
            chooser.getExtensionFilters().add(extFilter);
        }
        File selectedFile = chooser.showOpenDialog(stage);
        try {
            BufferedImage image = ImageIO.read(selectedFile);
            File saveToFile = new File(ASSET_IMAGE_FOLDER_PATH + File.separator + selectedFile.getName());
            File otherSaveToFile = new File("Images/" + selectedFile.getName());
            String[] split = selectedFile.getPath().split("\\.");
            String extension = split[split.length-1];
            ImageIO.write(image, extension, saveToFile);
            ImageIO.write(image, extension, otherSaveToFile);
            drawImageScrollPane();
        } catch (IOException e) {
            //TODO: Test that this works
            String[] text = myResources.getString(IO_ERROR).split(",");
            ErrorBox errorBox = new ErrorBox(text[0], text[1]);
            errorBox.display();
        }
    }

    private void createAndDisplayAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(myResources.getString(ERROR_HEADER));
        alert.setHeaderText(contentText);
        alert.setContentText(null);
        alert.show();
    }

    private void handleClose(){
        if(!mySelectedImageName.equals("")){
            this.close();
        }
        else{
            String[] text = myResources.getString("NOIMAGE").split(",");
            ErrorBox errorBox = new ErrorBox(text[0], text[1]);
            errorBox.display();
        }
    }

    private void handleApply(){
        //TODO: add method to set Entity's image
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
     * Gets the Name of the selected image
     * @return String of the selected image name
     */
    public String getImageName(){
        return  mySelectedImageName;
    }

    private void setImageToSelected(String resourceName) {
        myProp.getPropertyMap().put(EntityField.IMAGE, resourceName);
    }
}

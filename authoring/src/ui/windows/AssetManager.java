package ui.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Carrie Hunner
 * This Class creates an AssetManger Stage
 * It is a superclass designed to enable different assets
 * to be displayed. Currently its sub-classes are ImageManager and
 * Audio Manager. To add another subclass, the methods must be implemented
 * but the corresponding AssetManger properties file must be edited
 * to have a title for the pane and a list of acceptable file extensions
 */
abstract public class AssetManager extends Stage {
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle("asset_manager");
    protected static final ResourceBundle GENERAL_RESOURCES = ResourceBundle.getBundle("authoring_general");
    private Set<String> myExtensions;
    private ScrollPane myScrollPane;
    private HBox myButtonHBox;
    protected String mySelectedAssetName;
    private TitledPane myImageTitledPane;
    private VBox myOuterVBox;
    private static final String BUTTON_INFO = "Buttons";

    private static final String IO_ERROR = "IOError";
    private static final String ERROR_HEADER = "ErrorHeader";
    private static final String EXTENSION_PREFIX = "*.";
    private static final String DEFAULT_STYLE_SHEET = "default.css";
    private static final String ASSET_SPECIFIC_SHEET = "asset-manager";
    protected String myAssetFolderPath;
    protected String myTitleKey;
    protected String myExtensionKey;
    protected static final double SPACING = 10;
    private static final int STAGE_WIDTH = 400;
    private static final int STAGE_HEIGHT = 300;
    private static final int BUTTON_SPACING = 20;
    protected static final Insets INSETS = new Insets(SPACING, SPACING, SPACING, SPACING);

    /**
     * This is a constructor that forces the coder to input info for creating the window
     * @param assetFolderPath String of the path to the assetFolder
     * @param titleKey String of the key to the window title in the asset_manager properties file
     * @param extensionKey String of the key to the list of extensions in the asset_manager properties file
     */
    public AssetManager(String assetFolderPath, String titleKey, String extensionKey){
        myAssetFolderPath = assetFolderPath;
        myTitleKey = titleKey;
        myExtensionKey = extensionKey;
        mySelectedAssetName = "";
        initializeVariables();
        initializeStage();
        fillExtensionSet();
        populateScrollPane();
        createButtonPane();
        fillVBox();
    }



    private void fillVBox() {
        myOuterVBox.getChildren().add(myImageTitledPane);
        myOuterVBox.getChildren().add(myButtonHBox);
    }


    private void createButtonPane() {
        String buttonString = RESOURCES.getString(BUTTON_INFO);
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

    private void populateScrollPane() {
        myScrollPane.setPadding(INSETS);
        myScrollPane.setContent(createAndFormatScrollPaneContent());
        File assetFolder = new File(myAssetFolderPath);
        for(File temp : assetFolder.listFiles()){
            try {
                String lowerCaseExtension = temp.getName().split("\\.")[1].toLowerCase();
                if(myExtensions.contains(lowerCaseExtension)){
                    addAsset(temp);
                }
            }
            catch (IndexOutOfBoundsException e){
                //this occurs when there is no file extension
                //this file should be skipped over and nothing should happen
                //this catch should be empty
            }
        }
    }

    /**
     * Method that adds a file to the manager
     * @param file File to be added
     */
    abstract protected void addAsset(File file);

    /**
     * Method that should create and format a pane that
     * will be the content of the Manger's scrollpane
     * @return Pane of the desired content
     */
    abstract protected Node createAndFormatScrollPaneContent();

    private void saveAsset(File selectedFile){
        try {
            File dest = new File(myAssetFolderPath + selectedFile.getName()); //any location
            System.out.println(dest.getPath());
            File outsideDest = new File("Images" + File.separator + selectedFile.getName());
            Files.copy(selectedFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(selectedFile.toPath(), outsideDest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            String[] text = RESOURCES.getString(IO_ERROR).split(",");
            ErrorBox errorBox = new ErrorBox(text[0], text[1]);
            errorBox.display();
        }
    }

    private void initializeVariables(){
        myExtensions = new HashSet<>();
        myOuterVBox = new VBox();
        myOuterVBox.setPrefHeight(STAGE_HEIGHT);
        myScrollPane = new ScrollPane();
        myButtonHBox = new HBox();
        myImageTitledPane = new TitledPane();
        myImageTitledPane.setContent(myScrollPane);
        myImageTitledPane.setText(RESOURCES.getString(myTitleKey));
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

    private void fillExtensionSet() {
        for(String s: RESOURCES.getString(myExtensionKey).split(",")){
            myExtensions.add(s);
        }
    }

    /**
     * Allows the user to browse and add new assets
     * this is protected because the method needs to be
     * accessible in subclasses for reflection
     */
    protected void handleBrowse(){
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        initializeFileExtensions(chooser);
        File selectedFile = chooser.showOpenDialog(stage);
        if(selectedFile != null){
            saveAsset(selectedFile);
            populateScrollPane();
        }
    }

    private void initializeFileExtensions(FileChooser chooser) {
        for(String s : myExtensions){
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(s,EXTENSION_PREFIX + s);
            chooser.getExtensionFilters().add(extFilter);
        }
    }

    protected void createAndDisplayAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(RESOURCES.getString(ERROR_HEADER));
        alert.setHeaderText(contentText);
        alert.setContentText(null);
        alert.show();
    }

    /**
     * Makes sure the selected asset is null so
     * other classes cannot access the selected
     * because the user closed and didn't apply it
     */
    protected void handleClose(){
        mySelectedAssetName = null;
        this.close();
    }


    /**
     * This method is called by subclasses when someone hits the "Apply" button but
     * no Asset is selected
     */
    protected void handleNoAssetSelected(){
        String[] text = RESOURCES.getString("NOIMAGE").split(",");
        ErrorBox errorBox = new ErrorBox(text[0], text[1]);
        errorBox.display();
    }

    /**
     * should apply whatever needs to happen after an image has been selected
     */
    abstract protected void handleApply();


    /**
     * Gets the Name of the selected asset
     * @return String of the selected asset name
     */
    public String getAssetName(){
        return mySelectedAssetName;
    }
}

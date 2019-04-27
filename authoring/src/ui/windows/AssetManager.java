package ui.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.ErrorBox;
import ui.Utility;
import ui.TreeNode;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
    private HBox myButtonHBox;
    protected String mySelectedAssetName;
    private TabPane myTabPane;
    private VBox myOuterVBox;
    private ScrollPane myScrollPane;
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
    protected static final int STAGE_WIDTH = 400;
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
        initializeSubClassVariables();
        initializeStage();
        fillExtensionSet();
        populateTabs();
        createButtonPane();
        setUpOuterPanes();
    }



    private void setUpOuterPanes() {
        myOuterVBox.getChildren().add(myTabPane);
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

    private void populateTabs() {
        File assetFolder = new File(myAssetFolderPath);     //going to images directory
        List<File> fileList = Utility.getFilesFromFolder(assetFolder);

        Tab userUploaded = new Tab();
        userUploaded.setText("User Uploaded");
        VBox userVBox = new VBox();
        ScrollPane userScrollPane = new ScrollPane();
        userScrollPane.setFitToWidth(true);
        userScrollPane.setContent(userVBox);
        userUploaded.setContent(userScrollPane);
        myTabPane.getTabs().add(userUploaded);

        Tab defaultTab = new Tab();
        defaultTab.setText("Default");
        VBox vBox = new VBox();
        myTabPane.getTabs().add(defaultTab);
        ScrollPane defaultScrollPane = new ScrollPane();
        defaultScrollPane.setFitToWidth(true);
        defaultScrollPane.setContent(vBox);
        defaultTab.setContent(defaultScrollPane);
        TreeNode root = new TreeNode("root");

        for(File file : fileList){
            //TODO check extensions here
            //assuming correct extensions from here on out
            String[] infoArray = file.getName().split("#");  //TODO make this character special somewhere
            if(infoArray.length <= 1){
                //add to default tab
                addAsset(file, userVBox);

            }
            else{
                TreeNode traverseNode = root;
                List<String> infoList = new ArrayList<>(Arrays.asList(infoArray));
                infoList.remove(0); //always "" because name should always start with "/" and splitting around "/"
                addToTree(infoList, traverseNode, file);
            }
        }

        TreeNode traverseNode = root;
        fillVBox(traverseNode, vBox);

    }

    private void fillVBox(TreeNode root, VBox vBox) {
        for(TreeNode treeNode : root.getNodeChildren()){
            List<TreeNode> toBePanes = treeNode.getNodeChildren();
            for(TreeNode treeNode1 : toBePanes) {
                TitledPane titledPane = new TitledPane();
                titledPane.setText(treeNode1.getName());
                titledPane.setAlignment(Pos.TOP_LEFT);
                vBox.getChildren().add(titledPane);
                VBox vBox1 = new VBox();
                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setContent(vBox1);
                scrollPane.setFitToWidth(true);
                titledPane.setContent(scrollPane);
                fillVBox(treeNode1, vBox1);
            }
        }
        for(File file : root.getFileChildren()){
            addAsset(file, vBox);
        }
    }

    public void addToTree(List<String> info, TreeNode root, File file){
        if(info.size() == 1){
            root.addChild(file);
            return;
        }
        else{
            root = root.next(info.get(0));
            info.remove(0);
            addToTree(info, root, file);
        }
    }

//    private void populateTabs() {
//        System.out.println("***********************");
//        File assetFolder = new File(myAssetFolderPath);     //going to images directory
//        Map<File, List<File>> rootDirectoryMap = Utility.getSubFoldersToFiles(assetFolder);
//        List<File> directoriesToGetAssetsFrom = new ArrayList<>();
//        VBox titledPaneVBox = new VBox();
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setFitToWidth(true);
//        scrollPane.setContent(titledPaneVBox);
//        for(Map.Entry<File, List<File>> entry : rootDirectoryMap.entrySet()){       //going to defaults/user_uploaded
//            directoriesToGetAssetsFrom.add(entry.getKey());
//            System.out.println("Root directory: " + entry.getKey().getName());
//
//            Tab tab = new Tab();
//            tab.setText(entry.getKey().getName());
//            tab.setContent(scrollPane);
//
//
//
//
//            for(File file : entry.getValue()){
//
//                if(file.isDirectory()){
//                    directoriesToGetAssetsFrom.add(file);
//                    System.out.println("\tDirectory: " + file.getName());
//                    Map<File, List<File>> subDirectoryMap = Utility.getSubFoldersToFiles(file);
//                    TitledPane titledPane = new TitledPane();
//                    titledPaneVBox.getChildren().add(titledPane);
//                    titledPane.setText(file.getName());
//                    Pane pane = new Pane();
//                    titledPane.setContent(pane);
//                    for(Map.Entry<File, List<File>> entry1 : subDirectoryMap.entrySet()){
//                        System.out.println("\t\t" + entry1.getKey().getName());
//                        for(File subFile : entry1.getValue()){
//                            System.out.println("\t\t\t" + subFile.getName());
//                            String lowerCaseExtension = subFile.getName().split("\\.")[1].toLowerCase();
//                            if(myExtensions.contains(lowerCaseExtension)){
//                                addAsset(subFile, pane);
//                            }
//                        }
//                    }
//                    for(File file1 : Utility.getFilesFromFolder(file)){
//                        System.out.println("\t\t" + file1.getName());
//                        addAsset(file1, pane);
//                    }
//                }
//            }
//
//            myTabPane.getTabs().add(tab);
//        }
//        for(File file : directoriesToGetAssetsFrom){
//            for(File assetFile : Utility.getFilesFromFolder(file)){
//                System.out.println("\t" + assetFile.getName());
//                String lowerCaseExtension = assetFile.getName().split("\\.")[1].toLowerCase();
//                if(myExtensions.contains(lowerCaseExtension)){
//                    Pane pane = new Pane();
//                    titledPaneVBox.getChildren().add(pane);
//                    addAsset(assetFile, pane);
//                }
//            }
//        }
//    }

    /**
     * Method that adds a file to the manager
     * @param file File to be added
     * @param pane to add the asset to
     */
    abstract protected void addAsset(File file, Pane pane);

    /**
     * Some variables in the subclasses need to be initialized before this constructor is finished
     * creating everything
     */
    abstract protected void initializeSubClassVariables();

    private void saveAsset(File selectedFile){
        try {
            File dest = new File(myAssetFolderPath + selectedFile.getName()); //any location
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
        myButtonHBox = new HBox();
        myTabPane = new TabPane();
        myScrollPane = new ScrollPane();
    }

    private void initializeStage(){
        this.setResizable(false);
        this.setWidth(STAGE_WIDTH);
        this.setHeight(STAGE_HEIGHT);
        Scene scene = new Scene(myOuterVBox);
        scene.getStylesheets().add(DEFAULT_STYLE_SHEET);
        myTabPane.getStyleClass().add(ASSET_SPECIFIC_SHEET);
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
            populateTabs();
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
    @SuppressWarnings("unused")
    abstract protected void handleApply();


    /**
     * Gets the Name of the selected asset
     * @return String of the selected asset name
     */
    public String getAssetName(){
        return mySelectedAssetName;
    }
}

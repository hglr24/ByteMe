package ui.main;

import data.external.DataManager;
import factory.GameTranslator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import runner.external.Game;
import data.external.GameCenterData;
import ui.AuthoringLevel;
import ui.ErrorBox;
import ui.Propertable;
import ui.PropertableType;
import ui.UIException;
import ui.Utility;
import ui.manager.GroupManager;
import ui.manager.InfoEditor;
import ui.manager.ObjectManager;
import ui.panes.DefaultTypesPane;
import ui.panes.LevelsPane;
import ui.panes.PropertiesPane;
import ui.panes.UserCreatedTypesPane;
import ui.panes.Viewer;
import voogasalad.util.reflection.Reflection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Harry Ross
 */
public class MainGUI {

    private Game myLoadedGame;
    private GameCenterData myGameData;
    private Stage myStage;
    private HBox myViewerBox;
    private UserCreatedTypesPane myCreatedTypesPane;
    private ObjectManager myObjectManager;
    private Map<Propertable, Viewer> myViewers;
    private ObservableStringValue myCurrentStyle;
    private ObjectProperty<Propertable> mySelectedEntity;
    private ObjectProperty<Propertable> myCurrentLevel;

    private static final double STAGE_MIN_HEIGHT = 600;
    private static final double PROP_PANE_HEIGHT = 210;
    private static final String DEFAULT_FIRST_LEVEL = "New_Level_1";
    private static final String DEFAULT_STYLESHEET = "default.css";
    private static final String MENU_ITEMS_FILE = "main_menu_items";
    private static final String STAGE_TITLE = "ByteMe Authoring Environment";
    private static final ResourceBundle GENERAL_RESOURCES = ResourceBundle.getBundle("authoring_general");
    private static final ResourceBundle SAVING_ASSETS_RESOURCES = ResourceBundle.getBundle("mainGUI_assets");

    public MainGUI() { // Default constructor for creating a new game from scratch
        myLoadedGame = new Game();
        myGameData = new GameCenterData();
        myStage = new Stage();
        myViewers = new HashMap<>();
        defaultGameData();
        myCurrentLevel = new SimpleObjectProperty<>();
        mySelectedEntity = new SimpleObjectProperty<>();
        myObjectManager = new ObjectManager(myCurrentLevel);

        AuthoringLevel blankLevel = new AuthoringLevel(DEFAULT_FIRST_LEVEL, myObjectManager);
        myObjectManager.addLevel(blankLevel);
        myCurrentLevel.setValue(blankLevel);

        myCurrentStyle = new SimpleStringProperty(DEFAULT_STYLESHEET);
        myCurrentStyle.addListener((change, oldVal, newVal) -> swapStylesheet(oldVal, newVal));
        myCurrentLevel.addListener((change, oldVal, newVal) -> swapViewer(oldVal, newVal));
    }

    public MainGUI(Game game, GameCenterData gameData) {
        this();
        myLoadedGame = game;
        myGameData = gameData;
    }

    public void launch() {
        myStage.setTitle(STAGE_TITLE);
        myStage.setScene(createMainGUI());
        myStage.setMinHeight(STAGE_MIN_HEIGHT);
        myStage.show();
        myStage.setMinWidth(myStage.getWidth());
    }

    private Scene createMainGUI() { //TODO clean up
        BorderPane mainBorderPane = new BorderPane();
        Scene mainScene = new Scene(mainBorderPane);
        HBox propPaneBox = new HBox();
        propPaneBox.getStyleClass().add("prop-pane-box");
        HBox entityPaneBox = new HBox();
        entityPaneBox.getStyleClass().add("entity-pane-box");

        myCreatedTypesPane = createTypePanes(entityPaneBox, mainScene);
        createViewersForExistingLevels();

        createPropertiesPanes(propPaneBox, mainScene);
        myViewerBox = new HBox(myViewers.get(myCurrentLevel.getValue()));
        myViewerBox.prefHeightProperty().bind(mainScene.heightProperty());
        myViewerBox.prefWidthProperty().bind(mainScene.widthProperty());

        mainBorderPane.setCenter(myViewerBox);
        mainBorderPane.setRight(entityPaneBox);
        mainBorderPane.setTop(addMenu());
        mainBorderPane.setBottom(propPaneBox);

        mainScene.getStylesheets().add(myCurrentStyle.getValue());
        mainBorderPane.getCenter().getStyleClass().add("main-center-pane");
        return mainScene;
    }

    private void createViewersForExistingLevels() {
        for (AuthoringLevel level : myObjectManager.getLevels()) {
            myViewers.put(level, createViewer(level));
        }
    }

    private UserCreatedTypesPane createTypePanes(HBox entityPaneBox, Scene mainScene) {
        UserCreatedTypesPane userCreatedTypesPane = new UserCreatedTypesPane(myObjectManager);
        DefaultTypesPane defaultTypesPane = new DefaultTypesPane(userCreatedTypesPane);
        entityPaneBox.getChildren().addAll(defaultTypesPane, userCreatedTypesPane);
        entityPaneBox.prefHeightProperty().bind(mainScene.heightProperty().subtract(PROP_PANE_HEIGHT));
        userCreatedTypesPane.prefHeightProperty().bind(entityPaneBox.heightProperty());
        defaultTypesPane.prefHeightProperty().bind(entityPaneBox.heightProperty());
        return userCreatedTypesPane;
    }

    private Viewer createViewer(AuthoringLevel levelBasis) {
        return new Viewer(levelBasis, myCreatedTypesPane, mySelectedEntity, myObjectManager);
    }

    @SuppressWarnings("Duplicates")
    private void createPropertiesPanes(HBox propPaneBox, Scene mainScene) {
        try {
            LevelsPane levelsPane = new LevelsPane(myObjectManager, myCurrentLevel);
            PropertiesPane objectProperties =
                    new PropertiesPane(myObjectManager, PropertableType.OBJECT, mySelectedEntity);
            PropertiesPane levelProperties =
                    new PropertiesPane(myObjectManager, PropertableType.LEVEL, myCurrentLevel);
            PropertiesPane instanceProperties =
                    new PropertiesPane(myObjectManager, PropertableType.INSTANCE, mySelectedEntity);
            levelsPane.prefWidthProperty().bind(mainScene.widthProperty().divide(4));
            objectProperties.prefWidthProperty().bind(mainScene.widthProperty().divide(4));
            levelProperties.prefWidthProperty().bind(mainScene.widthProperty().divide(4));
            instanceProperties.prefWidthProperty().bind(mainScene.widthProperty().divide(4));
            propPaneBox.getChildren().addAll(levelsPane, levelProperties, instanceProperties, objectProperties);
        } catch (UIException e) {
            ErrorBox errorbox = new ErrorBox("Properties Error", e.getMessage());
            errorbox.display();
        }
    }

    private MenuBar addMenu() {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createMenu("File", "New", "Open", "Save"), //TODO make this better
                createMenu("Edit", "Info", "Groups", "Preferences"), createMenu("View", "Fullscreen"));
        return menuBar;
    }

    private Menu createMenu(String label, String... options) {
        Menu newMenu = new Menu(label);
        ResourceBundle bundle = ResourceBundle.getBundle(MENU_ITEMS_FILE);
        for (String option : options) {
            newMenu.getItems().add(makeMenuItem(option, event -> {
                try {
                    this.getClass().getDeclaredMethod((String) bundle.getObject(option)).invoke(this);
                } catch (Exception e) {
                    // catch FIX
                }
            }));
        }
        return newMenu;
    }

    private MenuItem makeMenuItem(String label, EventHandler<ActionEvent> handler) {
        MenuItem newItem = new MenuItem(label);
        newItem.setOnAction(handler);
        return newItem;
    }

    @SuppressWarnings("unused")
    private void newGame() {
        MainGUI newWorkspace = new MainGUI();
        newWorkspace.launch();
    }

    @SuppressWarnings("unused")
    private void openGame() {
        System.out.println("Open"); //TODO
        DataManager dataManager = new DataManager();
        loadAllAssets(dataManager);
    }

    @SuppressWarnings("unused")
    private void saveGame() {
        GameTranslator translator = new GameTranslator(myObjectManager);
        try {
            Game exportableGame = translator.translate();
            DataManager dm = new DataManager();
            dm.saveGameData(myGameData.getFolderName(), myGameData.getAuthorName(), exportableGame);
            dm.saveGameInfo(myGameData.getFolderName(), myGameData.getAuthorName(), myGameData);
            saveAndClearFolder(dm, "authoring/assets/images/");
            saveAndClearFolder(dm, "authoring/assets/audio");
        } catch (UIException e) {
            ErrorBox errorBox = new ErrorBox("Save Error", e.getMessage());
            errorBox.showAndWait();
        }
    }

    @SuppressWarnings("unused")
    private void openGroupManager() {
        GroupManager groupManager = new GroupManager(myObjectManager);
        groupManager.showAndWait();
    }

    @SuppressWarnings("unused")
    private void openGameInfo() {
        InfoEditor infoEditor = new InfoEditor(myGameData);
        infoEditor.showAndWait();
    }

    @SuppressWarnings("unused")
    private void openPreferences() {
        System.out.println("Preferences"); //TODO
    }

    @SuppressWarnings("unused")
    private void toggleFullscreen() {
        myStage.setFullScreen(!myStage.isFullScreen());
    }

    private void swapViewer(Propertable oldLevel, Propertable newLevel) {
        myViewerBox.getChildren().remove(myViewers.get(oldLevel));
        if (!myViewers.containsKey(newLevel))
            myViewers.put(newLevel, createViewer((AuthoringLevel) newLevel));

        myViewerBox.getChildren().add(myViewers.get(newLevel));
    }

    private void swapStylesheet(String oldVal, String newVal) {
        myStage.getScene().getStylesheets().remove(oldVal);
        myStage.getScene().getStylesheets().add(newVal);
    }

    private void defaultGameData() {
        myGameData.setFolderName("NewGame");
        myGameData.setImageLocation("");
        myGameData.setTitle("New Game");
        myGameData.setDescription("A fun new game");
    }

    //TODO make this work for audio too - need to differentiate which dataManager method using
    //outerDirectory - folder that needs sub-folders "defaults" and "user-uploaded"
    private void saveAndClearFolder(DataManager dataManager, String outerDirectoryPath){
        File outerDirectory = new File(outerDirectoryPath);
        for(File file : outerDirectory.listFiles()){
            dataManager.saveImage(file.getName(), file);
            //TODO uncomment file.delete();
        }
    }

    private void loadAllAssets(DataManager dataManager){
        String prefix = myGameData.getTitle() + myGameData.getAuthorName();
        //loadAssets(dataManager, SAVING_ASSETS_RESOURCES.getString("images_filepath"), prefix);
        loadAssets(dataManager, SAVING_ASSETS_RESOURCES.getString("images_filepath"), GENERAL_RESOURCES.getString("defaults"));
        //loadAssets(dataManager, SAVING_ASSETS_RESOURCES.getString("audio_filepath"), prefix);
        loadAssets(dataManager, SAVING_ASSETS_RESOURCES.getString("audio_filepath"), GENERAL_RESOURCES.getString("defaults"));
    }


    //TODO: differentiate between images and audio
    //TODO: test the file copying
    private void loadAssets(DataManager dataManager, String folderFilePath, String prefix){
//        System.out.println("Made it to loadAssets");
//        String key = folderFilePath.split("/")[folderFilePath.split("/").length-1];
//        try {
//            System.out.println("Key: " + key);
//            Map<String, InputStream> imagesMap = (Map<String, InputStream>) Reflection.callMethod(dataManager, GENERAL_RESOURCES.getString(key), prefix);
//            for(Map.Entry<String, InputStream> entry : imagesMap.entrySet()){
//                InputStream inputStream = entry.getValue();
//                File destination = new File(folderFilePath + entry.getKey());
//                Files.copy(inputStream, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
//            }
//        } catch (IOException e) {
//            //TODO: handle error
//            e.printStackTrace();
//        }
    }
}

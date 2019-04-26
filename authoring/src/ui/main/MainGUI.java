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
import ui.manager.GroupManager;
import ui.manager.InfoEditor;
import ui.manager.ObjectManager;
import ui.panes.DefaultTypesPane;
import ui.panes.LevelsPane;
import ui.panes.PropertiesPane;
import ui.panes.UserCreatedTypesPane;
import ui.panes.Viewer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Harry Ross
 */
public class MainGUI {

    private Game myGame;
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

    public MainGUI() { // Default constructor for creating a new game from scratch
        myGame = new Game();
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
        myGame = game;
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
    }

    @SuppressWarnings("unused")
    private void saveGame() {
        System.out.println("save game called");
        GameTranslator translator = new GameTranslator(myGame, myGameData, myObjectManager);
        Game exportableGame = translator.translate();
        GameCenterData gameData = translator.getNewGameData();

        System.out.println("Made it to line");
        DataManager dm = new DataManager();
        System.out.println("Made it after the line");
//        dm.createGameFolder(gameData.getFolderName());
//        dm.saveGameData(gameData.getFolderName(), exportableGame);
//        dm.saveGameInfo(gameData.getFolderName(), gameData);
        dm.saveGameData(gameData.getFolderName(), gameData.getAuthorName(), exportableGame);
        dm.saveGameInfo(gameData.getFolderName(), gameData.getAuthorName(), gameData);
        System.out.println("Game saved");
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
    private void saveAndClearFolder(DataManager dataManager, File outerDirectory){
        System.out.println("Save and clear folder called");
        System.out.println(outerDirectory.getName());
        for(File innerFolder : outerDirectory.listFiles()){
            if(innerFolder.isDirectory()){
                for(File asset : innerFolder.listFiles()){
                    System.out.println(asset.getName());
                    String gameTitle = myGameData.getTitle();
                    String authorUsername = myGameData.getAuthorName();
                    String imageTitle = gameTitle + authorUsername + asset.getName();
                    dataManager.saveImage(imageTitle, asset);
                    //TODO: uncomment this
//            if(temp.delete()){
//                System.out.println("deleted");
//            }
                }
            }


        }
    }

    //TODO: differentiate between images and audio
    //TODO: test the file copying
    private void loadAssets(DataManager dataManager, String folderFilePath){
        String prefix = myGameData.getTitle() + myGameData.getAuthorName();
        try {
            Map<String, InputStream> imagesMap = dataManager.loadAllImages(prefix);
            for(Map.Entry<String, InputStream> entry : imagesMap.entrySet()){
                Files.copy(entry.getValue(), Paths.get(GENERAL_RESOURCES.getString("images_filepath")), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (SQLException e) {
            //TODO: handle error
            e.printStackTrace();
        } catch (IOException e) {
            //TODO: handle error
            e.printStackTrace();
        }
    }

}
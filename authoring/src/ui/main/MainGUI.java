package ui.main;

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
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.ErrorBox;
import ui.LevelField;
import ui.Propertable;
import ui.PropertableType;
import ui.UIException;
import ui.manager.GroupManager;
import ui.manager.ObjectManager;
import ui.panes.DefaultTypesPane;
import ui.panes.PropertiesPane;
import ui.panes.UserCreatedTypesPane;
import ui.panes.Viewer;

import java.util.ResourceBundle;

/**
 * @author Harry Ross
 */
public class MainGUI {

    private Game myGame;
    private Stage myStage;
    private ObjectManager myObjectManager;
    private ObservableStringValue myCurrentStyle;
    private ObjectProperty<Propertable> mySelectedEntity;
    private ObjectProperty<Propertable> myCurrentLevel;

    private static final double STAGE_MIN_HEIGHT = 600;
    private static final double PROP_PANE_HEIGHT = 210;
    private static final String DEFAULT_STYLESHEET = "default.css"; //TODO propagate Stylesheet
    private static final String MENU_ITEMS_FILE = "main_menu_items";
    private static final String STAGE_TITLE = "ByteMe Authoring Environment";

    public MainGUI() { // Default constructor for creating a new game from scratch
        myGame = new Game();
        myStage = new Stage();
        myObjectManager = new ObjectManager();
        AuthoringLevel blankLevel = new AuthoringLevel("Level_1", myObjectManager);
        AuthoringEntity blankEntity = new AuthoringEntity("Object_1", myObjectManager);
        mySelectedEntity = new SimpleObjectProperty<>(null);
        myCurrentLevel = new SimpleObjectProperty<>(blankLevel);
        myCurrentStyle = new SimpleStringProperty(DEFAULT_STYLESHEET);
        myCurrentStyle.addListener((change, oldVal, newVal) -> swapStylesheet(oldVal, newVal));
    }

    public MainGUI(Game game) {
        this();
        myGame = game;
    }

    public void launch() {
        myStage.setTitle(STAGE_TITLE);
        myStage.setScene(createMainGUI());
        myStage.setMinHeight(STAGE_MIN_HEIGHT);
        myStage.show();
        myStage.setMinWidth(myStage.getWidth());
    }

    private Scene createMainGUI(){
        BorderPane mainBorderPane = new BorderPane();
        Scene mainScene = new Scene(mainBorderPane);
        HBox propPaneBox = new HBox();
        propPaneBox.getStyleClass().add("prop-pane-box");
        HBox entityPaneBox = new HBox();
        entityPaneBox.getStyleClass().add("entity-pane-box");

        UserCreatedTypesPane userCreatedTypesPane = createTypePanes(entityPaneBox, mainScene);
        Viewer viewer = createViewer(userCreatedTypesPane);
        createPropertiesPanes(propPaneBox, mainScene);
        HBox centerPaneBox = new HBox(viewer);
        centerPaneBox.prefHeightProperty().bind(mainScene.heightProperty());
        centerPaneBox.prefWidthProperty().bind(mainScene.widthProperty());

        mainBorderPane.setCenter(centerPaneBox);
        mainBorderPane.setRight(entityPaneBox);
        mainBorderPane.setTop(addMenu());
        mainBorderPane.setBottom(propPaneBox);

        mainScene.getStylesheets().add(myCurrentStyle.getValue());
        mainBorderPane.getCenter().getStyleClass().add("main-center-pane");
        return mainScene;
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

    private Viewer createViewer(UserCreatedTypesPane userCreatedTypesPane) {
        Viewer viewer = new Viewer(myCurrentLevel, userCreatedTypesPane, mySelectedEntity);
        viewer.setMinWidth(400);
        viewer.setMinHeight(300);
        return viewer;
    }

    private void createPropertiesPanes(HBox propPaneBox, Scene mainScene) {
        try {
            PropertiesPane objectProperties =
                    new PropertiesPane(PropertableType.OBJECT, mySelectedEntity, myObjectManager.getLabelManager());
            PropertiesPane levelProperties =
                    new PropertiesPane(PropertableType.LEVEL, myCurrentLevel, myObjectManager.getLabelManager());
            PropertiesPane instanceProperties =
                    new PropertiesPane(PropertableType.INSTANCE, mySelectedEntity, myObjectManager.getLabelManager());
            objectProperties.prefWidthProperty().bind(mainScene.widthProperty().divide(3));
            levelProperties.prefWidthProperty().bind(mainScene.widthProperty().divide(3));
            instanceProperties.prefWidthProperty().bind(mainScene.widthProperty().divide(3));
            propPaneBox.getChildren().addAll(levelProperties, instanceProperties, objectProperties);
        } catch (UIException e) {
            ErrorBox errorbox = new ErrorBox("Properties Error", e.getMessage());
            errorbox.display();
        }
    }

    private MenuBar addMenu() {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createMenu("File", "New", "Open", "Save"), //TODO make this better
                createMenu("Edit", "Groups", "Preferences"), createMenu("View", "Fullscreen"));
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

    private void newGame() {
        System.out.println("New"); //TODO
    }

    private void openGame() {
        System.out.println("Open"); //TODO
    }

    private void saveGame() {
        System.out.println("Save"); //TODO
    }

    private void openGroupManager() {
        GroupManager groupManager = new GroupManager(myObjectManager);
        groupManager.showAndWait();
    }

    private void openPreferences() {
        System.out.println("Preferences"); //TODO
    }

    private void toggleFullscreen() {
        myStage.setFullScreen(!myStage.isFullScreen());
    }

    private void swapStylesheet(String oldVal, String newVal) {
        myStage.getScene().getStylesheets().remove(oldVal);
        myStage.getScene().getStylesheets().add(newVal);
    }

}
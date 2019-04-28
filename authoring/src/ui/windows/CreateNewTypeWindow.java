package ui.windows;

import data.external.GameCenterData;
import engine.external.Entity;
import engine.external.component.NameComponent;
import engine.external.component.SpriteComponent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.DefaultTypeXMLReaderFactory;
import ui.ErrorBox;
import ui.Utility;
import ui.manager.ObjectManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Carrie Hunner
 * This class creates a pop-up window that allows the
 * user to create a new object type
 */
public class CreateNewTypeWindow extends Stage {
    private GridPane myGridPane;
    private static final ResourceBundle SYNTAX_RESOURCES = ResourceBundle.getBundle("property_syntax");
    private static final ResourceBundle WINDOW_RESOURCES = ResourceBundle.getBundle("new_object_window");
    private static final ResourceBundle GENERAL_RESOURCES = ResourceBundle.getBundle("authoring_general");

    private ComboBox myCategoryComboBox;
    private ComboBox myDefaultTypeNameComboBox;
    private TextField myTextField;
    private Node myButtonNode;
    private Pane mySelectedImagePane;
    private DefaultTypeXMLReaderFactory myDefaultTypesFactory;
    private String mySelectedImageName;
    private Entity myUserCreatedEntity;

    private static final int STAGE_HEIGHT = 325;
    private static final int STAGE_WIDTH = 400;
    private static final int GRIDPANE_GAP = 10;
    private static final int INPUT_WIDTH = 100;
    private static final int PICTURE_SIZE = 50;
    private static final String STYLE_SHEET = "default.css";
    private GameCenterData myGameCenterData;

    /**
     * Creates a window for the user to create a new type
     * @param defaultName Name of the defaultEntity the user is basing their
     *                    object off of
     */
    public CreateNewTypeWindow(String defaultName, GameCenterData gameCenterData){
        mySelectedImageName = null;
        myGameCenterData = gameCenterData;
        initializeGridPane();
        initializeVariables();
        createContent(myDefaultTypesFactory.getCategory(defaultName), defaultName);
        initializeAndDisplayStage();
    }

    /**
     * Gets the entity defined by the user
     * Called by DefaultTypesPane when this stage is closed
     * @return Entity defined by the user
     */
    public Entity getUserCreatedEntity(){
        return  myUserCreatedEntity;
    }

    /**
     * Using this name, the category and a new entity can be created from the DefaultXMLReaderFactory
     * @return String of the name of the default type it is based on
     */
    public String getDefaultTypeName(){
        String defaultTypeName = myDefaultTypeNameComboBox.getValue().toString();
        return defaultTypeName;
    }


    private void initializeVariables() {
        myDefaultTypeNameComboBox = new ComboBox();
        myCategoryComboBox = new ComboBox();
        myTextField = new TextField();
        myButtonNode = new HBox();
        mySelectedImagePane = new Pane();
        myDefaultTypesFactory = new DefaultTypeXMLReaderFactory();
        mySelectedImageName = "";
        myUserCreatedEntity = null;
    }

    private void initializeGridPane(){
        myGridPane = new GridPane();
        myGridPane.setAlignment(Pos.TOP_CENTER);
        myGridPane.setHgap(GRIDPANE_GAP);
        myGridPane.setVgap(GRIDPANE_GAP);
    }

    private void createContent(String isANewTypeOf, String isBasedOn) {
        createAndAddLabel(WINDOW_RESOURCES.getString("Label1"));
        createAndAddTextField();
        createAndAddLabel(WINDOW_RESOURCES.getString("Label2"));
        createAndAddCategoryDropDown(isANewTypeOf);
        createAndAddLabel(WINDOW_RESOURCES.getString("Label3"));
        createAndAddBasedOnDropDown(isBasedOn);
        createImageSelectionPane();
        createButtonPane();
    }

    private void createImageSelectionPane() {
        String[] buttonResources = WINDOW_RESOURCES.getString("AssetButton").split(",");
        Button button = Utility.makeButton(this, buttonResources[1], buttonResources[0]);
        myGridPane.add(button, 0, myGridPane.getRowCount());

        FileInputStream fileInputStream = null; //closed
        try {
            fileInputStream = new FileInputStream("authoring/resources/no_image.png");  //closed
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(new Image(fileInputStream, 50, 50, false, false));  //closed
        Utility.closeInputStream(fileInputStream);  //closed
        mySelectedImagePane.getChildren().add(imageView);
        myGridPane.add(mySelectedImagePane, 1, myGridPane.getRowCount()-1);

    }

    private void openImageAssetManager(){
        ImageManager assetManager = new ImageManager();
        assetManager.showAndWait();
        if(assetManager.getImageView() != null){
            ImageView imageView = assetManager.getImageView();
            mySelectedImagePane.getChildren().clear();
            mySelectedImagePane.getChildren().add(imageView);
            mySelectedImageName = assetManager.getAssetName();
        }
    }

    private void createButtonPane() {
        String[] buttons = WINDOW_RESOURCES.getString("Buttons").split(",");
        List<Button> buttonList = new ArrayList<>();
        for(String s : buttons){
            String[] info = s.split(" ");
            buttonList.add(Utility.makeButton(this, info[1], info[0]));
        }
        myButtonNode = Utility.createButtonBar(buttonList);
    }

    private void handleCloseButton(){
        this.close();
    }

    private void handleCreateButton(){
        if(checkValidInputs()){
            String typeLabel = myTextField.getText();
            String defaultTypeName = (String) myDefaultTypeNameComboBox.getValue();

            Entity entity = myDefaultTypesFactory.createEntity(defaultTypeName);
            entity.addComponent(new NameComponent(typeLabel));
            entity.addComponent(new SpriteComponent(mySelectedImageName));
            myUserCreatedEntity = entity;
            this.close();
        }
        else{
            String[] errorInfo = WINDOW_RESOURCES.getString("InvalidInputs").split(",");
            ErrorBox errorBox = new ErrorBox(errorInfo[0], errorInfo[1]);
            errorBox.display();
        }
    }

    private boolean checkValidInputs() {
        Set<Boolean> checkerSet = new HashSet<>();
        checkerSet.add((!mySelectedImageName.equals("")));
        System.out.println("Image good: " + (mySelectedImageName != null));
        checkerSet.add(!myTextField.getText().isEmpty());
        System.out.println("TextField Not Empty: " + (!myTextField.getText().isEmpty()));
        checkerSet.add(myTextField.getText().matches(SYNTAX_RESOURCES.getString("LABEL")));
        System.out.println("TextField good for label: " + (myTextField.getText().matches(SYNTAX_RESOURCES.getString("LABEL"))));
        return !checkerSet.contains(false);
    }


    private void createAndAddBasedOnDropDown(String isBasedOn) {
        myDefaultTypeNameComboBox.setPrefWidth(INPUT_WIDTH);
        myDefaultTypeNameComboBox.setValue(isBasedOn);
        myGridPane.add(myDefaultTypeNameComboBox, 1, myGridPane.getRowCount()-1);
    }

    private void populateDefaultTypeComboBox(String category) {
        List<String> dropDownContent = myDefaultTypesFactory.getDefaultNames(category);
        myDefaultTypeNameComboBox.getItems().clear();
        myDefaultTypeNameComboBox.getItems().addAll(dropDownContent);
    }

    private void createAndAddLabel(String s) {
        Label label = new Label(s);
        label.setAlignment(Pos.CENTER);
        int numRows = myGridPane.getRowCount();
        myGridPane.add(label, 0, numRows+1);
    }

    private void createAndAddTextField(){
        myTextField.setPrefWidth(INPUT_WIDTH);
        myGridPane.add(myTextField, 1, myGridPane.getRowCount()-1);
    }

    private void createAndAddCategoryDropDown(String initialTypeOf){
        List<String> listOfCategories = myDefaultTypesFactory.getCategories();
        myCategoryComboBox.getItems().addAll(listOfCategories);
        myCategoryComboBox.setPrefWidth(INPUT_WIDTH);
        myGridPane.add(myCategoryComboBox, 1, myGridPane.getRowCount()-1);
        myCategoryComboBox.valueProperty().addListener((observableValue, o, t1) -> populateDefaultTypeComboBox((String) t1));
        myCategoryComboBox.setValue(initialTypeOf);
    }
    private void initializeAndDisplayStage() {
        this.setWidth(STAGE_WIDTH);
        this.setHeight(STAGE_HEIGHT);
        TitledPane titledPane = createAndFormatTitledPane();
        Scene scene = new Scene(titledPane);
        scene.getStylesheets().add(STYLE_SHEET);
        this.setScene(scene);
    }

    private TitledPane createAndFormatTitledPane() {
        TitledPane titledPane = new TitledPane();
        titledPane.setText(WINDOW_RESOURCES.getString("Title"));
        VBox contents = new VBox();
        contents.getChildren().addAll(myGridPane, myButtonNode);
        contents.setSpacing(10.0);
        titledPane.setContent(contents);
        titledPane.setCollapsible(false);
        return titledPane;
    }
}

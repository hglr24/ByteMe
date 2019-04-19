package ui.windows;

import engine.external.Entity;
import engine.external.component.NameComponent;
import engine.external.component.SpriteComponent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ui.DefaultTypesFactory;
import ui.ErrorBox;
import ui.Utility;

import java.util.*;

/**
 * @author Carrie Hunner
 * This class creates a pop-up window that allows the
 * user to create a new object type
 */
public class CreateNewTypeWindow extends Stage {
    private GridPane myGridPane;
    private static final ResourceBundle SYNTAX_RESOURCES = ResourceBundle.getBundle("property_syntax");
    private static final ResourceBundle myWindowResources = ResourceBundle.getBundle("new_object_window");
    private static final ResourceBundle myTypeResources = ResourceBundle.getBundle("default_entity_type");
    private ComboBox myTypeOfComboBox;
    private ComboBox myBasedOnComboBox;
    private TextField myTextField;
    private Node myButtonNode;
    private Pane mySelectedImagePane;
    private DefaultTypesFactory myDefaultTypesFactory;
    private String mySelectedImageName;
    private Entity myUserCreatedEntity;

    private static final int STAGE_HEIGHT = 325;
    private static final int STAGE_WIDTH = 400;
    private static final int GRIDPANE_GAP = 10;
    private static final int INPUT_WIDTH = 100;
    private static final int PICTURE_SIZE = 50;
    private static final String STYLE_SHEET = "default.css";

    /**
     * Creates and displays a stage
     * @param isANewTypeOf the String corresponding to a category that the new object is a typeOf
     * @param isBasedOn the default Entity type it is based in
     */
    public CreateNewTypeWindow(String isANewTypeOf, String isBasedOn){
        mySelectedImageName = null;
        initializeGridPane();
        initializeVariables();
        createContent(isANewTypeOf, isBasedOn);
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
     * Gets the typeOf
     * Called by UserCreatedTypesPane to determine what category to display
     * the new type in
     * The 0th position contains the category (isTypeOf)
     * and the 1st position contains what it is based on
     * @return String of the category
     */
    public String[] getCategoryInfo(){
        String[] result = new String[2];
        result[0] = myTypeOfComboBox.getValue().toString();
        result[1] = myBasedOnComboBox.getValue().toString();
        return result;
    }


    private void initializeVariables() {
        myBasedOnComboBox = new ComboBox();
        myTypeOfComboBox = new ComboBox();
        myTextField = new TextField();
        myButtonNode = new HBox();
        mySelectedImagePane = new Pane();
        myDefaultTypesFactory = new DefaultTypesFactory();
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
        createAndAddLabel(myWindowResources.getString("Label1"));
        createAndAddTextField();
        createAndAddLabel(myWindowResources.getString("Label2"));
        createAndAddTypeOfOnDropDown(isANewTypeOf);
        createAndAddLabel(myWindowResources.getString("Label3"));
        createAndAddBasedOnDropDown(isBasedOn);
        createImageSelectionPane();
        createButtonPane();
    }

    private void createImageSelectionPane() {
        String[] buttonResources = myWindowResources.getString("AssetButton").split(",");
        Button button = Utility.makeButton(this, buttonResources[1], buttonResources[0]);
        myGridPane.add(button, 0, myGridPane.getRowCount());

        Rectangle imagePlaceholder = new Rectangle(PICTURE_SIZE, PICTURE_SIZE);
        mySelectedImagePane.getChildren().add(imagePlaceholder);
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
        String[] buttons = myWindowResources.getString("Buttons").split(",");
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
            String typeOf = (String) myTypeOfComboBox.getValue();
            String basedOn = (String) myBasedOnComboBox.getValue();

            Entity entity = myDefaultTypesFactory.getDefaultEntity(typeOf, basedOn);
            entity.addComponent(new NameComponent(typeLabel));
            entity.addComponent(new SpriteComponent(mySelectedImageName));
            myUserCreatedEntity = entity;
            this.close();
        }
        else{
            String[] errorInfo = myWindowResources.getString("InvalidInputs").split(",");
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
        myBasedOnComboBox.setPrefWidth(INPUT_WIDTH);
        myBasedOnComboBox.setValue(isBasedOn);
        myGridPane.add(myBasedOnComboBox, 1, myGridPane.getRowCount()-1);
    }

    private void populateBasedOnDropDown(String s) {
        List<String> dropDownContent = myDefaultTypesFactory.getTypes(s);
        myBasedOnComboBox.getItems().clear();
        myBasedOnComboBox.getItems().addAll(dropDownContent);
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

    private void createAndAddTypeOfOnDropDown(String initialTypeOf){
        String[] types = myTypeResources.getString("Tabs").split(",");
        myTypeOfComboBox.getItems().addAll(Arrays.asList(types));
        myTypeOfComboBox.setPrefWidth(INPUT_WIDTH);
        myGridPane.add(myTypeOfComboBox, 1, myGridPane.getRowCount()-1);
        myTypeOfComboBox.valueProperty().addListener((observableValue, o, t1) -> populateBasedOnDropDown((String) t1));
        myTypeOfComboBox.setValue(initialTypeOf);
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
        titledPane.setText(myWindowResources.getString("Title"));
        VBox contents = new VBox();
        contents.getChildren().addAll(myGridPane, myButtonNode);
        contents.setSpacing(10.0);
        titledPane.setContent(contents);
        titledPane.setCollapsible(false);
        return titledPane;
    }
}

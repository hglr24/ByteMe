package ui.panes;

import engine.external.Entity;
import engine.external.component.NameComponent;
import engine.external.component.SpriteComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
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
import ui.Utility;
import ui.manager.AssetManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class CreateNewTypeWindow extends Stage {
    private GridPane myGridPane;
    private ResourceBundle myWindowResources;
    private ResourceBundle myTypeResources;
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
    private static final String WINDOW_RESOURCES = "new_object_window";
    private static final String TYPE_RESOURCES = "default_entity_type";

    public CreateNewTypeWindow(String isANewTypeOf, String isBasedOn){
        initializeGridPane();
        initializeVariables();
        myWindowResources = ResourceBundle.getBundle(WINDOW_RESOURCES);
        myTypeResources = ResourceBundle.getBundle(TYPE_RESOURCES);
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
        createAssetManagerButtonPane();
        createButtonPane();
    }

    private void createAssetManagerButtonPane() {
        String[] buttonResources = myWindowResources.getString("AssetButton").split(",");
        Button button = Utility.makeButton(this, buttonResources[1], buttonResources[0]);
        myGridPane.add(button, 0, myGridPane.getRowCount());

        Rectangle imagePlaceholder = new Rectangle(PICTURE_SIZE, PICTURE_SIZE);
        mySelectedImagePane.getChildren().add(imagePlaceholder);
        myGridPane.add(mySelectedImagePane, 1, myGridPane.getRowCount()-1);

    }

    private void openAssetManager(){
        AssetManager assetManager = new AssetManager();
        assetManager.showAndWait();
        ImageView imageView = assetManager.getImageView();
        mySelectedImagePane.getChildren().clear();
        mySelectedImagePane.getChildren().add(imageView);
        mySelectedImageName = assetManager.getImageName();
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
        String typeLabel = myTextField.getText();
        String typeOf = (String) myTypeOfComboBox.getValue();
        String basedOn = (String) myBasedOnComboBox.getValue();

        Entity entity = myDefaultTypesFactory.getDefaultEntity(typeOf, basedOn);
        entity.addComponent(new NameComponent(typeLabel));
        entity.addComponent(new SpriteComponent(mySelectedImageName));
        myUserCreatedEntity = entity;
        this.close();
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
        myTypeOfComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                populateBasedOnDropDown((String) t1);
            }
        });
        myTypeOfComboBox.setValue(initialTypeOf);
    }
    private void initializeAndDisplayStage() {
        this.setWidth(STAGE_WIDTH);
        this.setHeight(STAGE_HEIGHT);
        TitledPane titledPane = new TitledPane();
        titledPane.setText(myWindowResources.getString("Title"));
        VBox contents = new VBox();
        contents.getChildren().addAll(myGridPane, myButtonNode);
        contents.setSpacing(10.0);
        titledPane.setContent(contents);
        titledPane.setCollapsible(false);
        Scene scene = new Scene(titledPane);
        scene.getStylesheets().add(STYLE_SHEET);
        this.setScene(scene);
    }
}

package ui.panes;

import engine.external.Entity;
import engine.external.component.SpriteComponent;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.EntityField;
import ui.LevelField;
import ui.Propertable;
import ui.Utility;
import ui.manager.ObjectManager;

import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Viewer extends ScrollPane {
    private StackPane myStackPane;
    private static final int CELL_SIZE = 50;
    private Double myRoomHeight;
    private Double myRoomWidth;
    private boolean isDragOnView;
    private ObjectManager myObjectManager;
    private AuthoringLevel myAuthoringLevel;
    private AuthoringEntity myDraggedAuthoringEntity;
    private ObjectProperty<Propertable> mySelectedEntityProperty;
    private UserCreatedTypesPane myUserCreatedPane;
    private static final ResourceBundle myGeneralResources = ResourceBundle.getBundle("authoring_general");
    private Pane myLinesPane;
    private String myBackgroundFileName;
    private static final ResourceBundle myResources = ResourceBundle.getBundle("viewer");
    private static final String SHEET = "viewer-scroll-pane";


    /**
     *
     * @param authoringLevel
     * @param userCreatedTypesPane
     * @param objectProperty
     */
    public Viewer(AuthoringLevel authoringLevel, UserCreatedTypesPane userCreatedTypesPane,
                  ObjectProperty objectProperty, ObjectManager objectManager){
        myObjectManager = objectManager;
        myUserCreatedPane = userCreatedTypesPane;
        mySelectedEntityProperty = objectProperty;
        myAuthoringLevel = authoringLevel;
        initializeAndFormatVariables();
        myAuthoringLevel.getPropertyMap().addListener((MapChangeListener<? super Enum, ? super String>) change ->
                handleChange(change));
        setupAcceptDragEvents();
        setupDragDropped();
        setRoomSize();
        updateGridLines();
    }

    private void initializeAndFormatVariables() {
        myStackPane = new StackPane();
        myLinesPane = new Pane();
        myBackgroundFileName = null;
        myStackPane.getChildren().addListener((ListChangeListener<Node>) change -> updateZField());
        myStackPane.getChildren().add(myLinesPane);
        System.out.println("List size with just lines: " + myStackPane.getChildren().size());
        myStackPane.setAlignment(Pos.TOP_LEFT);
        this.setContent(myStackPane);
        this.getStyleClass().add(SHEET);
    }

    private void updateZField() {
        int objectCount = 0;
        for(Node node : myStackPane.getChildren()){
            if(node instanceof ImageWithEntity){
                System.out.println("*************");
                AuthoringEntity authoringEntity = ((ImageWithEntity) node).getAuthoringEntity();
                authoringEntity.getPropertyMap().put(EntityField.Z, Integer.toString(objectCount));
                System.out.println("Label: " + authoringEntity.getPropertyMap().get(EntityField.LABEL) + "\t Index: " + authoringEntity.getPropertyMap().get(EntityField.Z));
                System.out.println("****************");
                objectCount++;
            }
        }
    }


    private void handleChange(MapChangeListener.Change<? extends Enum,? extends String> change) {
        if(change.wasAdded() && myResources.containsKey(change.getKey().toString())){
            Utility.makeAndCallMethod(myResources, change, this);
        }
    }

    private void updateWidth(String width){
        myRoomWidth = Double.parseDouble(width);
        updateGridLines();
        updateBackground(myBackgroundFileName);
        myStackPane.setMinWidth(myRoomWidth);
        myStackPane.setMaxWidth(myRoomWidth);
    }

    private void updateHeight(String height){
        myRoomHeight = Double.parseDouble(height);
        updateGridLines();
        updateBackground(myBackgroundFileName);
        myStackPane.setMinHeight(myRoomHeight);
        myStackPane.setMaxHeight(myRoomHeight);
    }

    private void updateBackground(String filename){
        if(filename != null){
            String filepath = myGeneralResources.getString("images_filepath") + filename;
            FileInputStream fileInputStream = Utility.makeFileInputStream(filepath);
            Image image = new Image(fileInputStream, myRoomWidth, myRoomHeight, false, false);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
            myStackPane.setBackground(new Background(backgroundImage));
            System.out.println("Size with background: " + myStackPane.getChildren().size());
            myBackgroundFileName = filename;
        }
    }

    private void setupAcceptDragEvents() {
        myStackPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
        });
    }

    private void setupDragDropped() {

        myStackPane.setOnDragDropped(dragEvent -> {
            AuthoringEntity authoringEntity;
            if(isDragOnView){
                authoringEntity = myDraggedAuthoringEntity;
                isDragOnView = false;
            }
            else{
                Entity entity = myUserCreatedPane.getDraggedEntity();
                authoringEntity = myUserCreatedPane.getDraggedAuthoringEntity();
                String imageName = (String) entity.getComponent(SpriteComponent.class).getValue();
                authoringEntity = new AuthoringEntity(authoringEntity, entity);
                authoringEntity.getPropertyMap().put(EntityField.IMAGE, imageName);
                addImage(Utility.createImageWithEntity(authoringEntity));
            }
            authoringEntity.getPropertyMap().put(EntityField.X, "" + snapToGrid(dragEvent.getX()));
            authoringEntity.getPropertyMap().put(EntityField.Y, "" + snapToGrid(dragEvent.getY()));
            mySelectedEntityProperty.setValue(authoringEntity);
        });
    }

    /**
     * Snaps the point to the closest gridline
     * @param value int to be snapped
     * @return int that is snapped
     */
    private double snapToGrid(double value){
        double valueRemainder = value % CELL_SIZE;
        double result;
        if(valueRemainder >= CELL_SIZE/2){
            result = value + CELL_SIZE - valueRemainder;
        }
        else{
            result = value - valueRemainder;
        }
        return result;

    }

    private void addImage(ImageWithEntity imageView){
        applyLeftClickHandler(imageView);
        applyDragHandler(imageView);
        applyRightClickHandler(imageView);
        myStackPane.getChildren().add(imageView);
    }

    private void applyRightClickHandler(ImageWithEntity imageView) {
        imageView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                System.out.println("RIGHT CLICK");
                ListView listView = new ListView();
                Label label = new Label("Bring To Front");

                label.setOnMousePressed(mouseEvent1 -> {
                    imageView.toFront();
                });

                Label label2 = new Label(("Send to Back"));
                listView.getItems().add(label2);
                label2.setOnMousePressed(mouseEvent12 -> {
                    imageView.toBack();
                });

                Label label3 = new Label("Delete");
                listView.getItems().add(label3);
                label3.setOnMousePressed(mouseEvent13 -> {
                    myStackPane.getChildren().remove(imageView);
                    myObjectManager.removeEntityInstance(imageView.getAuthoringEntity());
                });

                listView.getItems().add(label);
                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setContent(listView);
                scrollPane.getStyleClass().add(SHEET);
                Scene scene = new Scene(scrollPane, 125, 100);
                scene.getStylesheets().add("default.css");
                scrollPane.getStyleClass().add(".object-layering-window");
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setX(mouseEvent.getScreenX());
                stage.setY(mouseEvent.getScreenY());
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
                stage.setAlwaysOnTop(true);
                stage.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent14 -> {
                    System.out.println("mouse click detected!");
                    stage.close();
                });
                stage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if(keyEvent.getCode() == KeyCode.ESCAPE){
                            stage.close();
                        }
                    }
                });
            }
        });
    }

    private void applyDragHandler(ImageWithEntity imageView) {
        imageView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Utility.setupDragAndDropImage(imageView);
                isDragOnView = true;
                myDraggedAuthoringEntity = imageView.getAuthoringEntity();
            }
        });
    }

    private void applyLeftClickHandler(ImageWithEntity imageView) {
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mySelectedEntityProperty.setValue(imageView.getAuthoringEntity());
            }
        });
    }

    private void setRoomSize(){
        myRoomHeight = Double.parseDouble(myAuthoringLevel.getPropertyMap().get(LevelField.HEIGHT));
        myRoomWidth = Double.parseDouble(myAuthoringLevel.getPropertyMap().get(LevelField.WIDTH));
        this.setPrefHeight(myRoomHeight);
        this.setPrefWidth(myRoomWidth);
        myStackPane.setMinWidth(myRoomWidth);
        myStackPane.setMinHeight(myRoomHeight);
    }

    private void updateGridLines(){
        myStackPane.getChildren().remove(myLinesPane);
        myLinesPane.getChildren().clear();
        myLinesPane.setMaxSize(myRoomWidth, myRoomHeight);
        myLinesPane.setMinSize(myRoomWidth, myRoomHeight);
        addHorizontalLines();
        addVerticalLines();
        myStackPane.getChildren().add(myLinesPane);
        myLinesPane.toBack();
    }

    private void addHorizontalLines() {
        int x1 = 0;
        for(int k = 0; k < myRoomHeight/CELL_SIZE; k++){
            int y = k * CELL_SIZE;
            Line tempLine = new Line(x1, y, myRoomWidth, y);
            myLinesPane.getChildren().add(tempLine);
        }
    }

    private void addVerticalLines(){
        int y1 = 0;
        for(int k = 0; k < myRoomWidth/CELL_SIZE; k++){
            int x = k * CELL_SIZE;
            Line tempLine = new Line(x, y1, x, myRoomHeight);
            myLinesPane.getChildren().add(tempLine);
        }
    }

}

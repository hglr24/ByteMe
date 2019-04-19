package ui.panes;

import engine.external.Entity;
import engine.external.component.SpriteComponent;
import javafx.beans.property.ObjectProperty;
import javafx.collections.MapChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import ui.*;
import ui.manager.ObjectManager;

import java.io.FileInputStream;
import java.util.ResourceBundle;


public class Viewer extends ScrollPane {
    private StackPane myStackPane;
    private static final int CELL_SIZE = 50;
    private Double myRoomHeight;
    private Double myRoomWidth;
    private boolean isDragOnView;
    private ObjectManager myObjectManager;
    private ObjectProperty<Propertable> myAuthoringLevel;
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
    public Viewer(ObjectProperty<Propertable> authoringLevel, UserCreatedTypesPane userCreatedTypesPane, ObjectProperty objectProperty, ObjectManager objectManager){
        myObjectManager = objectManager;
        myUserCreatedPane = userCreatedTypesPane;
        mySelectedEntityProperty = objectProperty;
        myAuthoringLevel = authoringLevel;
        initializeAndFormatVariables();
        myAuthoringLevel.getValue().getPropertyMap().addListener((MapChangeListener<? super Enum, ? super String>) change ->
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
        myStackPane.getChildren().add(myLinesPane);
        myStackPane.setAlignment(Pos.TOP_LEFT);
        this.setContent(myStackPane);
        this.getStyleClass().add(SHEET);
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
        myStackPane.getChildren().add(imageView);
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
        myRoomHeight = Double.parseDouble(myAuthoringLevel.getValue().getPropertyMap().get(LevelField.HEIGHT));
        myRoomWidth = Double.parseDouble(myAuthoringLevel.getValue().getPropertyMap().get(LevelField.WIDTH));
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

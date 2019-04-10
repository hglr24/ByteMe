package ui.panes;

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

import java.io.FileInputStream;
import java.util.ResourceBundle;


public class Viewer extends ScrollPane {
    private StackPane myStackPane;
    private static final int CELL_SIZE = 50;
    private boolean isDragOnView;
    private ObjectProperty<Propertable> myAuthoringLevel;
    private AuthoringEntity myDraggedAuthoringEntity;
    private ObjectProperty<Propertable>  mySelectedEntity;
    private UserCreatedTypesPane myUserCreatedPane;
    private ResourceBundle myGeneralResources;
    private Pane myLinesPane;
    private String myBackgroundFileName;
    private static final ResourceBundle myResources = ResourceBundle.getBundle("viewer");


    /**
     *
     * @param authoringLevel
     * @param userCreatedTypesPane
     * @param objectProperty
     */
    public Viewer(ObjectProperty<Propertable> authoringLevel, UserCreatedTypesPane userCreatedTypesPane, ObjectProperty objectProperty){
        myStackPane = new StackPane();
        myLinesPane = new Pane();
        myBackgroundFileName = null;
        myStackPane.getChildren().add(myLinesPane);
        myUserCreatedPane = userCreatedTypesPane;
        mySelectedEntity = objectProperty;
        myAuthoringLevel = authoringLevel;
        myAuthoringLevel.getValue().getPropertyMap().addListener((MapChangeListener<? super Enum, ? super String>) change ->
                handleChange(change));
        myStackPane.setAlignment(Pos.TOP_LEFT);
        myGeneralResources = ResourceBundle.getBundle("authoring_general");
        setupAcceptDragEvents();
        setupDragDropped();
        setRoomSize();
        updateGridLines();
        this.setContent(myStackPane);
        this.getStyleClass().add("viewer-scroll-pane");
    }


    private void handleChange(MapChangeListener.Change<? extends Enum,? extends String> change) {
        if(change.wasAdded() && myResources.containsKey(change.getKey().toString())){
            Utility.makeAndCallMethod(myResources, change, this);
        }
        if(change.getKey().equals(LevelField.BACKGROUND)){
            System.out.println("Background change triggered");
        }
    }

    private void updateWidth(String width){
        Double widthDouble = Double.parseDouble(width);
        updateGridLines();
        updateBackground(myBackgroundFileName);
        myStackPane.setMinWidth(widthDouble);
        myStackPane.setMaxWidth(widthDouble);
    }

    private void updateHeight(String height){
        Double heightDouble = Double.parseDouble(height);
        updateGridLines();
        updateBackground(myBackgroundFileName);
        myStackPane.setMinHeight(heightDouble);
        myStackPane.setMaxHeight(heightDouble);
    }

    private void updateBackground(String filename){
        if(filename != null){
            System.out.println("Trying to update background");
            String filepath = myGeneralResources.getString("images_filepath") + filename;
            FileInputStream fileInputStream = Utility.makeFileInputStream(filepath);
            Double roomHeight = this.getPrefHeight();
            Double roomWidth = this.getPrefWidth();
            Image image = new Image(fileInputStream, roomWidth, roomHeight, false, false);
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

        myStackPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                AuthoringEntity authoringEntity;
                if(isDragOnView){
                    authoringEntity = myDraggedAuthoringEntity;
                    isDragOnView = false;
                }
                else{
                    authoringEntity = myUserCreatedPane.getDraggedAuthoringEntity();
                    addImage(Utility.createImageWithEntity(authoringEntity));
                }
                authoringEntity.getPropertyMap().put(EntityField.X, "" + snapToGrid(dragEvent.getX()));
                authoringEntity.getPropertyMap().put(EntityField.Y, "" + snapToGrid(dragEvent.getY()));
                mySelectedEntity.setValue(authoringEntity);
            }
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
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mySelectedEntity.setValue(imageView.getAuthoringEntity());
            }
        });
        imageView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Utility.setupDragAndDropImage(imageView);
                isDragOnView = true;
                myDraggedAuthoringEntity = imageView.getAuthoringEntity();
            }
        });
        myStackPane.getChildren().add(imageView);
    }

    private void setRoomSize(){
        Double height = Double.parseDouble(myAuthoringLevel.getValue().getPropertyMap().get(LevelField.HEIGHT));
        Double width = Double.parseDouble(myAuthoringLevel.getValue().getPropertyMap().get(LevelField.WIDTH));
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        myStackPane.setMinWidth(width);
        myStackPane.setMinHeight(height);
    }

    private void updateGridLines(){
        myStackPane.getChildren().remove(myLinesPane);
        myLinesPane.getChildren().clear();
        int height = (int) Math.round(Double.parseDouble(myAuthoringLevel.getValue().getPropertyMap().get(LevelField.HEIGHT)));
        int width = (int) Math.round(Double.parseDouble(myAuthoringLevel.getValue().getPropertyMap().get(LevelField.WIDTH)));
        myLinesPane.setPrefWidth(width);
        myLinesPane.setPrefHeight(height);
        addHorizontalLines(height, width);
        addVerticalLines(height, width);
        myStackPane.getChildren().add(myLinesPane);
    }

    private void addHorizontalLines(int height, int width) {
        int x1 = 0;
        for(int k = 0; k < height/CELL_SIZE; k++){
            int y = k * CELL_SIZE;
            Line tempLine = new Line(x1, y, width, y);
            myLinesPane.getChildren().add(tempLine);
        }
    }

    private void addVerticalLines(int height, int width){
        int y1 = 0;
        int y2 = height;
        for(int k = 0; k < width/CELL_SIZE; k++){
            int x = k * CELL_SIZE;
            Line tempLine = new Line(x, y1, x, y2);
            myLinesPane.getChildren().add(tempLine);
        }
    }

}

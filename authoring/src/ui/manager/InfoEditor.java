package ui.manager;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import data.external.GameCenterData;
import ui.ErrorBox;
import ui.Utility;
import ui.windows.ImageManager;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class InfoEditor extends Stage {

    private GameCenterData myData;
    private TextField myNameField;
    private TextField myDescField;
    private TextField myFolderField;
    private ImageView myImage;
    private String myNewImage;

    private static final int ICON_SIZE = 60;
    private static final String TITLE_FIELD = "Title";
    private static final String FOLDER_FIELD = "Folder";
    private static final String GAME_DATA_SYNTAX = "game_info_syntax";
    private static final String NO_IMAGE_ICON = "no_image.png";
    private static final List<String> INFO_LABELS = Arrays.asList("Game Title", "Description", "Output Directory", "Image");

    public InfoEditor(GameCenterData data) {
        this.setResizable(false);
        myData = data;
        createDialog();
    }

    private void createDialog() {
        Label header = new Label("Edit Game Info");

        Button okButton = Utility.makeButton(this, "applyAndClose", "Ok");
        Button cancelButton = Utility.makeButton(this, "cancel", "Cancel");

        this.setScene(Utility.createDialogPane(header, createContent(), Arrays.asList(okButton, cancelButton)));
    }

    private Node createContent() {
        GridPane contentBox = new GridPane();
        contentBox.getStyleClass().add("info-editor");

        createTextFields(contentBox);
        createImageView(contentBox);

        makeLabels(contentBox);
        return contentBox;
    }

    private void createTextFields(GridPane contentBox) {
        myNameField = new TextField();
        myNameField.textProperty().setValue(myData.getTitle());
        contentBox.add(myNameField, 1, 0);

        myDescField = new TextField();
        myDescField.textProperty().setValue(myData.getDescription());
        contentBox.add(myDescField, 1, 1);

        myFolderField = new TextField();
        myFolderField.textProperty().setValue(myData.getFolderName());
        contentBox.add(myFolderField, 1, 2);
    }

    private void createImageView(GridPane contentBox) {
        try {
            myImage = new ImageView(myData.getImageLocation());
        } catch (Exception e) {
            myImage = new ImageView(NO_IMAGE_ICON);
        }
        myImage.setFitWidth(ICON_SIZE);
        myImage.setFitHeight(ICON_SIZE);
        myImage.setOnMouseClicked(event -> openAssetManager());
        contentBox.add(myImage, 1, 3);
    }

    private void makeLabels(GridPane gridPane) {
        for (int i = 0; i < INFO_LABELS.size(); i++)
            gridPane.add(new Label(INFO_LABELS.get(i)), 0, i);
    }

    private void openAssetManager() {
        ImageManager imageManager = new ImageManager();
        imageManager.showAndWait();
        if (imageManager.getAssetName() != null) {
            myImage.setImage(new Image(imageManager.getAssetName()));
            myNewImage = imageManager.getAssetName();
        }
    }

    @SuppressWarnings("unused")
    private void applyAndClose() {
        boolean invalid = false;
        ResourceBundle bundle = ResourceBundle.getBundle(GAME_DATA_SYNTAX);
        if (myNameField.getText().matches(bundle.getString(TITLE_FIELD)))
            myData.setTitle(myNameField.getText());
        else
            invalid = true;
        if (myFolderField.getText().matches(bundle.getString(FOLDER_FIELD)))
            myData.setFolderName(myFolderField.getText());
        else
            invalid = true;
        if (invalid) {
            ErrorBox errorBox = new ErrorBox("Variable Error", "Invalid variable, refer to documentation for syntax");
            errorBox.display();
        } else {
            myData.setDescription(myDescField.getText());
            myData.setImageLocation(myNewImage);
            this.close();
        }
    }

    @SuppressWarnings("unused")
    private void cancel() {
        this.close();
    }
}

package ui.manager;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupManager extends Stage {

    private ObjectManager myObjectManager;
    private ListView<String> myListView;
    private static final String DEFAULT_STYLESHEET = "default.css";

    public GroupManager(ObjectManager objectManager) {
        this.setResizable(false);
        this.setTitle("Group Manager");
        myObjectManager = objectManager;
        myListView = new ListView<>();
        ScrollPane scrollpane = new ScrollPane(createContent());
        BorderPane bp = new BorderPane();
        bp.getStyleClass().add("group-manager");
        bp.setCenter(new HBox(scrollpane));

        VBox instructions = new VBox();
        Label mainLabel = new Label("Add or Remove a Group");
        Label subLabel = new Label("Double-click a Group to rename");
        instructions.getChildren().addAll(mainLabel, subLabel);
        bp.setTop(instructions);

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> newGroupPrompt());
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(event -> removeLabel());
        Button okButton = new Button("Close");
        okButton.setOnAction(event -> this.close());
        bp.setBottom(createButtonBar(new ArrayList<>(Arrays.asList(addButton, removeButton, okButton))));

        Scene scene = new Scene(bp);
        scene.getStylesheets().add(DEFAULT_STYLESHEET);
        bp.getTop().getStyleClass().add("top-pane");
        bp.getCenter().getStyleClass().add("center-pane");
        subLabel.getStyleClass().add("sub-label");
        this.setScene(scene);
    }

    private HBox createContent() { //TODO clean up
        HBox contentBox = new HBox();
        contentBox.getChildren().add(myListView);
        myListView.setEditable(true);
        myListView.setCellFactory(TextFieldListCell.forListView());
        myListView.setItems(myObjectManager.getLabelManager().getLabels("Group"));
        myListView.setOnEditCommit(event -> editLabel(event));
        return contentBox;
    }

    private void addNewLabel(String newLabel) {
        myObjectManager.getLabelManager().addLabel("Group", newLabel);
    }

    private void editLabel(ListView.EditEvent<String> event) {
        String newVal = event.getNewValue();
        myListView.getItems().set(event.getIndex(), newVal);
    }

    private void removeLabel() {
        if (!myListView.getSelectionModel().getSelectedItems().isEmpty()) {
            String badLabel = myListView.getSelectionModel().getSelectedItems().get(0);
            myObjectManager.getLabelManager().removeLabel("Group", badLabel);
        }
    }

    private void newGroupPrompt() {
        BorderPane content = new BorderPane();
        Scene newGroup = new Scene(content);
        Stage prompt = new Stage();
        prompt.setScene(newGroup);
        prompt.setResizable(false);
        prompt.setTitle("New Group");

        HBox centerContent = new HBox();
        TextField newGroupField = new TextField();
        newGroupField.setPromptText("New Group Name...");
        centerContent.getChildren().add(newGroupField);
        content.setCenter(centerContent);
        content.getCenter().getStyleClass().add("center-pane");

        Button addButton = new Button("Add Group");
        addButton.setOnAction(event -> { addNewLabel(newGroupField.getText()); prompt.close();});
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> prompt.close());
        content.setBottom(createButtonBar(new ArrayList<>(Arrays.asList(addButton, cancelButton))));

        newGroup.getStylesheets().add(DEFAULT_STYLESHEET);
        prompt.showAndWait();
    }

    private HBox createButtonBar(List<Button> buttonList) {
        HBox rtn = new HBox();
        rtn.getChildren().addAll(buttonList);
        rtn.getStyleClass().add("buttons-bar");
        if (!buttonList.isEmpty())
            Platform.runLater(() -> buttonList.get(0).requestFocus());
        return rtn;
    }
}

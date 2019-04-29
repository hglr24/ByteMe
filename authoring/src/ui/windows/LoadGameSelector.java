package ui.windows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import ui.ErrorBox;
import ui.Utility;

import java.util.Arrays;
import java.util.List;

public class LoadGameSelector extends Stage {

    private ListView<String> myListView;
    private List<String> myGameOptions;
    private String mySelectedGame;

    public LoadGameSelector(List<String> gameOptions) {
        myListView = new ListView<>();
        myGameOptions = gameOptions;
        this.setResizable(false);
        createDialog();
    }

    private void createDialog() {
        Label header = new Label("Load Game from Database");

        Button openButton = Utility.makeButton(this, "open", "Open");
        Button cancelButton = Utility.makeButton(this, "cancel", "Cancel");

        this.setScene(Utility.createDialogPane(header, createContent(), Arrays.asList(openButton, cancelButton)));
    }

    private Node createContent() {
        ObservableList<String> listContents = FXCollections.observableList(myGameOptions);
        myListView.setItems(listContents);
        return new ScrollPane(myListView);
    }

    @SuppressWarnings("unused")
    private void open() {
        if (!myListView.getSelectionModel().getSelectedItems().isEmpty()) {
            mySelectedGame = myListView.getSelectionModel().getSelectedItems().get(0);
            this.close();
        }
        else {
            ErrorBox error = new ErrorBox("Load Error", "No game selected");
            error.display();
        }
    }

    @SuppressWarnings("unused")
    private void cancel() {
        this.close();
    }

    public String getSelectedGame() {
        return mySelectedGame;
    }

}

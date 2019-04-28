package frontend.popups;

import data.external.DataManager;
import data.external.GameCenterData;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public abstract class Popup {
    private static final String PANE_SELECTOR = "pane";
    private static final Color BACKGROUND_COLOR = Color.rgb(46, 43, 51);
    private static final String TITLE_SELECTOR = "titlefont";
    private static final String SUBTITLE_SELECTOR = "subtitlefont";
    private static final String GAME_PAGE_SELECTOR = "gamepage";
    private static final String DEFAULT_LANGUAGE = "languages/English";

    protected BorderPane myDisplay;
    protected DataManager myManager;
    protected GameCenterData myData;
    protected ResourceBundle myLanguageBundle;


    public Popup(GameCenterData data, DataManager manager) {
        myData = data;
    }

    public Popup(DataManager manager) {
        myManager = manager;
        myLanguageBundle = ResourceBundle.getBundle(DEFAULT_LANGUAGE);
        initializeDisplay();
        display();
    }



    protected void initializeDisplay() {
        myDisplay = new BorderPane();
        myDisplay.getStylesheets().add("center.css");
        myDisplay.getStyleClass().add(GAME_PAGE_SELECTOR);
        addHeader();
        addBody();
        addButtons();
    }

    protected void showScene(Pane pane, double width, double height) {
        pane.getStylesheets().add("center.css");
        pane.getStyleClass().add(PANE_SELECTOR);
        Scene scene = new Scene(pane, width, height, BACKGROUND_COLOR);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    protected void addTitleAndSubtitle(BorderPane pane, String title, String subtitle) {
        Text titleText = new Text(title);
        titleText.getStyleClass().add(TITLE_SELECTOR);
        Text subtitleText = new Text(subtitle);
        subtitleText.getStyleClass().add(SUBTITLE_SELECTOR);
        VBox text = new VBox(titleText, subtitleText);
        text.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(text, Pos.CENTER);
        pane.setTop(text);
    }

    protected abstract void addHeader();

    protected abstract void addBody();

    protected abstract void addButtons();

    protected abstract void display();

}

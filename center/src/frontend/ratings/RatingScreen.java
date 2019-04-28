package frontend.ratings;

import data.external.DataManager;
import data.external.GameCenterData;
import data.external.GameRating;
import frontend.Utilities;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class RatingScreen {
    private static final double RATING_WIDTH = 350;
    private static final double RATING_HEIGHT = 450;
    private static final String DEFAULT_LANGUAGE = "languages/English";
    private static final String BODY_SELECTOR = "bodyfont";
    private static final String TEXT_FIELD_SELECTOR = "commentbox";
    private static final String PADDING_SELECTOR = "ratingpadding";

    private BorderPane myDisplay;
    private ResourceBundle myLanguageBundle;
    private GameCenterData myData;
    private DataManager myManager;
    private TextArea myText;
    private StarBox myStars;

    public RatingScreen(GameCenterData data, DataManager manager) {
        myLanguageBundle = ResourceBundle.getBundle(DEFAULT_LANGUAGE);
        myData = data;
        myManager = manager;
        initializeDisplay();
        display();
    }

    public void addRatingButton(GameCenterData data) {
        try {
            myManager.addRating(new GameRating("DEFAULT", data.getTitle(), data.getAuthorName(), myStars.getCurrentNumberOfStars(), myText.getText()));
        } catch (SQLException e) {
            // todo: handle this
            System.out.println("Adding rating was unsuccessful");
        }
        ((Stage) myDisplay.getScene().getWindow()).close();
    }

    public void cancelButton(GameCenterData data) {
        ((Stage) myDisplay.getScene().getWindow()).close();
    }

    private void initializeDisplay() {
        myDisplay = new BorderPane();
        addTitle();
        addRatingInput();
        addButtons();
    }

    private void addRatingInput() {
        BorderPane ratingOptions = new BorderPane();
        addStars(ratingOptions);
        addCommentBox(ratingOptions);
        ratingOptions.getStyleClass().add(PADDING_SELECTOR);
        myDisplay.setCenter(ratingOptions);
        myDisplay.getStyleClass().add(PADDING_SELECTOR);
    }

    private void addButtons() {
        myDisplay.setBottom(Utilities.makeButtons(this, myData));
    }

    private void addStars(BorderPane pane) {
        BorderPane stars = new BorderPane();
        Text starTitle = new Text(Utilities.getValue(myLanguageBundle, "starTitle"));
        starTitle.getStyleClass().add(BODY_SELECTOR);
        BorderPane.setAlignment(starTitle, Pos.CENTER);
        stars.setTop(starTitle);
        myStars = new StarBox();
        BorderPane.setAlignment(myStars.getDisplay(), Pos.CENTER);
        stars.setCenter(myStars.getDisplay());
        stars.getStyleClass().add(PADDING_SELECTOR);
        pane.setTop(stars);
    }

    private void addCommentBox(BorderPane pane) {
        BorderPane comment = new BorderPane();
        Text commentTitle = new Text(Utilities.getValue(myLanguageBundle, "commentTitle"));
        commentTitle.getStyleClass().add(BODY_SELECTOR);
        BorderPane.setAlignment(commentTitle, Pos.CENTER);
        comment.setTop(commentTitle);
        myText = new TextArea();
        myText.getStyleClass().add(TEXT_FIELD_SELECTOR);
        comment.setCenter(myText);
        pane.setCenter(comment);
    }

    private void addTitle() {
        Utilities.addTitleAndSubtitle(myDisplay, Utilities.getValue(myLanguageBundle, "ratingTitle"), myData.getTitle());
    }

    private void display() {
        Utilities.showScene(myDisplay, RATING_WIDTH, RATING_HEIGHT);
    }

}

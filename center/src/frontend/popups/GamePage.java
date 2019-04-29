package frontend.popups;

import data.external.DataManager;
import data.external.GameCenterData;
import frontend.Utilities;
import frontend.ratings.RatingList;
import frontend.statistics.StatisticDisplay;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

public class GamePage extends Popup {
    private static final String BODY_SELECTOR = "bodyfont";
    private static final String SCROLLER_SELECTOR = "scroller";
    private static final String SUBTITLE_SELECTOR = "subtitlefont";
    private static final String PADDING_SELECTOR = "borderpadding";
    public static final double POPUP_WIDTH = 750;
    private static final double POPUP_HEIGHT = 500;
    private static final double IMAGE_SIZE = 500;
    private static final double WRAP_OFFSET = 20;
    private static final double SCROLL_OFFSET = 20;
    private static final double MAX_HEIGHT = 200;

    private String myCurrentUser;
    private GameCenterData myData;

    public GamePage(GameCenterData data, DataManager manager, String user) {
        super(manager);
        myData = data;
        myCurrentUser = user;
        initializeDisplay();
        display();
    }

    public void playGameButton(GameCenterData data) {
        Utilities.launchGameRunner(data.getTitle(), data.getAuthorName(), myCurrentUser);
    }

    public void rateGameButton(GameCenterData data) {
        new RatingScreen(data, myManager, myCurrentUser);
    }

    @Override
    protected void display() {
        showScene(myDisplay, POPUP_WIDTH, POPUP_HEIGHT);
    }

    @Override
    protected void addBody() {
        BorderPane contentPane = new BorderPane();
        Pane gamePreview = null;
        try {
            gamePreview = Utilities.getImagePane(myManager, myData.getImageLocation(), IMAGE_SIZE, MAX_HEIGHT);
        } catch (FileNotFoundException e) {
            // do nothing
        }
        gamePreview.setPadding(new Insets(20));
        contentPane.setTop(gamePreview);
        Text body = new Text(myData.getDescription());
        body.getStyleClass().add(BODY_SELECTOR);
        body.setWrappingWidth(POPUP_WIDTH - WRAP_OFFSET);
        contentPane.setCenter(body);
        addRatingsAndStatistics(contentPane);
        ScrollPane scroller = new ScrollPane(contentPane);
        scroller.getStylesheets().add("center.css");
        scroller.getStyleClass().add(SCROLLER_SELECTOR);
        scroller.setMaxHeight(POPUP_HEIGHT - SCROLL_OFFSET);
        myDisplay.setCenter(scroller);
    }

    private void addRatingsAndStatistics(BorderPane contentPane) {
        BorderPane extraPane = new BorderPane();
        addGameRatings(extraPane);
        addGameStatistics(extraPane);
        contentPane.setBottom(extraPane);
    }

    private void addGameStatistics(BorderPane extraPane) {
        BorderPane statisticsPane = new BorderPane();
        Text title = new Text(myData.getTitle() + Utilities.getValue(myLanguageBundle, "statisticsTitle"));
        title.getStyleClass().add(SUBTITLE_SELECTOR);
        BorderPane.setAlignment(title, Pos.CENTER);
        statisticsPane.setTop(title);
        statisticsPane.setCenter(new StatisticDisplay(myData, myManager).getDisplay());
        extraPane.setTop(statisticsPane);
    }

    private void addGameRatings(BorderPane extraPane) {
        BorderPane ratingPane = new BorderPane();
        Text title = new Text(myData.getTitle() + Utilities.getValue(myLanguageBundle, "ratingsTitle"));
        title.getStyleClass().add(SUBTITLE_SELECTOR);
        ratingPane.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        ratingPane.setCenter(new RatingList(myData, myManager).getDisplay());
        ratingPane.getStyleClass().add(PADDING_SELECTOR);
        extraPane.setCenter(ratingPane);
    }

    @Override
    protected void addHeader() {
        addTitleAndSubtitle(myDisplay, myData.getTitle(), myData.getAuthorName(), POPUP_WIDTH);
    }

    @Override
    protected void addButtons() {
        BorderPane buttonPane = new BorderPane();
        buttonPane.setCenter(Utilities.makeButtons(this, myData));
        myDisplay.setBottom(buttonPane);
    }

}

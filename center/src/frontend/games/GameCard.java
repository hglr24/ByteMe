/**
 * @Author Megan Phibbons
 * @Date April 2019
 * @Purpose Act as a display component for game objects. Contains title, description, image, and buttons in a neater package.
 * @Dependencies javafx, GameRunner, GameCenterData, Utilities, and java.io
 * @Uses: Used by GameList for displaying single games at a time.
 */

package frontend.games;

import data.external.DataManager;
import frontend.popups.GamePage;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import data.external.GameCenterData;
import frontend.Utilities;

import java.util.ResourceBundle;

public class GameCard {
    private static final int GAME_IMAGE_SIZE = 200;
    private static final int WRAP_OFFSET = 25;

    public static final double DISPLAY_WIDTH = 300;
    public static final double DISPLAY_HEIGHT = 350;
    private static final String DEFAULT_LANGUAGE_LOCATION = "languages/English";
    private static final String BACKGROUND_SELECTOR = "rectangle";
    private static final String TEXT_SELECTOR = "cardtext";
    private static final String TITLE_SELECTOR = "cardtitle";
    private static final String BODY_SELECTOR = "cardbody";
    private static final String FOREGROUND_SELECTOR = "cardpadding";
    private static final String CONTENT_SELECTOR = "contentpadding";
    private int myIndex;
    private ResourceBundle myLanguageBundle;
    private GameCenterData myGame;
    private Pane myDisplay;
    private DataManager myManager;

    /**
     * @purpose constructor that sets up parameters of the game and initializes the resource bundle used for text.
     * @param game the GameCenterData object that represents the contents of the GameCard
     * @param index the index that the game card is in a list. This is used for styling purposes
     */
    public GameCard(GameCenterData game, int index, DataManager manager) {
        myGame = game;
        myIndex = index % 2 + 1;
        myLanguageBundle = ResourceBundle.getBundle(DEFAULT_LANGUAGE_LOCATION);
        myManager = manager;
        initializeDisplay();
    }

    /**
     * @purpose share the display of the pane. This is used in GameList so that the contents of the GameCard can actually
     * be displayed.
     * @return the current display stored in GameCard.
     */
    public Pane getDisplay() {
        return myDisplay;
    }

    public void readMoreButton(GameCenterData data) {
        new GamePage(data, myManager);
    }

    public void playGameButton(GameCenterData data) {
        Utilities.launchGameRunner(data.getFolderName());
    }

    private void initializeDisplay() {
        BorderPane cardDisplay = new BorderPane();
        StackPane cardContents = fillCardContents();
        cardDisplay.setCenter(cardContents);
        myDisplay = cardDisplay;
    }

    private StackPane fillCardContents() {
        StackPane cardContents = new StackPane();
        addBackground(cardContents);
        addForeGround(cardContents);
        return cardContents;
    }

    private void addBackground(Pane pane) {
        BorderPane background = new BorderPane();
        Rectangle display = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        display.getStyleClass().add(BACKGROUND_SELECTOR + myIndex);
        background.setCenter(display);
        pane.getChildren().add(background);
    }

    private void addForeGround(Pane pane) {
        BorderPane foreground = new BorderPane();
        foreground.getStyleClass().add(FOREGROUND_SELECTOR);
        addTitleContent(foreground);
        addAuthor(foreground);
        addImageAndContent(foreground);
        addButtons(foreground);
        pane.getChildren().add(foreground);
    }

    private void addAuthor(BorderPane foreground) {

    }

    private void addButtons(BorderPane foreground) {
        BorderPane buttonPane = new BorderPane();
        buttonPane.setCenter(Utilities.makeButtons(this, myGame));
        foreground.setBottom(buttonPane);
    }

    private void addImageAndContent(BorderPane foreground) {
        BorderPane contentPane = new BorderPane();
        addImage(contentPane);
        Text imageDescription = new Text(myGame.getDescription());
        imageDescription.getStyleClass().add(BODY_SELECTOR);
        imageDescription.getStyleClass().add(TEXT_SELECTOR + myIndex);
        imageDescription.setWrappingWidth(DISPLAY_WIDTH - WRAP_OFFSET);
        contentPane.setCenter(imageDescription);
        contentPane.getStyleClass().add(CONTENT_SELECTOR);
        foreground.setCenter(contentPane);
    }

    private void addImage(BorderPane contentPane) {
        contentPane.setTop(Utilities.getImagePane(myManager, myGame.getImageLocation(), GAME_IMAGE_SIZE));
    }

    private void addTitleContent(BorderPane foreground) {
        Text title = new Text(myGame.getTitle());
        title.getStyleClass().add(TITLE_SELECTOR);
        title.getStyleClass().add(TEXT_SELECTOR + myIndex);
        BorderPane titlePane = new BorderPane();
        titlePane.setCenter(title);
        Text author = new Text(myGame.getAuthorName());
        author.getStyleClass().add(BODY_SELECTOR);
        author.getStyleClass().add(TEXT_SELECTOR + myIndex);
        BorderPane.setAlignment(author, Pos.CENTER);
        titlePane.setBottom(author);
        foreground.setTop(titlePane);
    }

}

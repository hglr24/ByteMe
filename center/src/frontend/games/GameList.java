/**
 * @Author Megan Phibbons
 * @Date April 2019
 * @Purpose List all of the games available in a user-friendly attractive way so that they can select a game they are
 * interested in.
 * @Dependencies DataManager, javafx, GameCenterData
 * @Uses: Used in GamePane for displaying games
 */

package frontend.games;

import data.external.DataManager;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import data.external.GameCenterData;

import java.util.List;

public class GameList {
    private Pane myDisplay;
    private static final double CARD_H_OFFSET = 50;
    private static final int NUM_CARDS_DISPLAYED = 3;
    private static final String GAME_LIST_SELECTOR = "cardlist";
    private static final String SCROLLER_SELECTOR = "scroller";
    private List<GameCenterData> myGames;

    /**
     * @purpose constructor, reads in the list of original GameData objects and saves it, then sets up the display of cards.
     */
    public GameList() {
        DataManager manager = new DataManager();
        myGames = manager.loadAllGameInfoObjects();
        initializeDisplay();
    }

    /**
     * @purpose give the display of the GameList to the GamePane
     * @return the current display attached to the GameList.
     */
    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        FlowPane gameList = new FlowPane();
        gameList.setAlignment(Pos.CENTER);
        gameList.getStyleClass().add(GAME_LIST_SELECTOR);
        gameList.setPrefWrapLength((GameCard.DISPLAY_WIDTH + CARD_H_OFFSET) * NUM_CARDS_DISPLAYED);
        int index = 0;
        for(GameCenterData game : myGames) {
            GameCard c = new GameCard(game, index);
            gameList.getChildren().add(c.getDisplay());
            index++;
        }
        ScrollPane scroller = new ScrollPane();
        scroller.getStyleClass().add(SCROLLER_SELECTOR);
        scroller.setContent(gameList);
        scroller.setMinHeight(GameCard.DISPLAY_HEIGHT * 2);
        BorderPane content = new BorderPane(scroller);
        myDisplay = content;
    }

}

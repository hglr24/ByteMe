/**
 * @Author Megan Phibbons
 * @Date April 2019
 * @Purpose This is the pane that encompasses all of the game data. For now, it only includes a title and list of games.
 * Moving forward, this will also have a sort button and an add new game button.
 * @Dependencies javafx and Utilities.
 * @Uses: Used in CenterMain to display the games
 */

package frontend.games;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import frontend.Utilities;

import java.util.ResourceBundle;

public class GamePane {
    public static final int GAME_PANE_TOP_PADDING = 10;
    public static final int GAME_PANE_PADDING = 100;
    private static final String SUBTITLE_SELECTOR = "subtitlefont";
    private Pane myDisplay;
    private ResourceBundle myResources;

    /**
     * @purpose initialize the languages resource bundle and set up the game display.
     */
    public GamePane() {
        myResources = ResourceBundle.getBundle("languages/English");
        initializeDisplay();
    }

    /**
     * @purpose give the display to CenterMain so that it can show everything relating to games.
     * @return the current display of the GamePane
     */
    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        BorderPane gamePane = new BorderPane();
        gamePane.setPadding(new Insets(GAME_PANE_TOP_PADDING, GAME_PANE_PADDING, GAME_PANE_PADDING, GAME_PANE_PADDING));
        Text subtitle = new Text(Utilities.getValue(myResources, "gamePaneTitle"));
        subtitle.getStyleClass().add(SUBTITLE_SELECTOR);
        BorderPane.setAlignment(subtitle, Pos.CENTER);
        gamePane.setTop(subtitle);
        gamePane.setCenter((new GameList()).getDisplay());
        myDisplay = gamePane;
    }
}

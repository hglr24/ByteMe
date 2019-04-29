package frontend.statistics;

import data.external.DataManager;
import data.external.GameCenterData;
import data.external.UserScore;
import frontend.Utilities;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticDisplay {
    private static final String SUBTITLE_SELECTOR = "subtitlefont";
    private static final String BODY_SELECTOR = "bodyfont";
    private BorderPane myDisplay;
    private List<UserScore> myScores;
    private ResourceBundle myLanguageBundle;

    private static final int COLUMN_NUMBER = 3;

    public StatisticDisplay(GameCenterData data, DataManager manager) {
        myLanguageBundle = ResourceBundle.getBundle("languages/English");
        try {
            myScores = manager.loadScores(data.getTitle(), data.getAuthorName());
        } catch (SQLException e) {
            myScores = new ArrayList<>();
        }
        initializeDisplay();
    }

    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        myDisplay = new BorderPane();
        GridPane scores = new GridPane();
        scores.setAlignment(Pos.CENTER);
        scores.setHgap(100);
        scores.setVgap(20);
        scores.setPadding(new Insets(10));
        setUpScoresHeader(scores);
        setUpScoresBody(scores);
        myDisplay.setCenter(scores);
    }

    private void setUpScoresHeader(GridPane scores) {
        for(int i = 0; i < COLUMN_NUMBER; i++) {
            Text header = new Text(Utilities.getValue(myLanguageBundle, "scoreHeader" + i));
            header.getStyleClass().add(SUBTITLE_SELECTOR);
            scores.add(header, i, 0);
        }
    }

    private void setUpScoresBody(GridPane scores) {
        int i = 1;
        for(UserScore score : myScores) {
            if(i > 10) {
                break;
            }
            Text rank = new Text(i + "");
            rank.getStyleClass().add(BODY_SELECTOR);
            scores.add(rank, 0, i);
            Text username = new Text(score.getUserName());
            username.getStyleClass().add(BODY_SELECTOR);
            scores.add(username, 1, i);
            Text scoreValue = new Text("" + Math.round(score.getScore()));
            scoreValue.getStyleClass().add(BODY_SELECTOR);
            scores.add(scoreValue, 2, i);
            i++;
        }
    }


}

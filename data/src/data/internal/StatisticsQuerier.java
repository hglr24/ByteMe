package data.internal;

import data.external.UserScore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class StatisticsQuerier extends Querier {

    private static final String STATISTICS_TABLE_NAME = "GameStatistics";
    private static final String USER_NAME_COLUMN = "UserName";
    private static final String GAME_NAME_COLUMN = "GameName";
    private static final String AUTHOR_NAME_COLUMN = "AuthorName";
    private static final String SCORE_COLUMN = "Score";
    private static final String TIME_COLUMN = "ScoreTime";

    private static final String SAVE_SCORE = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", STATISTICS_TABLE_NAME, USER_NAME_COLUMN, GAME_NAME_COLUMN, AUTHOR_NAME_COLUMN, SCORE_COLUMN);
    private static final String LOAD_SCORE = String.format("SELECT %s, %s FROM %s WHERE %s = ? AND %s = ? ORDER BY %s DESC", USER_NAME_COLUMN, SCORE_COLUMN, STATISTICS_TABLE_NAME, GAME_NAME_COLUMN, AUTHOR_NAME_COLUMN, SCORE_COLUMN);
    private static final String REMOVE_SCORES = String.format("DELETE FROM %s WHERE %s = ? AND %s = ? AND %s = ?", STATISTICS_TABLE_NAME, USER_NAME_COLUMN, GAME_NAME_COLUMN, AUTHOR_NAME_COLUMN);

    private PreparedStatement mySaveScoreStatement;
    private PreparedStatement myLoadScoresStatement;
    private PreparedStatement myRemoveScoresStatement;

    public StatisticsQuerier(Connection connection) throws SQLException{
        super(connection);
    }

    @Override
    protected void prepareStatements() throws SQLException {
        mySaveScoreStatement = myConnection.prepareStatement(SAVE_SCORE);
        myLoadScoresStatement = myConnection.prepareStatement(LOAD_SCORE);
        myRemoveScoresStatement = myConnection.prepareStatement(REMOVE_SCORES);
        myPreparedStatements = List.of(mySaveScoreStatement, myLoadScoresStatement);
    }

    public void saveScore(String userName, String gameName, String authorName, Double score) throws SQLException {
        mySaveScoreStatement.setString(1, userName);
        mySaveScoreStatement.setString(2, gameName);
        mySaveScoreStatement.setString(3, authorName);
        mySaveScoreStatement.setDouble(4, score);
        mySaveScoreStatement.execute();
    }


    public List<UserScore> loadScores(String gameName, String authorName) throws SQLException {
        List<UserScore> usersScoresList = new LinkedList<>();
        myLoadScoresStatement.setString(1, gameName);
        myLoadScoresStatement.setString(2, authorName);
        ResultSet resultSet = myLoadScoresStatement.executeQuery();
        while (resultSet.next()) {
            usersScoresList.add(new UserScore(resultSet.getString(USER_NAME_COLUMN), resultSet.getDouble(SCORE_COLUMN)));
        }
        return usersScoresList;
    }

    public void removeScores(String userName, String gameName, String authorName) throws SQLException {
        myRemoveScoresStatement.setString(1, userName);
        myRemoveScoresStatement.setString(2, gameName);
        myRemoveScoresStatement.setString(3, authorName);
        myRemoveScoresStatement.execute();
    }
}

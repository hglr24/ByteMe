package data.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckpointQuerier extends Querier {

    private static final String CHECKPOINTS_TABLE_NAME = "Checkpoints";
    private static final String USER_NAME_COLUMN = "UserName";
    private static final String GAME_NAME_COLUMN = "GameName";
    private static final String AUTHOR_NAME_COLUMN = "AuthorName";
    private static final String LEVEL_COLUMN = "LevelObjectXML";
    private static final String CHECKPOINT_TIME_COLUMN = "CheckpointTime";

    private static final String SAVE_CHECKPOINT = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", CHECKPOINTS_TABLE_NAME, USER_NAME_COLUMN, GAME_NAME_COLUMN, AUTHOR_NAME_COLUMN, LEVEL_COLUMN);
    private static final String LOAD_CHECKPOINT = String.format("SELECT %s, %s FROM %s WHERE %s = ? AND %s = ? AND %s = ?", LEVEL_COLUMN, CHECKPOINT_TIME_COLUMN, CHECKPOINTS_TABLE_NAME, USER_NAME_COLUMN, GAME_NAME_COLUMN, AUTHOR_NAME_COLUMN);
    private static final String DELETE_CHECKPOINTS = String.format("DELETE FROM %s WHERE %s = ? AND %s = ? AND %s = ?", CHECKPOINTS_TABLE_NAME, USER_NAME_COLUMN, GAME_NAME_COLUMN, AUTHOR_NAME_COLUMN);

    private PreparedStatement mySaveStatement;
    private PreparedStatement myLoadStatement;
    private PreparedStatement myDeleteStatement;

    /**
     * Querier constructor that stores a connection and initializes the prepared statements
     *
     * @param connection connection to the database
     * @throws SQLException if statements cannot be prepared
     */
    public CheckpointQuerier(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected void prepareStatements() throws SQLException {
        mySaveStatement = myConnection.prepareStatement(SAVE_CHECKPOINT);
        myLoadStatement = myConnection.prepareStatement(LOAD_CHECKPOINT);
        myDeleteStatement = myConnection.prepareStatement(DELETE_CHECKPOINTS);
        myPreparedStatements = List.of(mySaveStatement, myLoadStatement, myDeleteStatement);
    }

    public Map<Timestamp, String> getCheckpoints(String userName, String gameName, String authorName) throws SQLException {
        Map<Timestamp, String> checkpoints = new HashMap<>();
        myLoadStatement.setString(1, userName);
        myLoadStatement.setString(2, gameName);
        myLoadStatement.setString(3, authorName);
        ResultSet resultSet = myLoadStatement.executeQuery();
        while (resultSet.next()) {
            checkpoints.put(resultSet.getTimestamp(CHECKPOINT_TIME_COLUMN), resultSet.getString(LEVEL_COLUMN));
        }
        return checkpoints;
    }

    public void saveCheckpoint(String userName, String gameName, String authorName, String rawXML) throws SQLException {
        mySaveStatement.setString(1, userName);
        mySaveStatement.setString(2, gameName);
        mySaveStatement.setString(3, authorName);
        mySaveStatement.setString(4, rawXML);
        mySaveStatement.execute();
    }

    public void deleteCheckpoints(String userName, String gameName, String authorName) throws SQLException {
        myDeleteStatement.setString(1, userName);
        myDeleteStatement.setString(2, gameName);
        myDeleteStatement.setString(3, authorName);
        myDeleteStatement.execute();
    }
}

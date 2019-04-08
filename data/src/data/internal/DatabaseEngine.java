package data.internal;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DatabaseEngine {

    public static final String TABLE_USER_AUTHENTICATION = "user_authentication";

    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "hashed_password";
    public static final String COLUMN_EMAIL = "email";

    private static final String JDBC_DRIVER = "jdbc:mysql://";
    private static final String IP_ADDRESS = "67.159.94.60";
    private static final String PORT_NUMBER = "3306";
    private static final String DATABASE_NAME = "vooga_byteme";
    private static final String SERVER_TIMEZONE = "serverTimezone=UTC";
    private static final String DATABASE_URL = JDBC_DRIVER + IP_ADDRESS + ":"+ PORT_NUMBER + "/" + DATABASE_NAME +
            "?" + SERVER_TIMEZONE;
    private static final String USERNAME = "vooga";
    private static final String PASSWORD = "byteMe!";

    public static final String GAME_INFORMATION_TABLE_NAME = "GameInformation";
    private static final String GAME_ID_COLUMN = "GameID";
    private static final String GAME_NAME_COLUMN = "GameName";
    private static final String GAME_DATA_COLUMN = "GameData";
    private static final String GAME_INFO_COLUMN = "GameInfo";
    private static final String ASSETS_COLUMN = "Assets";
    private static final String FIRST_PUBLISHED_COLUMN = "FirstPublished";
    private static final String MOST_RECENT_PUBLISHED_COLUMN = "MostRecentPublish";
    public static final List<String> GAME_INFORMATION_COLUMN_NAME = List.of(
            GAME_NAME_COLUMN,
            GAME_DATA_COLUMN,
            GAME_INFO_COLUMN,
            ASSETS_COLUMN,
            FIRST_PUBLISHED_COLUMN,
            MOST_RECENT_PUBLISHED_COLUMN
    );

    public static final String GAME_STATISTICS_TABLE_NAME = "GameStatistics";
    private static final String USER_ID_COLUMN = "UserID";
    private static final String SCORE_COLUMN = "Score";
    private static final String TIMESTAMP_COLUMN = "Timestamp";
    public static final List<String> GAME_STATISTICS_COLUMN_NAMES = List.of(
            GAME_ID_COLUMN,
            GAME_NAME_COLUMN,
            USER_ID_COLUMN,
            SCORE_COLUMN,
            TIMESTAMP_COLUMN
    );

    public static final String USERS_TABLE_NAME= "Users";
    private static final String USERNAME_COLUMN = "UserName";
    private static final String PASSWORD_COLUMN = "Password";
    private static final String AUTHORED_GAMES_COLUMN = "AuthoredGames";
    private static final String LAST_LOGIN_COLUMN = "LastLogin";
    private static final String FIRST_LOGIN_COLUMN = "FirstLogin";
    public static final List<String> USERS_COLUMN_NAMES = List.of(
            USERNAME_COLUMN,
            USER_ID_COLUMN,
            PASSWORD_COLUMN,
            AUTHORED_GAMES_COLUMN,
            LAST_LOGIN_COLUMN,
            FIRST_LOGIN_COLUMN
    );

    public static final String CHECKPOINTS_TABLE_NAME = "Checkpoints";
    private static final String LEVEL_OBJECT_COLUMN = "LevelObjectXML";
    private static final String CHECKPOINT_COLUMN = "CheckpointTime";
    public static final List<String> CHECKPOINTS_COLUMN_NAMES = List.of(
            USERNAME_COLUMN,
            USER_ID_COLUMN,
            GAME_NAME_COLUMN,
            LEVEL_OBJECT_COLUMN,
            CHECKPOINT_COLUMN
    );

    public DatabaseEngine(){

    }

    public void createEntryForNewGame(String gameName){
        int id = 4;
        String sqlQuery =
                "INSERT INTO " + "GameInformation" + " " + "(GameID, GameName) VALUES("+ id + ", '" + gameName +"')";
        executeStatement(sqlQuery);
    }

    public void printGameTable(){
        printTable("GameInformation");
    }

    private void printTable(String tableToPrint) {
        ResultSet results = executeStatement("SELECT * FROM " + tableToPrint);
//        try {
//            processResults(results);
//        } catch (SQLException exception){
//            exception.printStackTrace();
//        }
    }

    private ResultSet executeStatement(String sqlQuery) {
        ResultSet results = null;
        try {

            //Establish connection to the database, will create database if not exists at the path
            Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

            // Everything with JDBC is done with statements, use the connection to create a statement
            Statement statement = conn.createStatement();
            statement.execute(sqlQuery);

            results = statement.getResultSet();
            if (results != null) {
                processResults(results);
            }

            statement.close();
            conn.close();

        } catch (SQLException exception){
            System.out.println("Failed: " + exception.getMessage());
        }
        return results;
    }

    public void updateGameEntry(String gameName, String rawXML){

    }

    public void addAssets(String gameName, List<String> assetPaths){

    }

    public String loadGameData(String gameName){
        return "";
    }

    public String loadGameInformation(String gameName){
        return "";
    }

    private void processResults(ResultSet results) throws SQLException {
        // iterate of the results, starts at beginning so results.next() takes you to first record
        while (results.next()){
            System.out.println(results.getString(1));
//            System.out.println(results.getString("name") + " " +
//                    results.getInt("phone") + " " +
//                    results.getString("email"));
        }
        // have to close your results sets since it is a resource
        results.close();
    }


}

package data.internal;

import java.sql.*;
import java.util.List;

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
            System.out.println(results.getString(0));
//            System.out.println(results.getString("name") + " " +
//                    results.getInt("phone") + " " +
//                    results.getString("email"));
        }
        // have to close your results sets since it is a resource
        results.close();
    }


}

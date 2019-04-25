package data.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Querier objects are used to query the database.  Querier objects should have defined tables and general areas of
 * the database that they query to separate functionality and divide up the database accesses to smaller classes
 */
public abstract class Querier {

    protected static final String ON_DUPLICATE_UPDATE = "ON DUPLICATE KEY UPDATE";
    protected static final String DELETE_ONE_CONDITION = "DELETE FROM %s WHERE %s = ?";
    protected static final String DELETE_TWO_CONDITIONS = "DELETE FROM %s WHERE %s = ? AND %s = ?";
    protected static final String SELECT_ONE_COLUMN_ONE_CONDITION = "SELECT %s FROM %s WHERE %s = ?";
    protected static final String SELECT_TWO_WHOLE_COLUMNS = "SELECT %s, %s FROM %s;";
    protected static final String INSERT_TWO_VALUES = "INSERT INTO %s (%s, %s) VALUES (?, ?)";
    protected static final String INSERT_THREE_VALUES = "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)";
    protected static final String UPDATE_ONE_COLUMN = "%s %s %s = ?";

    protected Connection myConnection;
    protected List<PreparedStatement> myPreparedStatements;

    /**
     * Querier constructor that stores a connection and initializes the prepared statements
     * @param connection connection to the database
     * @throws SQLException if statements cannot be prepared
     */
    public Querier(Connection connection) throws SQLException{
        myConnection = connection;
        prepareStatements();
    }

    /**
     * Prepares all the sql statements to be run by the concrete Querier class
     * @throws SQLException if statements cannot be prepared
     */
    protected abstract void prepareStatements() throws SQLException;

    /**
     * Closes all the open prepared statements to manage resources and prevent resource leaks
     * @throws SQLException if statements cannot be closed properly
     */
    public void closeStatements() throws SQLException{
        for (PreparedStatement statement : myPreparedStatements){
            if (statement != null){
                statement.close();
            }
        }
    }
}

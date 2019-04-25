package data.internal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Querier class to access and query the User table to perform user authentication
 */
public class UserQuerier extends Querier{

    private static final String USERS_TABLE_NAME= "Users";
    private static final String USERNAME_COLUMN = "UserName";
    private static final String PASSWORD_COLUMN = "Password";

    private static final String GET_HASHED_PASSWORD = String.format(SELECT_ONE_COLUMN_ONE_CONDITION, PASSWORD_COLUMN, USERS_TABLE_NAME,
            USERNAME_COLUMN);
    private static final String CREATE_USER =
            String.format(INSERT_TWO_VALUES, USERS_TABLE_NAME, USERNAME_COLUMN, PASSWORD_COLUMN);
    private static final String DELETE_USER = String.format(DELETE_ONE_CONDITION, USERS_TABLE_NAME, USERNAME_COLUMN);

    private static final String HASH_ALGORITHM = "SHA-256";

    private static final String COULD_NOT_VALIDATE_USER = "Could not validate user: ";
    private static final String COULD_NOT_GENERATE_HASH = "Could not generate hash: ";

    private PreparedStatement myGetPasswordStatement;
    private PreparedStatement myCreateUserStatement;
    private PreparedStatement myDeleteUserStatement;

    /**
     * UserQuerier constructor
     * @param connection connection to the database
     * @throws SQLException if cannot prepare statements
     */
    public UserQuerier(Connection connection) throws SQLException{
        super(connection);
    }

    @Override
    protected void prepareStatements() throws SQLException {
        myGetPasswordStatement = myConnection.prepareStatement(GET_HASHED_PASSWORD);
        myCreateUserStatement = myConnection.prepareStatement(CREATE_USER);
        myDeleteUserStatement = myConnection.prepareStatement(DELETE_USER);
        myPreparedStatements = List.of(myGetPasswordStatement, myCreateUserStatement, myDeleteUserStatement);
    }

    /**
     * Authenticates a user's login attempt
     * @param userName entered user name
     * @param password entered password
     * @return true if valid user name and password combination
     */
    public boolean authenticateUser(String userName, String password) {
        try {
            String hashedPassword = generateHashedPassword(password);
            String storedHash = retrieveStoredHash(userName);
            return hashedPassword.equals(storedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(COULD_NOT_VALIDATE_USER + e.getMessage());
        }
        return false;
    }

    /**
     * Creates a new user entry in the database
     * @param userName user name of new user
     * @param password password of new user
     * @return true if successfully creates a new user entry
     * @throws SQLException if statement fails
     */
    public boolean createUser(String userName, String password) throws SQLException{
        myCreateUserStatement.setString(1, userName);
        try {
            myCreateUserStatement.setString(2, generateHashedPassword(password));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(COULD_NOT_GENERATE_HASH + e.getMessage());
        }
        int updates = myCreateUserStatement.executeUpdate();
        return updates == 1;
    }

    private String retrieveStoredHash(String userName) throws SQLException{
        myGetPasswordStatement.setString(1, userName);
        ResultSet resultSet = myGetPasswordStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(PASSWORD_COLUMN);
        } else {
            return "";
        }
    }

    // Hashing process adapted from: https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    // Author: Lokesh Gupta
    private String generateHashedPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        byte[] bytes = messageDigest.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte bite : bytes) {
            stringBuilder.append(Integer.toString((bite & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }

    /**
     * Removes a user from the database
     * @param userName name of the user to delete
     * @return true if any users were deleted
     * @throws SQLException if statement fails
     */
    public boolean removeUser(String userName) throws SQLException{
        myDeleteUserStatement.setString(1, userName);
        int affectedRows = myDeleteUserStatement.executeUpdate();
        return affectedRows > 0;
    }
}

package data.internal;

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

    public DatabaseEngine(){

    }


}

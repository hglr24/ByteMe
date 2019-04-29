module data {
    exports data.external;
    requires xstream;
    requires transitive java.sql;
    requires mysql.connector.java;
    requires javafx.graphics;
}
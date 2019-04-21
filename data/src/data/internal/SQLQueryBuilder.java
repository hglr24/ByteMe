package data.internal;

import java.util.List;

public class SQLQueryBuilder {

    public static final String SELECT = "SELECT";
    public static final String FROM = "FROM";
    public static final String SELECT_FROM = SELECT + " * " + FROM;
    public static final String WHERE = "WHERE";
    public static final String EQUALS = "=";
    public static final String STATEMENT_TERMINATOR = ";";
    public static final String SPACER = " ";

    public String buildQuery(List<String> columnNames, String tableName) {
        if (columnNames.isEmpty()){
            return SELECT_FROM + SPACER + tableName + STATEMENT_TERMINATOR;
        }
        StringBuilder stringBuilder = new StringBuilder(SELECT + " ");
        for (String column : columnNames){
            stringBuilder.append(column).append(SPACER);
        }
        stringBuilder.append(FROM + SPACER).append(tableName).append(STATEMENT_TERMINATOR);
        return stringBuilder.toString();
    }
}

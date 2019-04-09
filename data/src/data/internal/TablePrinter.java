package data.internal;

import data.external.DatabaseEngine;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TablePrinter implements ResultsProcessor {

    private String myTableToPrint;

    public TablePrinter(String tableToPrint) {
        myTableToPrint = tableToPrint;
    }

    @Override
    public void processResults(ResultSet resultSet) throws SQLException {
        for (String columnName : DatabaseEngine.DATABASE_SCHEMA.get(myTableToPrint)){
            System.out.print(columnName + "\t");
        }
        System.out.println();
        while(resultSet != null && resultSet.next()){
            for (String columnName : DatabaseEngine.DATABASE_SCHEMA.get(myTableToPrint)){
                System.out.print(resultSet.getString(columnName) + "\t");
            }
            System.out.println();
        }
    }
}

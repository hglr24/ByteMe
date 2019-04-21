package data.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultsProcessor {

    void processResults(ResultSet resultSet) throws SQLException;
}

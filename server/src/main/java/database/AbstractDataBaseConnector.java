package database;

import java.sql.*;

public abstract class AbstractDataBaseConnector {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/jira";
    static final String USER = "test";
    static final String PASS = "test";

    public void creatTable()  throws SQLException {

    }

    public boolean tableExists( String tableName) throws SQLException {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        ) {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }
}

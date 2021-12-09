package Repository;

import Model.Comment;

import java.sql.*;

public class IdGenerator extends AbstractDataBaseConnector{
    public static Integer lastId = 1;

    public static int getNewId() {
        return lastId ++;
    }

    public void creatTable() throws SQLException {

        if (tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE lastId " +
                        "(id SERIAL PRIMARY KEY, " +
                        " lastId VARCHAR(255)) ";

                stmt.executeUpdate(sql);

                String init = "INSERT INTO lastId VALUES (1, 1)";
                stmt.executeUpdate(init);
                System.out.println("Created table in given database...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void initData() {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, lastId FROM lastId");
        ) {
            while(rs.next()){
                lastId = rs.getInt("lastId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "categories";
    }
}

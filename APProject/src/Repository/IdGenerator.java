package Repository;

import java.sql.*;

public class IdGenerator extends AbstractDataBaseConnector {
    public static Integer lastId = 0;

    public static int getNewId() {
        lastId += 1;
        insert(lastId);
        return lastId;
    }

    public void creatTable() throws SQLException {

        if (!tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE idgenerator " +
                        "(id SERIAL PRIMARY KEY, " +
                        " lastId int) ";

                stmt.executeUpdate(sql);

                String init = "INSERT INTO idgenerator VALUES (1, 0)";
                stmt.executeUpdate(init);

                System.out.println("Created table in given database...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void initData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, lastId FROM idgenerator");
        ) {
            while (rs.next()) {
                lastId = rs.getInt("lastId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "idgenerator";
    }

    private static void insert(Integer lastId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            PreparedStatement preparedStatement = conn
                    .prepareStatement("update idgenerator set lastId = ? where id = 1");
            preparedStatement.setInt(1, lastId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

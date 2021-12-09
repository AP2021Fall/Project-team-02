package Repository;

import Model.Log;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogRepository extends AbstractDataBaseConnector{

    private static List<Log> logs = new ArrayList<>();

    public void creatTable() throws SQLException {

        if (tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE logs " +
                        "(id SERIAL PRIMARY KEY, " +
                        " userId INTEGER, " +
                        " date VARCHAR(255)) ";

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void initData() {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, userId, date FROM logs");
        ) {
            while(rs.next()){
                Log log = new Log(rs.getInt("id"),
                        rs.getString("date"),
                        rs.getInt("userId"));
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "logs";
    }

    public void createLog(Log log) {
        log.setId(IdGenerator.getNewId());
        logs.add(log);
    }

    public List<Log> getLogsByUserId(Integer id) {
        return logs.stream().filter(log -> log.getUserId().equals(id)).collect(Collectors.toList());
    }
}
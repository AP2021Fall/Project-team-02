package Repository;

import Model.Board;
import Model.Task;
import Repository.table.BoardTable;
import Repository.table.TaskTable;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class TaskRepository extends AbstractDataBaseConnector{

    private static Map<Integer, Task> tasksById = new HashMap<>();
    private static Map<Integer, TaskTable> taskTablesById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE tasks " +
                        "(id SERIAL PRIMARY KEY, " +
                        " title VARCHAR(255), " +
                        " categoryId INTEGER, " +
                        " description VARCHAR(255)," +
                        " priority VARCHAR(255)," +
                        " creationDate INTEGER, " +
                        " deadLine VARCHAR(255)," +
                        " users VARCHAR(255)," +
                        " comments VARCHAR(255)" +
                        ") ";

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
            ResultSet rs = stmt.executeQuery("SELECT id, title, categoryId, description, priority, creationDate, deadLine, users, comments FROM tasks");
        ) {
            while(rs.next()){
                Task task = new Task(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getString("creationDate"),
                        rs.getString("deadLine"));

                TaskTable taskTable = new TaskTable(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getString("creationDate"),
                        rs.getString("deadLine"),
                        rs.getString("users"),
                        rs.getString("comments"),
                        rs.getInt("categoryId"));


                tasksById.put(task.getId(), task);
                taskTablesById.put(taskTable.getId(), taskTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "tasks";
    }

    public Task findById(int taskId) {
        return tasksById.get(taskId);
    }
}
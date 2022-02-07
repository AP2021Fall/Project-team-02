package Repository;

import Model.*;
import Repository.table.TaskTable;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskRepository extends AbstractDataBaseConnector {

    public static Map<Integer, Task> tasksById = new HashMap<>();
    private static Map<Integer, TaskTable> taskTablesById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (!tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE tasks " +
                        "(id SERIAL PRIMARY KEY, " +
                        " title VARCHAR(255), " +
                        " categoryId INTEGER, " +
                        " description VARCHAR(255)," +
                        " priority VARCHAR(255)," +
                        " creationDate VARCHAR(255), " +
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
//            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "tasks";
    }

    public Task findById(int taskId) {
        return tasksById.get(taskId);
    }

    public List<Integer> removeByBoard(Integer boardId) {
        List<Task> toRemove = tasksById.values().stream().filter(t -> t.getCategory().getBoard().getId().equals(boardId)).collect(Collectors.toList());
        toRemove.forEach(t -> {
            tasksById.remove(t.getId());
            taskTablesById.remove(t.getId());
        });
        return toRemove.stream().map(Task::getId).collect(Collectors.toList());
    }

    public Task createTask(Task task) {
        task.setId(IdGenerator.getNewId());
        insert(task.getTable());
        return tasksById.put(task.getId(), task);
    }

    public void insert(TaskTable taskTable){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement("INSERT INTO tasks (id, title, categoryId, description, priority, creationDate, deadLine, users, comments) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, taskTable.getId());
            preparedStatement.setString(2, taskTable.getTitle());
            preparedStatement.setInt(3, taskTable.getCategoryId());
            preparedStatement.setString(4, taskTable.getDescription());
            preparedStatement.setString(5, taskTable.getPriority());
            preparedStatement.setString(6, taskTable.getCreationDate());
            preparedStatement.setString(7, taskTable.getDeadLine());
            preparedStatement.setString(8, taskTable.getUsers());
            preparedStatement.setString(9, taskTable.getComments());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void convertTableToObject(){
        for (Task task : tasksById.values()) {
            TaskTable taskTable = taskTablesById.get(task.getId());

            if(taskTable.getUsers() != null) {
                String[] users = taskTable.getUsers().split(",");
                for (String userIdStr : users) {
                    try {
                        Integer userId = Integer.parseInt(userIdStr);
                        User user = UserRepository.usersById.get(userId);
                        task.getUsers().add(user);
                        user.getTasks().add(task);
                    } catch (Exception e) {
//                    e.printStackTrace();
                    }
                }
            }
            if (taskTable.getComments() != null) {
                String[] comments = taskTable.getComments().split(",");
                for (String commentIdStr : comments) {
                    try {
                        Integer commentId = Integer.parseInt(commentIdStr);
                        Comment comment = CommentRepository.commentsById.get(commentId);
                        task.getComments().add(comment);
                    } catch (Exception e) {
//                    e.printStackTrace();
                    }
                }
            }
        }

    }

    public void update(Task task){
        update(task.getTable());
    }

    private void update(TaskTable taskTable){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement("update tasks " +
                    " set title = ?, categoryId = ?, description = ?, priority = ?, creationDate = ?, deadLine = ?, users = ?, comments = ?" +
                    " where id = ?");

            preparedStatement.setString(1, taskTable.getTitle());
            preparedStatement.setInt(2, taskTable.getCategoryId());
            preparedStatement.setString(3, taskTable.getDescription());
            preparedStatement.setString(4, taskTable.getPriority());
            preparedStatement.setString(5, taskTable.getCreationDate());
            preparedStatement.setString(6, taskTable.getDeadLine());
            preparedStatement.setString(7, taskTable.getUsers());
            preparedStatement.setString(8, taskTable.getComments());
            preparedStatement.setInt(9, taskTable.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}

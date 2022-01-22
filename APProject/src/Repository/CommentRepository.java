package Repository;

import Model.Comment;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommentRepository extends AbstractDataBaseConnector {

    public static Map<Integer, Comment> commentsById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (!tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE comments " +
                        "(id SERIAL PRIMARY KEY, " +
                        " userId INTEGER, " +
                        " taskId INTEGER, " +
                        " message VARCHAR(255)) ";

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void initData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, userId, taskId, message FROM comments");
        ) {
            while (rs.next()) {
                Comment comment = new Comment(rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getInt("taskId"),
                        rs.getString("message"));
                commentsById.put(comment.getId(), comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "comments";
    }

    public void removeByTaskId(List<Integer> taskIds) {
        List<Integer> toRemove = commentsById.values().stream().filter(c -> taskIds.contains(c.getTaskId()))
                .map(Comment::getId).collect(Collectors.toList());

        commentsById.remove(toRemove);
    }


    public Comment createComment(Comment comment) {
        comment.setId(IdGenerator.getNewId());
        commentsById.put(comment.getId(), comment);
        insert(comment);
        return comment;

    }

    private void insert(Comment comment) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            PreparedStatement preparedStatement;
            preparedStatement = conn
                    .prepareStatement("INSERT INTO comments (id, userId, taskId, message) VALUES(?, ?, ?, ?)");

            preparedStatement.setInt(1, comment.getId());
            preparedStatement.setInt(2, comment.getUserId());
            preparedStatement.setInt(3, comment.getTaskId());
            preparedStatement.setString(4, comment.getMessage());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

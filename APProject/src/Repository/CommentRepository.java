package Repository;

import Model.Comment;
import Model.Log;
import Model.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CommentRepository extends AbstractDataBaseConnector{

    private static Map<Integer, Comment> commentsById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (tableExists(getTableName())) {
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
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, userId, taskId, message FROM comments");
        ) {
            while(rs.next()){
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
}

package Repository;

import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageRepository extends AbstractDataBaseConnector {

    private static List<Message> messages = new ArrayList<>();

    public void creatTable() throws SQLException {

        if (tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE messages " +
                        "(id SERIAL PRIMARY KEY, " +
                        " senderId INTEGER, " +
                        " receiverId INTEGER, " +
                        " type VARCHAR(255), " +
                        " txt VARCHAR(255)) ";

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
             ResultSet rs = stmt.executeQuery("SELECT id, senderId,receiverId, type, txt FROM messages");
        ) {
            while (rs.next()) {
                Message message = new Message(rs.getInt("id"),
                        rs.getString("txt"),
                        rs.getString("type"),
                        rs.getInt("senderId"),
                        rs.getInt("receiverId"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "messages";
    }

    public List<Message> findByReceiverIdAndType(Integer userId, String type) {
        return messages.stream().filter(m -> m.getReceiverId().equals(userId) && m.getType().equals(type)).collect(Collectors.toList());
    }

    public Message createMessage(Message message) {
        message.setId(IdGenerator.getNewId());

        messages.add(message);
        return message;
    }
}
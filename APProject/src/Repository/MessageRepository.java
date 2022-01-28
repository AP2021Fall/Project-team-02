package Repository;

import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageRepository extends AbstractDataBaseConnector {
    public static List<Message> messages = new ArrayList<>();

    public void creatTable() throws SQLException {

        if (!tableExists(getTableName())) {
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
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, senderId,receiverId, type, txt FROM messages");
        ) {
            while(rs.next()){
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

    public List<Message> findByReceiverId(Integer userId) {
        return messages.stream().filter(m -> m.getReceiverId().equals(userId)).collect(Collectors.toList());
    }

    public Message createMessage(Message message) {
        message.setId(IdGenerator.getNewId());
        messages.add(message);
        insert(message);
        return message;
    }

    public void insert(Message message){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            PreparedStatement preparedStatement;
            if (message.getReceiverId() != null) {
                preparedStatement = conn
                        .prepareStatement("INSERT INTO messages (id, senderId, receiverId, type, txt) VALUES(?, ?, ?, ?, ?)");

                preparedStatement.setInt(1, message.getId());
                preparedStatement.setInt(2, message.getSenderId());
                preparedStatement.setInt(3, message.getReceiverId());
                preparedStatement.setString(4, message.getType());
                preparedStatement.setString(5, message.getTxt());
            }else{
                preparedStatement = conn
                        .prepareStatement("INSERT INTO messages (id, senderId, type, txt) VALUES(?, ?, ?, ?)");
                preparedStatement.setInt(1, message.getId());
                preparedStatement.setInt(2, message.getSenderId());
                preparedStatement.setString(3, message.getType());
                preparedStatement.setString(4, message.getTxt());
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void update(Message message){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            PreparedStatement preparedStatement;
            if (message.getReceiverId() != null) {
                preparedStatement = conn
                        .prepareStatement("update messages " +
                                " set senderId = ?, receiverId = ?, type = ?, txt = ?" +
                                " where id = ?");

                preparedStatement.setInt(1, message.getSenderId());
                preparedStatement.setInt(2, message.getReceiverId());
                preparedStatement.setString(3, message.getType());
                preparedStatement.setString(4, message.getTxt());
                preparedStatement.setInt(5, message.getId());
            }else{
                preparedStatement = conn
                        .prepareStatement("update messages " +
                                " set senderId = ?, type= ?, txt = ?" +
                                " where id = ?");
                preparedStatement.setInt(1, message.getSenderId());
                preparedStatement.setString(2, message.getType());
                preparedStatement.setString(3, message.getTxt());
                preparedStatement.setInt(4, message.getId());
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
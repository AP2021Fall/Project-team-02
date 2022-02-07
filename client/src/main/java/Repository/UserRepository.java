package Repository;

import Model.User;
import Repository.table.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository extends AbstractDataBaseConnector {

    public static Map<Integer, User> usersById = new HashMap<>();
    private static Map<Integer, UserTable> userTablesById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (!tableExists(getTableName())) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE TABLE users " +
                        "(id SERIAL PRIMARY KEY, " +
                        " username VARCHAR(255), " +
                        " password VARCHAR(255), " +
                        " email VARCHAR(255), " +
                        " role VARCHAR(255)," +
                        " logs VARCHAR(255)," +
                        " teams VARCHAR(255)," +
                        " messages VARCHAR(255)," +
                        " tasks VARCHAR(255)," +
                        " leader boolean," +
                        " fullName VARCHAR(255)," +
                        " birthDate VARCHAR(255)," +
                        " token VARCHAR(520)," +
                        " isPrivate  boolean" +
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
            ResultSet rs = stmt.executeQuery("SELECT id, username, password, email, role, isPrivate, logs, teams, messages, tasks, leader, fullName, birthDate  FROM users");
        ) {
            while(rs.next()){
                User user = new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("fullName"),
                        rs.getBoolean("leader"),
                        rs.getString("birthDate"),
                        rs.getBoolean("isPrivate"));

                UserTable userTable = new UserTable(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("logs"),
                        rs.getString("messages"),
                        rs.getBoolean("leader"),
                        rs.getString("fullName"),
                        rs.getString("birthDate"),
                        rs.getBoolean("isPrivate"));


                usersById.put(user.getId(), user);
                userTablesById.put(userTable.getId(), userTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTokens() {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, token FROM users");
        ) {
            while(rs.next()){
                User user = usersById.get(rs.getInt("id"));
                user.setToken(rs.getString("token"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    String getTableName() {
        return "users";
    }

    public void createUser(User user) {
        user.setId(IdGenerator.getNewId());
        insert(user.getTable());
        usersById.put(user.getId(), user);
    }

    public boolean existUsername(String username) {
        return usersById.values().stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public boolean existEmail(String email) {
        return usersById.values().stream().anyMatch(u -> u.getEmail().equals(email));
    }

    public User findByUsername(String username) {
        return usersById.values().stream().filter(u -> u.getUsername().equals(username)).findAny().orElse(null);
    }

    public User findByToken(String token) {
        return usersById.values().stream().filter(u -> u.getToken() != null && u.getToken().equals(token)).findAny().orElse(null);
    }

    public User getById(Integer id) {
        return usersById.get(id);
    }

    public void deleteUser(User user) {
        usersById.remove(user.getId());
        removeUserById(user.getId());
        userTablesById.remove(user.getId());
    }

    public void update(User user){
        update(user.getTable());
    }

    private void removeUserById(Integer id) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement = conn
                    .prepareStatement("delete from users where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public List<User> findAll() {
        return new ArrayList<>(usersById.values());
    }

    public void insert(UserTable userTable){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            PreparedStatement preparedStatement;
            preparedStatement = conn
                    .prepareStatement("INSERT INTO users (id, username, password, email, role, logs, messages, leader, fullName, birthDate, isPrivate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, userTable.getId());
            preparedStatement.setString(2, userTable.getUsername());
            preparedStatement.setString(3, userTable.getPassword());
            preparedStatement.setString(4, userTable.getEmail());
            preparedStatement.setString(5, userTable.getRole());
            preparedStatement.setString(6, userTable.getLogs());
            preparedStatement.setString(7, userTable.getMessages());
            preparedStatement.setBoolean(8, userTable.getLeader());
            preparedStatement.setString(9, userTable.getFullName());
            preparedStatement.setString(10, userTable.getBirthDate());
            preparedStatement.setBoolean(11, userTable.getPrivate());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void convertTableToObject(){
        for (User user : usersById.values()) {

            UserTable userTable = userTablesById.get(user.getId());

            if (userTable.getMessages() != null) {
                String[] messages = userTable.getMessages().split(",");
                for (String messageIdStr : messages) {
                    try {
                        Integer messageId = Integer.parseInt(messageIdStr);
                        MessageRepository.messages.stream().filter(m -> m.getId().equals(messageId)).findAny().ifPresent(message -> user.getMessages().add(message));
                    } catch (Exception e) {
//                    e.printStackTrace();
                    }
                }
            }

            if(userTable.getLogs() != null) {
                String[] logs = userTable.getLogs().split(",");
                for (String logIdStr : logs) {
                    try {
                        Integer logId = Integer.parseInt(logIdStr);
                        LogRepository.logs.stream().filter(l -> l.getId().equals(logId)).findAny().ifPresent(log -> user.getLogs().add(log));
                    } catch (Exception e) {
//                    e.printStackTrace();
                    }
                }
            }
        }

    }

    private void update(UserTable userTable){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            PreparedStatement preparedStatement;
            preparedStatement = conn
                    .prepareStatement("update users " +
                            " set username = ?, password = ?, email = ?, role = ?, logs = ?, messages = ?, leader = ?, fullName = ?, birthDate = ? , isPrivate = ?" +
                            " where id = ?");

            preparedStatement.setString(1, userTable.getUsername());
            preparedStatement.setString(2, userTable.getPassword());
            preparedStatement.setString(3, userTable.getEmail());
            preparedStatement.setString(4, userTable.getRole());
            preparedStatement.setString(5, userTable.getLogs());
            preparedStatement.setString(6, userTable.getMessages());
            preparedStatement.setBoolean(7, userTable.getLeader());
            preparedStatement.setString(8, userTable.getFullName());
            preparedStatement.setString(9, userTable.getBirthDate());
            preparedStatement.setBoolean(10, userTable.getPrivate());
            preparedStatement.setInt(11, userTable.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }


}

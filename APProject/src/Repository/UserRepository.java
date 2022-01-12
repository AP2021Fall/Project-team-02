package Repository;

import Model.User;
import Repository.table.UserTable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository extends AbstractDataBaseConnector {

    private static Map<Integer, User> usersById = new HashMap<>();
    private static Map<Integer, UserTable> userTablesById = new HashMap<>();

    public void creatTable() throws SQLException {

        if (tableExists(getTableName())) {
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
                        " birthDate VARCHAR(255)" +
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
            ResultSet rs = stmt.executeQuery("SELECT id, username, password, email, role, logs, teams, messages, tasks, leader, fullName, birthDate  FROM users");
        ) {
            while(rs.next()){
                User user = new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("fullName"),
                        rs.getBoolean("leader"),
                        rs.getString("birthDate"));

                UserTable userTable = new UserTable(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("logs"),
                        rs.getString("teams"),
                        rs.getString("messages"),
                        rs.getString("tasks"),
                        rs.getBoolean("leader"),
                        rs.getString("fullName"),
                        rs.getString("birthDate"));


                usersById.put(user.getId(), user);
                userTablesById.put(userTable.getId(), userTable);
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

    public User getById(Integer id) {
        return usersById.get(id);
    }

    public void deleteUser(User user) {
        usersById.remove(user.getId());
        userTablesById.remove(user.getId());
    }

    public List<User> findAll() {
        return new ArrayList<>(usersById.values());
    }

}

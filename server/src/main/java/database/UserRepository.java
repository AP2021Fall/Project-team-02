package database;

import controller.LoginDto;

import java.sql.*;


public class UserRepository extends AbstractDataBaseConnector {


    public static LoginDto getUserData(String username) {

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username, role, password  FROM users ");
        ) {
            while(rs.next()){
                if (rs.getString("username").equals(username)){
                    return new LoginDto(rs.getString("password"), rs.getString("role"));
                }
            }
        } catch (SQLException e) {

        }

        return null;
    }

    public static void update(String username, String token){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement;
            preparedStatement = conn
                    .prepareStatement("update users set token = ? where username = ?");

            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logout(String token){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement;
            preparedStatement = conn
                    .prepareStatement("update users set token = null where token = ?");

            preparedStatement.setString(1, token);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

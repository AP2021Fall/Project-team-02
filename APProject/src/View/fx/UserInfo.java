package View.fx;

import Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    public static String username;
    public static String role;
    public static List<String> menuStack = new ArrayList<>();

    public static void logout(){
        UserInfo.username = null;
        UserInfo.role = null;
        menuStack = new ArrayList<>();
    }

    public static void setData(String username, String role){
        UserInfo.username = username;
        UserInfo.role = role;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserInfo.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        UserInfo.role = role;
    }
}

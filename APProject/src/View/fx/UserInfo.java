package View.fx;


import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    public static String username;
    public static String role;
    public static List<String> menuStack = new ArrayList<>();
    public static String selectedTeam;
    public static String selectedBoard;
    public static String targetPanel;

    public static void logout(){
        UserInfo.username = null;
        UserInfo.role = null;
        menuStack = new ArrayList<>();
        selectedTeam = null;
        selectedBoard = null;
        targetPanel = null;
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

    public static String getSelectedTeam() {
        return selectedTeam;
    }

    public static void setSelectedTeam(String selectedTeam) {
        UserInfo.selectedTeam = selectedTeam;
    }

    public static String getSelectedBoard() {
        return selectedBoard;
    }

    public static void setSelectedBoard(String selectedBoard) {
        UserInfo.selectedBoard = selectedBoard;
    }

    public static String getTargetPanel() {
        return targetPanel;
    }

    public static void setTargetPanel(String targetPanel) {
        UserInfo.targetPanel = targetPanel;
    }
}

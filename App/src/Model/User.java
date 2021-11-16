package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String password ;
    private String username ;
    private String email ;
    private int id ;
    private ArrayList<Team> userTeams ;
    private ArrayList<Task> userTasks ;

    public ArrayList<Team> getUserTeams() {
        return userTeams;
    }

    public ArrayList<Task> getUserTasks() {
        return userTasks;
    }

    private ArrayList<String> notifications ;
    public ArrayList<String> showUserNotifications() {
        return notifications ;
    }
    public void addUserToTask(Task task) {

    }
    public void addUserToTeam(Team team) {

    }
    public User(int password, String username, String email) {
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public int generateId() {
        return id ;
    }
    public int getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}

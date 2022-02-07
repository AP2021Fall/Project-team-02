package Model;

import Controller.dto.ShowProfileResponse;
import Repository.table.UserTable;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String fullName;
    private String token;
    private Boolean isLeader = false;
    private String birthDate;
    private List<Log> logs = new ArrayList<>();
    private List<Team> teams = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private boolean isPrivate = false;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email, String role, boolean isLeader) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isLeader = isLeader;
    }

    public User(Integer id, String username, String password, String email, String role, String fullName, Boolean isLeader, String birthDate, boolean isPrivate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
        this.isLeader = isLeader;
        this.birthDate = birthDate;
        this.isPrivate = isPrivate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginUsername(){
        return username;
    }

    public String getUsername() {
        if (isPrivate)
            return "Anonymous";
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getLeader() {
        return isLeader;
    }

    public void setLeader(Boolean leader) {
        isLeader = leader;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ShowProfileResponse profile() {
        Integer totalScore = teams.stream().map(t -> (t.getUsersScore().getOrDefault(id, 0))).mapToInt(Integer::intValue).sum();

        return new ShowProfileResponse(fullName, username, birthDate, email, role, totalScore);
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public UserTable getTable() {
        StringBuilder logsIdStr = new StringBuilder("");
        for (Log log : logs) {
            logsIdStr.append(log.getId() + ",");
        }

        StringBuilder messagesIdStr = new StringBuilder("");
        for (Message message : messages) {
            messagesIdStr.append(message.getId() + ",");
        }

        return new UserTable(id, username, password, email, role, logsIdStr.toString(), messagesIdStr.toString(), isLeader, fullName, birthDate, isPrivate);
    }
}


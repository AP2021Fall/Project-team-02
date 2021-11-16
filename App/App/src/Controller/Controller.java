package Controller;

import Model.*;

import java.text.NumberFormat;
import java.time.LocalDateTime;


public class Controller {
    public boolean rejectTeams(String teamNames) {

    }
    public boolean checkIsPending(String teamNames) {

    }
    public boolean acceptTeams(String teamNames) {

    }
    public String showPendingTeams() {

    }
    public void changeRole(String name, UserRole role) {

    }
    public void banUser(String name) {

    }
    public boolean usernameExist(String name) {

    }
    public boolean checkLoggedIn(String name) {

    }
    public boolean checkAdmin(String name) {

    }
    public void showProfile(String name) {

    }
    public void sendNotificationToAll(String notification) {

    }
    public void sendNotificationToMember(String name, String notification) {

    }
    public void sendNotificationToTeam(Team team, String notification) {

    }
    public boolean assignMember(Team team, int taskId, String userName) {

    }
    public boolean addMember(Team team, String teamMember) {

    }
    public String showMember(Team team) {

    }
    public boolean deadlineValid(LocalDateTime deadline) {

    }
    public boolean startTimeValid(LocalDateTime startTime) {

    }
    public boolean taskExists(String task) {

    }
    public Task createTask(String taskTitle, LocalDateTime startTime, LocalDateTime deadline) {

    }
    public String showAllTasks(Team activeTeam) {

    }
    public boolean nameValid(String team) {

    }
    public Team createTeam(String team){

    }
    public String showTeam(String team){

    }
    public String showTeams(TeamLeader teamLeader) {

    }
    public String showTaskById(Team team, int id) {

    }
    public String showTasks(Team team) {

    }
    public void sendMessage(String message){

    }
    public String showChatRoom(Team team) {

    }
    public String showRoadMap(Team team) {

    }
    public String showScoreBoard(Team team) {

    }
    public boolean removeUserFromTask(int id, String Username) {

    }
    public boolean addUserToTask(int id , String Username ) {

    }
    public boolean removeUserFromTask(int id , String Username ) {

    }

    public void editTaskDeadline(int id, LocalDateTime newDeadline) {

    }
    public void editTaskPriority(int id, Priority newPriority) {

    }
    public void editTaskDescription(int id , String discription) {

    }
    public void editTaskTitle(int id , String title) {

    }

    public String showNotification(User activeUser) {

    }
    public String showLog(User activeUser) {

    }
    public void showProfile(User activeUser) {

    }
    public void showTeam() {

    }
    public String showTeams(User activeUser) {

    }
    public void changeUsername(String oldUsername , String newUsername) {

    }

    public void changePassword(String username , String oldPassword ,
                               String newPassword ) {

    }


    public void createUser(String username ,String password , String email) {

    }
    public boolean checkPasswordFormat(String username) {

    }
    public boolean checkEmailFormat(String email) {

    }
    public boolean checkUserNameFormat(String username) {

    }
    public boolean checkUsernameExists(String username) {

    }
    public boolean checkPassword(String username ,String password) {

    }
    public boolean isTeamLeader(User user) {
        return false ;
    }
}

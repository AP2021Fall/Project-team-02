package Controller;

import Controller.dto.ChangePasswordResponse;
import Controller.dto.ChangeUsernameResponse;
import Model.*;
import Repository.*;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    public static BoardRepository boardRepository = new BoardRepository();
    public static CategoryRepository categoryRepository = new CategoryRepository();
    public static CommentRepository CommentRepository = new CommentRepository();
    public static LogRepository logRepository = new LogRepository();
    public static MessageRepository messageRepository = new MessageRepository();
    public static TaskRepository taskRepository = new TaskRepository();
    public static TeamRepository teamRepository = new TeamRepository();
    public static UserRepository userRepository = new UserRepository();

    //    public void getInput(String input) {
//
//
//    }
//    public String showDeadlines(String input) {
//
//    }
//    public int BoardFailure(String BoardName) {
//
//    }
//    public int BoardCompletion(String BoardName) {
//
//    }
//    public String showTasks(String BoardName) {
//
//    }
//    public String showBoard(String BoardName) {
//
//    }
//    public void openTask() {
//
//    }
//    public String showFailedTask(String BoardName) {
//
//    }
//    public String showDoneTasks(String BoardName) {
//
//    }
//    public void changeUserScore() {
//
//    }
//    public void taskDone(int taskId) {
//
//    }
//    public void taskFailed(int taskId) {
//
//    }
//    public String showTasks (String category , String BoardName) {
//
//    }
//    public void addTaskToBoard(String boardName , int id) {
//
//    }
//    public void checkCategoryExists(String category) {
//
//    }
//    public void forceTaskTo(String category, String title , String BoardName) {
//
//    }
//    public boolean isTaskFinished(int taskId) {
//
//    }
//    public boolean userInTeam(String activeTeam , String userName ) {
//
//    }
//    public boolean taskOwner(int taskId) {
//
//    }
//    public boolean checkTaskDeadLine(LocalDateTime dealLine) {
//
//    }
//    public boolean taskExistsInBoard(int TaskId) {
//
//    }
//    public void addTaskToBoard(int taskId ,String boardName) {
//
//    }
//    public void BoardDone(String BoardName) {
//
//    }
//    public void removeActiveBoard(String activeBoard) {
//
//    }
//    public void selectBoard(String activeBoard) {
//
//    }
//    public void removeBoard(String boardName) {
//
//    }
//    public boolean checkBoardExists(String boardName) {
//
//    }
//    public Board createBoard(String boardName) {
//
//    }
//    public boolean rejectTeams(String teamNames) {
//
//    }
//    public boolean checkIsPending(String teamNames) {
//
//    }
//    public boolean acceptTeams(String teamNames) {
//
//    }
//    public String showPendingTeams() {
//
//    }
//    public void changeRole(String name, UserRole role) {
//
//    }
//    public void banUser(String name) {
//
//    }
//    public boolean usernameExist(String name) {
//
//    }
//    public boolean checkLoggedIn(String name) {
//
//    }
//    public boolean checkAdmin(String name) {
//
//    }
//    public void showProfile(String name) {
//
//    }
//    public void sendNotificationToAll(String notification) {
//
//    }
//    public void sendNotificationToMember(String name, String notification) {
//
//    }
//    public void sendNotificationToTeam(Team team, String notification) {
//
//    }
//    public boolean assignMember(Team team, int taskId, String userName) {
//
//    }
//    public boolean addMember(Team team, String teamMember) {
//
//    }
//    public String showMember(Team team) {
//
//    }
//    public boolean deadlineValid(LocalDateTime deadline) {
//
//    }
//    public boolean startTimeValid(LocalDateTime startTime) {
//
//    }
//    public boolean taskExists(String task) {
//
//    }
//    public Task createTask(String taskTitle, LocalDateTime startTime, LocalDateTime deadline) {
//
//    }
//    public String showAllTasks(Team activeTeam) {
//
//    }
//    public boolean nameValid(String team) {
//
//    }
//    public Team createTeam(String team){
//
//    }
//    public String showTeam(String team){
//
//    }
//    public String showTeams(TeamLeader teamLeader) {
//
//    }
//    public String showTaskById(Team team, int id) {
//
//    }
//    public String showTasks(Team team) {
//
//    }
//    public void sendMessage(String message){
//
//    }
//    public String showChatRoom(Team team) {
//
//    }
//    public String showRoadMap(Team team) {
//
//    }
//    public String showScoreBoard(Team team) {
//
//    }
//    public boolean removeUserFromTask(int id, String Username) {
//
//    }
//    public boolean addUserToTask(int id , String Username ) {
//
//    }
//    public boolean removeUserFromTask(int id , String Username ) {
//
//    }
//
//    public void editTaskDeadline(int id, LocalDateTime newDeadline) {
//
//    }
//    public void editTaskPriority(int id, Priority newPriority) {
//
//    }
//    public void editTaskDescription(int id , String discription) {
//
//    }
//    public void editTaskTitle(int id , String title) {
//
//    }
//
//    public String showNotification(User activeUser) {
//
//    }
//    public String showLog(User activeUser) {
//
//    }
//    public void showProfile(User activeUser) {
//
//    }
//    public void showTeam() {
//
//    }
//    public String showTeams(User activeUser) {
//
//    }
    public ChangeUsernameResponse changeUsername(String oldUsername , String newUsername) {
        User user = userRepository.findByUsername(oldUsername);
        if(user != null) {
            if (newUsername.length() < 4)
                return new ChangeUsernameResponse(false, "");

            if (userRepository.findByUsername(newUsername) != null)
                return new ChangeUsernameResponse(false, "Username already taken!");

            if (checkUserNameFormat(newUsername)) {
                if (user.getUsername().equals(newUsername))
                    return new ChangeUsernameResponse(false, "");
                user.setUsername(newUsername);
                return new ChangeUsernameResponse(true, "");
            } else
                return new ChangeUsernameResponse(false, "");
        }
        return null;
    }

    public ChangePasswordResponse changePassword(String username , String oldPassword ,
                                                 String newPassword ) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            if(user.getPassword().equals(oldPassword)) {
                if(oldPassword.equals(newPassword)){
                    return new ChangePasswordResponse(false, "Please type a new password!");
                }
                if (checkPasswordFormat(newPassword)) {
                    user.setPassword(newPassword);
                    return  new ChangePasswordResponse(true, "");
                } else
                    return new ChangePasswordResponse(false, "Please choose a strong password (Containing at least 8 characters including 1 digit and 1 capital letter)");
            } else
                return new ChangePasswordResponse(false, "wrong old password!");
        }
        return null;
    }

    public String createUser(String username, String password, String confirmPassword, String email) {
        if (checkUsernameExists(username)) {
            if (password.equals(confirmPassword)) {
                if (CheckEmailExists(email)) {
                    if (checkUserNameFormat(username)) {
                        if (checkEmailFormat(email)) {
                            if (checkPasswordFormat(password)) {
                                User user = new User(username, password, email);
                                userRepository.createUser(user);
                                return "User created successfully!";
                            } else
                                return "Please choose a strong password (Containing at least 8 characters including 1 digit and 1 capital letter)";
                        } else
                            return "Email address is invalid!";
                    } else
                        return ""; //????????
                } else
                    return "User with this email already exists!";
            } else
                return "Your passwords are not the same!";
        } else
            return ""; //?????????
    }

    public boolean checkPasswordFormat(String password) {
        Pattern passwordPattern = Pattern.compile(""); //????????
        return passwordPattern.matcher(password).matches() && password.length() >= 8;
    }

    public boolean checkEmailFormat(String email) {
        Pattern gmailPattern = Pattern.compile("^[\\w]+gmail.com$");
        Pattern yahooPattern = Pattern.compile("^[\\w]+yahoo.com$");
        return yahooPattern.matcher(email).matches() || gmailPattern.matcher(email).matches();
    }

    public boolean checkUserNameFormat(String username) {
        Pattern usernamePattern = Pattern.compile("^[\\w]$");
        return usernamePattern.matcher(username).matches();
    }

    public boolean checkUsernameExists(String username) {
        return userRepository.existUsername(username);
    }

    public boolean CheckEmailExists(String email) {
        return userRepository.existEmail(email);
    }
//    public boolean checkPassword(String username ,String password) {
//
//    }
//    public boolean isTeamLeader(User user) {
//        return false ;
//    }
}

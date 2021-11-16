package Model;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Task {
    private int id ;
    private Board taskBoard ;
    private String title ;
    private String description;
    private Priority taskPriority ;
    private LocalDateTime taskCreationDate ;
    private LocalDateTime taskDeadLineDate ;
    private ArrayList<String> assignedUsersList;
    private HashMap<String, String> commentsList;

    public void foceTaskNext() {

    }

    public void forceTask() {

    }

    public void addUser(String user) {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskPriority(Priority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public void setTaskCreationDate(LocalDateTime taskCreationDate) {
        this.taskCreationDate = taskCreationDate;
    }

    public void setTaskDeadLineDate(LocalDateTime taskDeadLineDate) {
        this.taskDeadLineDate = taskDeadLineDate;
    }

    public void setAssignedUsersList(ArrayList<String> assignedUsersList) {
        this.assignedUsersList = assignedUsersList;
    }

    public void setCommentsList(HashMap<String, String> commentsList) {
        this.commentsList = commentsList;
    }

    private int generateId() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getTaskPriority() {
        return taskPriority;
    }

    public LocalDateTime getTaskCreationDate() {
        return taskCreationDate;
    }

    public LocalDateTime getTaskDeadLineDate() {
        return taskDeadLineDate;
    }

    public ArrayList<String> getAssignedUsersList() {
        return assignedUsersList;
    }

    public HashMap<String, String> getCommentsList() {
        return commentsList;
    }
}

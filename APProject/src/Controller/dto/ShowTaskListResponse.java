package Controller.dto;

import Model.Task;

import java.util.List;

public class ShowTaskListResponse {
    private boolean successful;
    private String message;
    private List<Task> tasks;

    public ShowTaskListResponse( String message) {
        this.successful = false;
        this.message = message;
    }

    public ShowTaskListResponse(List<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

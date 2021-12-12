package Controller.dto;

import Model.Task;

public class ShowTaskResponse {
    private String message;
    private Task task;

    public ShowTaskResponse(String message) {
        this.message = message;
    }

    public ShowTaskResponse(Task task) {
        this.task = task;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}


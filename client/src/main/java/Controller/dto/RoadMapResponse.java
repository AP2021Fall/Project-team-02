package Controller.dto;

import java.util.Map;

public class RoadMapResponse {
    private boolean successful;
    private String message;
    private Map<String, Integer> tasksStatus;

    public RoadMapResponse(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public RoadMapResponse(boolean successful, Map<String, Integer> tasksStatus) {
        this.successful = successful;
        this.tasksStatus = tasksStatus;
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

    public Map<String, Integer> getTasksStatus() {
        return tasksStatus;
    }

    public void setTasksStatus(Map<String, Integer> tasksStatus) {
        this.tasksStatus = tasksStatus;
    }
}

package Controller.dto;

import Model.Task;

import java.util.List;

public class ShowBoardResponse {
    private String boardName;
    private int completionPercentage;
    private int failedPercentage;
    private String leaderName;
    private List<Task> tasks;

    public ShowBoardResponse(String boardName, String leaderName) {
        this.boardName = boardName;
        this.leaderName = leaderName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public int getFailedPercentage() {
        return failedPercentage;
    }

    public void setFailedPercentage(int failedPercentage) {
        this.failedPercentage = failedPercentage;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}

package Controller.dto;

import Model.Task;
import Model.TaskPriority;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShowBoardResponse {
    private String boardName;
    private int completionPercentage;
    private int failedPercentage;
    private String leaderName;
    private List<Task> tasks = new ArrayList<>();

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

    public List<String> print() {
        List<String> response = new ArrayList<>();
        response.add("Board name: " + boardName);
        response.add("Board completion: " + completionPercentage);
        response.add("Board failed: " + failedPercentage);
        response.add("Board leader: " + leaderName);
        response.add("Highest Priority:");
        getTasksByPriority(TaskPriority.HIGHEST).stream().forEach(t -> t.print(response));
        response.add("High Priority:");
        getTasksByPriority(TaskPriority.HIGH).stream().forEach(t -> t.print(response));
        response.add("Low Priority:");
        getTasksByPriority(TaskPriority.LOW).stream().forEach(t -> t.print(response));
        response.add("Lowest Priority:");
        getTasksByPriority(TaskPriority.LOWEST).stream().forEach(t -> t.print(response));

        return response;

    }

    public List<Task> getTasksByPriority(String taskPriority){
        return tasks.stream().filter(task -> task.getPriority().equals(taskPriority)).sorted(Comparator.comparing(Task::getTitle))
                .collect(Collectors.toList());

    }
}

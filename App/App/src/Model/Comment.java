package Model;

public class Comment {
    private Integer id;
    private Integer userId;
    private Integer taskId;
    private String message;

    public Comment(Integer userId, Integer taskId, String message) {
        this.userId = userId;
        this.taskId = taskId;
        this.message = message;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

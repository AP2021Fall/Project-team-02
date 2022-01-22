package Repository.table;

public class TaskTable {
    private Integer id;
    private String title;
    private String description;
    private String priority;
    private String creationDate;
    private String deadLine;
    private String users;
    private String comments;
    private Integer categoryId;


    public TaskTable(Integer id, String title, String description, String priority, String creationDate, String deadLine, String users, String comments, Integer categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.creationDate = creationDate;
        this.deadLine = deadLine;
        this.users = users;
        this.comments = comments;
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}

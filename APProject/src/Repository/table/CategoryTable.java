package Repository.table;

public class CategoryTable {
    private Integer id;
    private String name;
    private Integer boardId;
    private String tasks;

    public CategoryTable(Integer id, String name, Integer boardId, String tasks) {
        this.id = id;
        this.name = name;
        this.boardId = boardId;
        this.tasks = tasks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }
}

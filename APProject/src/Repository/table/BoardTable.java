package Repository.table;

public class BoardTable {
    private Integer id;
    private String name;
    private Integer teamId;
    private String categories;
    private String tasksCategory;

    public BoardTable(Integer id, String name, Integer teamId, String categories, String tasksCategory) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
        this.categories = categories;
        this.tasksCategory = tasksCategory;
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

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTasksCategory() {
        return tasksCategory;
    }

    public void setTasksCategory(String tasksCategory) {
        this.tasksCategory = tasksCategory;
    }
}

package Repository.table;

public class BoardTable {
    private Integer id;
    private String name;
    private Integer teamId;
    private String tasksCategory;
    private Boolean isActive;

    public BoardTable(Integer id, String name, Integer teamId, String tasksCategory) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
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

    public String getTasksCategory() {
        return tasksCategory;
    }

    public void setTasksCategory(String tasksCategory) {
        this.tasksCategory = tasksCategory;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public int getActive() {
        if (isActive != null && isActive)
            return 1;
        return 0;
    }
}

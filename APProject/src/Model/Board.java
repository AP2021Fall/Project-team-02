package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private Integer id;
    private String name;
    private Team team;
    private List<Category> categories = new ArrayList<>();
    private Map<Integer, Integer> tasksCategory = new HashMap<>();
    private Boolean isActive;

    public Board(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public Board(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Map<Integer, Integer> getTasksCategory() {
        return tasksCategory;
    }

    public void setTasksCategory(Map<Integer, Integer> tasksCategory) {
        this.tasksCategory = tasksCategory;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}


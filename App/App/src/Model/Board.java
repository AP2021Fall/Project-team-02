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

    public Board(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public Integer getId() {
        return id;
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
}

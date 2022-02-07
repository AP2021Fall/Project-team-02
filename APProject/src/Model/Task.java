package Model;

import Repository.table.TaskTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Task {
    private Integer id;
    private String title;
    private String description;
    private String priority;
    private String creationDate;
    private String deadLine;
    private List<User> users = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private Category category;

    public Task(String title) {
        this.title = title;
    }

    public Task(Integer id, String title, String description, String priority, String creationDate, String deadLine) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.creationDate = creationDate;
        this.deadLine = deadLine;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isDone() {
        return category != null && category.getName().equals("done");
    }

    public boolean isFailed() {
        return category != null && category.getName().equals("failed");
    }

    public boolean taskTimeFinished(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD|HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(deadLine);
            return date.compareTo(new Date()) < 0;
        } catch (ParseException e) {
            return false;
        }
    }

    public static int getDeadlineDays(String deadLine) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD|HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(deadLine);

            LocalDate deadLineLocalDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            return Period.between(LocalDate.now(), deadLineLocalDate).getDays();
        } catch (Exception e) {
            return 0;
        }
    }

    public String teamName() {
        if(getCategory()!= null){
            return category.getBoard().getTeam().getName();
        }
        return "";
    }

    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd|HH:mm");
        dateFormat.setLenient(false);
        try {
            Date date = dateFormat.parse(dateStr);
            return date.compareTo(new Date()) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void print(List<String> response){
        response.add("Title: " + title);
        response.add("Category: " + category.getName());
        response.add("Description; " + description);
        response.add("Creation Date: " +creationDate);
        response.add("Deadline: " + description);
        List<String> usernames = users.stream().map(User::getUsername).collect(Collectors.toList());
        response.addAll(usernames);
        response.add("Assigned to:" + Arrays.toString(usernames.toArray()));
        response.add("Status: " + category);
    }

    public TaskTable getTable() {
        StringBuilder usersId = new StringBuilder("");
        for (User user : users) {
            usersId.append(user.getId() + ",");
        }

        StringBuilder commentsId = new StringBuilder("");
        for (Comment comment : comments) {
            commentsId.append(comment.getId() + ",");
        }

        int categoryId = category != null? category.getId(): 0;
        return new TaskTable(id, title, description,priority, creationDate,deadLine, usersId.toString(), commentsId.toString(), categoryId);
    }
}


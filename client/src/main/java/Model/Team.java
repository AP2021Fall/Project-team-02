package Model;

import Repository.table.TeamTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {
    private Integer id;
    private String name;
    private User leader;
    private Map<Integer, Integer> usersScore = new HashMap<>();
    private List<User> members = new ArrayList<>();
    public List<User> suspendMembers = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private List<Board> boards = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private boolean active;

    public Team(String name) {
        this.name = name;
        this.active = false;
    }

    public Team(Integer id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
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

    public Map<Integer, Integer> getUsersScore() {
        return usersScore;
    }

    public void setUsersScore(Map<Integer, Integer> usersScore) {
        this.usersScore = usersScore;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User findByUsername(String username) {
        return members.stream().filter(u -> u.getUsername().equals(username)).findAny().orElse(null);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> task) {
        this.tasks = task;
    }

    public List<User> getSuspendMembers() {
        return suspendMembers;
    }

    public void setSuspendMembers(List<User> suspendMembers) {
        this.suspendMembers = suspendMembers;
    }

    public TeamTable getTable() {
        StringBuilder usersScoreStr = new StringBuilder("");
        usersScore.forEach((userId, score) -> {
            usersScoreStr.append(userId + ",");
            usersScoreStr.append(score + ",");
        });

        StringBuilder membersIdStr = new StringBuilder("");
        for (User member : members) {
            membersIdStr.append(member.getId() + ",");
        }

        StringBuilder messagesIdStr = new StringBuilder("");
        for (Message message : messages) {
            messagesIdStr.append(message.getId() + ",");
        }

        StringBuilder tasksIdStr = new StringBuilder("");
        for (Task task : tasks) {
            tasksIdStr.append(task.getId() + ",");
        }


        StringBuilder suspendMembersIdStr = new StringBuilder("");
        for (User suspendMember : suspendMembers) {
            suspendMembersIdStr.append(suspendMember.getId() + ",");
        }



        return new TeamTable(id, name, leader.getId(), usersScoreStr.toString(), membersIdStr.toString(), messagesIdStr.toString(), tasksIdStr.toString(), suspendMembersIdStr.toString(), active);
    }
}


package Repository.table;

public class TeamTable {
    private Integer id;
    private String name;
    private Integer leaderId;
    private String usersScore;
    private String members;
    private String messages;
    private boolean active;

    public TeamTable(Integer id, String name, Integer leaderId, String usersScore, String members, String messages, boolean active) {
        this.id = id;
        this.name = name;
        this.leaderId = leaderId;
        this.usersScore = usersScore;
        this.members = members;
        this.messages = messages;
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

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public String getUsersScore() {
        return usersScore;
    }

    public void setUsersScore(String usersScore) {
        this.usersScore = usersScore;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

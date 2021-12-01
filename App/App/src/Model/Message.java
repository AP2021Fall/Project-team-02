package Model;

public class Message {
    private Integer id;
    private String txt;
    private String type;
    private Integer senderId;

    public Message(String message, String type, Integer senderId) {
        this.txt = message;
        this.type = type;
        this.senderId = senderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
}


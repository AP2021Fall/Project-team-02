package Model;

public class Message {
    private Integer id;
    private String txt;
    private String type;
    private Integer senderId;
    private Integer receiverId;

    public Message(String txt, String type, Integer senderId) {
        this.txt = txt;
        this.type = type;
        this.senderId = senderId;
    }

    public Message(Integer id, String txt, String type, Integer senderId, Integer receiverId) {
        this.id = id;
        this.txt = txt;
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Message(String txt, String type, Integer senderId, Integer receiverId) {
        this.txt = txt;
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
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

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }
}

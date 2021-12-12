package Controller.dto;

import java.util.List;

public class ChatRoomResponse {
    private boolean successful;
    private String message;
    private List<String> senders;
    private List<String> messages;

    public ChatRoomResponse(String message) {
        this.message = message;
    }

    public ChatRoomResponse(List<String> senders, List<String> messages) {
        this.successful = true;
        this.senders = senders;
        this.messages = messages;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getSenders() {
        return senders;
    }

    public void setSenders(List<String> senders) {
        this.senders = senders;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}

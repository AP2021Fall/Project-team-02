package Controller.dto;

public class ChangeUsernameResponse {
    private boolean successful;
    private String newUsername;
    private String message;

    public ChangeUsernameResponse(boolean successful, String newUsername, String message) {
        this.successful = successful;
        this.newUsername = newUsername;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

package Controller.dto;

import Model.User;

public class AdminShowProfileResponse {
    private User user;
    private String message;
    private boolean successful;

    public AdminShowProfileResponse(User user) {
        successful = true;
        this.user = user;
    }

    public AdminShowProfileResponse(String message) {
        successful = false;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

}

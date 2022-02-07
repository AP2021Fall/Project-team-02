package Controller.dto;

public class LoginResponse {
    private Integer userId;
    private String username;
    private String token;
    private String role;
    private String message;

    public LoginResponse(Integer userId, String username, String token, String role, String message) {
        this.userId = userId;
        this.username = username;
        this.token = token;
        this.role = role;
        this.message = message;
    }

    public LoginResponse(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

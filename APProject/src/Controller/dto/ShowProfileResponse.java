package Controller.dto;//package Controller.dto;

public class ShowProfileResponse {
    private String fullName;
    private String username;
    private String birthDate;
    private String email;
    private String role;
    private int totalScore;

    public ShowProfileResponse(String fullName, String username, String birthDate, String email, String role, int totalScore) {
        this.fullName = fullName;
        this.username = username;
        this.birthDate = birthDate;
        this.email = email;
        this.role = role;
        this.totalScore = totalScore;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", totalScore=" + totalScore +
                '}';
    }
}
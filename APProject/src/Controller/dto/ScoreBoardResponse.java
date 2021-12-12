package Controller.dto;

import java.util.Map;

public class ScoreBoardResponse {
    private boolean successful;
    private String message;
    private Map<String, Integer> usersScores;

    public ScoreBoardResponse(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public ScoreBoardResponse(boolean successful, Map<String, Integer> usersScores) {
        this.successful = successful;
        this.usersScores = usersScores;
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

    public Map<String, Integer> getUsersScores() {
        return usersScores;
    }

    public void setUsersScores(Map<String, Integer> usersScores) {
        this.usersScores = usersScores;
    }
}

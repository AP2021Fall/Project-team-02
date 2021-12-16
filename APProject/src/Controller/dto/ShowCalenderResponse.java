package Controller.dto;

import java.util.List;

public class ShowCalenderResponse {
    private List<String> deadLines;
    private String teamName;
    private String message;

    public ShowCalenderResponse(List<String> deadLines, String teamName) {
        this.deadLines = deadLines;
        this.teamName = teamName;
    }

    public ShowCalenderResponse(String message) {
        this.message = message;
    }

    public List<String> getDeadLines() {
        return deadLines;
    }

    public void setDeadLines(List<String> deadLines) {
        this.deadLines = deadLines;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

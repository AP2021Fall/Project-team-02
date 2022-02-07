package Controller.dto;

import java.util.List;

public class ShowCalenderResponse {
    private List<String> deadLines;
    private String message;

    public ShowCalenderResponse(List<String> deadLines) {
        this.deadLines = deadLines;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
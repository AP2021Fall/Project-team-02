package Model;

import java.io.Serializable;

public class Log implements Serializable {
    private Integer id;
    private String date;
    private Integer userId;

    public Log(String date, Integer userId) {
        this.date = date;
        this.userId = userId;
    }

    public Log(Integer id, String date, Integer userId) {
        this.id = id;
        this.date = date;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Log{" +
                "date='" + date + '\'' +
                '}';
    }
}


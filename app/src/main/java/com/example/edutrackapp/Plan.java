package com.example.edutrackapp;


public class Plan {
    private String title;
    private String timeStart;
    private String timeEnd;
    private String note;



    public Plan(String title, String timeStart, String timeEnd, String note) {
        this.title = title;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.note = note;
    }

    // Constructor không có note (để tương thích code cũ)
    public Plan(String title, String timeStart, String timeEnd) {
        this(title, timeStart, timeEnd, "");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

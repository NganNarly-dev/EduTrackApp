package com.example.edutrackapp;


public class Plan {
    private String title;
    private String timeStart;
    private String timeEnd;
    private String note;
    private String date;


    public Plan(String title, String timeStart, String timeEnd, String note, String date) {
        this.title = title;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.note = note;
        this.date = date;
    }
    public Plan(String title, String timeStart, String timeEnd) {
        this(title, timeStart, timeEnd, "", "");
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

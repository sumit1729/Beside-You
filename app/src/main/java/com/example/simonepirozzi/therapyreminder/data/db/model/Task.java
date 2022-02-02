package com.example.simonepirozzi.therapyreminder.data.db.model;

import java.util.ArrayList;
import java.util.Date;

public class Task {

    private String task, time, note;
    private ArrayList<String> listDays;
    private String duration;
    private Date date;
    private int remaining;

    public Task(String task, String time, String note, ArrayList<String> listDays, String duration, Date date) {
        this.task = task;
        this.time = time;
        this.note = note;
        this.listDays = listDays;
        this.duration = duration;
        this.date = date;
        this.remaining = 0;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<String> getListDays() {
        return listDays;
    }

    public void setListDays(ArrayList<String> listDays) {
        this.listDays = listDays;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}

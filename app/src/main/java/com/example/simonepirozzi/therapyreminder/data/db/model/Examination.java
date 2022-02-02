package com.example.simonepirozzi.therapyreminder.data.db.model;

public class Examination {
    private String title, day, time, location, doctor;

    public Examination(String title, String day, String time, String location, String doctor) {
        this.title = title;
        this.day = day;
        this.time = time;
        this.location = location;
        this.doctor = doctor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}

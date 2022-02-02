package com.example.simonepirozzi.therapyreminder.data.db.model;

public class Doctor {
    private String name, surname, address;
    private String number;

    public Doctor(String name, String surname, String address, String number) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

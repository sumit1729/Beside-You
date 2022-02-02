package com.example.simonepirozzi.therapyreminder.ui.home;

public enum HomeDaysEnum {
    MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"), FRIDAY("Friday"), SATURDAY("Saturday"),
    SUNDAY("Sunday");;

    public String value;

    HomeDaysEnum(String value) {
        this.value = value;
    }
}

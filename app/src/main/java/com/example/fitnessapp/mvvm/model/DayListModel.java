package com.example.fitnessapp.mvvm.model;

import com.google.firebase.firestore.DocumentId;

public class DayListModel {

    @DocumentId
    private String dayListId;
    private String day_name;
    private int day_number, day_exercise_number;

    public DayListModel() {

    }

    public DayListModel(String dayListId, String day_name, int day_number, int day_exercise_number) {
        this.dayListId = dayListId;
        this.day_name = day_name;
        this.day_number = day_number;
        this.day_exercise_number = day_exercise_number;
    }

    public String getDayListId() {
        return dayListId;
    }

    public void setDayListId(String dayListId) {
        this.dayListId = dayListId;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public int getDay_number() {
        return day_number;
    }

    public void setDay_number(int day_number) {
        this.day_number = day_number;
    }

    public int getDay_exercise_number() {
        return day_exercise_number;
    }

    public void setDay_exercise_number(int day_exercise_number) {
        this.day_exercise_number = day_exercise_number;
    }
}

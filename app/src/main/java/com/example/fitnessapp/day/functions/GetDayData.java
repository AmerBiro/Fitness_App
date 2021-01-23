package com.example.fitnessapp.day.functions;

import com.example.fitnessapp.mvvm.model.DayListModel;

import java.util.List;

public class GetDayData {

    private String dayListId;
    private String day_name;
    private int day_number, day_exercise_number;
    private List<DayListModel> dayListModels;
    private int position;

    public GetDayData(List<DayListModel> dayListModels, int position) {
        this.dayListModels = dayListModels;
        this.position = position;
        this.dayListId = dayListModels.get(this.position).getDayListId();
        this.day_name = dayListModels.get(this.position).getDay_name();
        this.day_number = dayListModels.get(this.position).getDay_number();
        this.day_exercise_number = dayListModels.get(this.position).getDay_exercise_number();
    }

    public String getDayListId() {
        return dayListId;
    }

    public String getDay_name() {
        return day_name;
    }

    public int getDay_number() {
        return day_number;
    }

    public int getDay_exercise_number() {
        return day_exercise_number;
    }
}

package com.example.fitnessapp.mvvm.model;

import com.google.firebase.firestore.DocumentId;

public class ProgramListModel {

    @DocumentId
    private String program_id;
    private String program_name;
    private String days, exercises;
    private String start_date, end_date;
    private String program_image_url;

    public ProgramListModel() {

    }

    public String getProgram_id() {
        return program_id;
    }

    public void setProgram_id(String program_id) {
        this.program_id = program_id;
    }

    public String getProgram_name() {
        return program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }


    public String getProgram_Image_url() {
        return program_image_url;
    }

    public void setProgram_Image_url(String image_url) {
        this.program_image_url = image_url;
    }
}

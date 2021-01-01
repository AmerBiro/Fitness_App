package com.example.fitnessapp.mvvm.model;

import com.google.firebase.firestore.DocumentId;

public class ProgramModel {

    @DocumentId
    private String program_id;
    private String program_name;
    private String days, exercises;
    private String start_date, end_date;
    private String program_image_url;

    public ProgramModel() {
    }

    public ProgramModel(String program_id, String program_name, String days, String exercises, String start_date, String end_date, String program_image_url) {
        this.program_id = program_id;
        this.program_name = program_name;
        this.days = days;
        this.exercises = exercises;
        this.start_date = start_date;
        this.end_date = end_date;
        this.program_image_url = program_image_url;
    }

    public String getProgram_name() {
        return program_name;
    }


    public String getDays() {
        return days;
    }


    public String getExercises() {
        return exercises;
    }


    public String getStart_date() {
        return start_date;
    }


    public String getEnd_date() {
        return end_date;
    }


    public String getProgram_Image_url() {
        return program_image_url;
    }

}

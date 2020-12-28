package com.example.fitnessapp.model;

import com.google.firebase.firestore.DocumentId;

public class ProgramListModel {

    @DocumentId
    private String program_id;
    private String program_name;
    private long days, excercises;
    private String start_date, end_date, program_image;

    public ProgramListModel() {

    }

    public ProgramListModel(String program_id, String program_name, long days, long excercises, String start_date, String end_date, String program_image) {
        this.program_id = program_id;
        this.program_name = program_name;
        this.days = days;
        this.excercises = excercises;
        this.start_date = start_date;
        this.end_date = end_date;
        this.program_image = program_image;
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

    public String getProgram_image() {
        return program_image;
    }

    public void setProgram_image(String program_image) {
        this.program_image = program_image;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public long getExcercises() {
        return excercises;
    }

    public void setExcercises(long excercises) {
        this.excercises = excercises;
    }
}

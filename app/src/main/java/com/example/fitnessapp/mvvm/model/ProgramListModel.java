package com.example.fitnessapp.mvvm.model;

import com.google.firebase.firestore.DocumentId;

public class ProgramListModel {


    @DocumentId
    private String programListId;
    private String programName, coachName, fitnessCenter, start_date, end_date, image_url;
    private int number, daysNumber, exercisesNumber;

    public ProgramListModel() {

    }

    public ProgramListModel(String programListId, String programName, String coachName, String fitnessCenter, String start_date, String end_date, String image_url, int number, int daysNumber, int exercisesNumber) {
        this.programListId = programListId;
        this.programName = programName;
        this.coachName = coachName;
        this.fitnessCenter = fitnessCenter;
        this.start_date = start_date;
        this.end_date = end_date;
        this.image_url = image_url;
        this.number = number;
        this.daysNumber = daysNumber;
        this.exercisesNumber = exercisesNumber;
    }

    public String getProgramListId() {
        return programListId;
    }

    public void setProgramListId(String programListId) {
        this.programListId = programListId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getFitnessCenter() {
        return fitnessCenter;
    }

    public void setFitnessCenter(String fitnessCenter) {
        this.fitnessCenter = fitnessCenter;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDaysNumber() {
        return daysNumber;
    }

    public void setDaysNumber(int daysNumber) {
        this.daysNumber = daysNumber;
    }

    public int getExercisesNumber() {
        return exercisesNumber;
    }

    public void setExercisesNumber(int exercisesNumber) {
        this.exercisesNumber = exercisesNumber;
    }
}

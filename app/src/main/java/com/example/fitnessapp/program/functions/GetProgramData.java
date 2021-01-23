package com.example.fitnessapp.program.functions;

import com.example.fitnessapp.mvvm.model.ProgramListModel;

import java.util.List;

public class GetProgramData {

    private String programListId;
    private String programName, coachName, fitnessCenter, start_date, end_date, image_url;
    private int number, daysNumber, exercisesNumber;
    private List<ProgramListModel> programListModels;
    private int position;

    public GetProgramData(List<ProgramListModel> programListModels, int position) {
        this.programListModels = programListModels;
        this.position = position;
        this.programListId = programListModels.get(this.position).getProgramListId();
        this.programName = programListModels.get(this.position).getProgramName();
        this.coachName = programListModels.get(this.position).getCoachName();
        this.fitnessCenter = programListModels.get(this.position).getFitnessCenter();
        this.daysNumber = programListModels.get(this.position).getDaysNumber();
        this.exercisesNumber = programListModels.get(this.position).getExercisesNumber();
        this.start_date = programListModels.get(this.position).getStart_date();
        this.end_date = programListModels.get(this.position).getEnd_date();
        this.image_url = programListModels.get(this.position).getImage_url();
        this.number = programListModels.get(this.position).getNumber();
    }

    public String getProgramListId() {
        return programListId;
    }

    public String getProgramName() {
        return programName;
    }

    public String getCoachName() {
        return coachName;
    }

    public String getFitnessCenter() {
        return fitnessCenter;
    }

    public int getDaysNumber() {
        return daysNumber;
    }

    public int getExercisesNumber() {
        return exercisesNumber;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getImage_url() {
        return image_url;
    }

    public int getNumber() {
        return number;
    }
}

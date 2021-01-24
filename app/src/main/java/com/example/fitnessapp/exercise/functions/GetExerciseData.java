package com.example.fitnessapp.exercise.functions;

import com.example.fitnessapp.mvvm.model.ExerciseListModel;

import java.util.List;

public class GetExerciseData {

    private String exercise_list_id;
    private String name, image_url, link;
    private long number, reps, rest, sets, start_weight;
    private List<ExerciseListModel> exerciseListModels;
    private ExerciseListModel exerciseListModel;
    private int position;

    public GetExerciseData(List<ExerciseListModel> exerciseListModels, int position) {
        this.exerciseListModels = exerciseListModels;
        this.position = position;
        this.exercise_list_id = exerciseListModels.get(this.position).getExercise_list_id();
        this.name = exerciseListModels.get(this.position).getName();
        this.image_url = exerciseListModels.get(this.position).getImage_url();
        this.link = exerciseListModels.get(this.position).getLink();
        this.number = exerciseListModels.get(this.position).getNumber();
        this.reps = exerciseListModels.get(this.position).getReps();
        this.rest = exerciseListModels.get(this.position).getRest();
        this.sets = exerciseListModels.get(this.position).getSets();
        this.start_weight = exerciseListModels.get(this.position).getStart_weight();
    }

    public GetExerciseData(ExerciseListModel exerciseListModel) {
        this.exerciseListModel = exerciseListModel;
        this.exercise_list_id = exerciseListModel.getExercise_list_id();
        this.name = exerciseListModel.getName();
        this.image_url = exerciseListModel.getImage_url();
        this.link = exerciseListModel.getLink();
        this.number = exerciseListModel.getNumber();
        this.reps = exerciseListModel.getReps();
        this.rest = exerciseListModel.getRest();
        this.sets = exerciseListModel.getSets();
        this.start_weight = exerciseListModel.getStart_weight();
    }

    public String getExercise_list_id() {
        return exercise_list_id;
    }

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getLink() {
        return link;
    }

    public long getNumber() {
        return number;
    }

    public long getReps() {
        return reps;
    }

    public long getRest() {
        return rest;
    }

    public long getSets() {
        return sets;
    }

    public long getStart_weight() {
        return start_weight;
    }
}

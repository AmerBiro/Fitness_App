package com.example.fitnessapp.mvvm.model;

import com.google.firebase.firestore.DocumentId;

public class ExerciseListModel {

    @DocumentId
    private String exercise_list_id;
    private String name, image_url, link;
    private int number, reps, rest, sets, start_weight;

    public ExerciseListModel(String exercise_list_id, String name, String image_url, String link, int number, int reps, int rest, int sets, int start_weight) {
        this.exercise_list_id = exercise_list_id;
        this.name = name;
        this.image_url = image_url;
        this.link = link;
        this.number = number;
        this.reps = reps;
        this.rest = rest;
        this.sets = sets;
        this.start_weight = start_weight;
    }

    public ExerciseListModel() {

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

    public int getNumber() {
        return number;
    }

    public int getReps() {
        return reps;
    }

    public int getRest() {
        return rest;
    }

    public int getSets() {
        return sets;
    }

    public int getStart_weight() {
        return start_weight;
    }
}

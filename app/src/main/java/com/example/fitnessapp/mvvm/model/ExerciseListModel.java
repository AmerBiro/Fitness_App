package com.example.fitnessapp.mvvm.model;

import com.google.firebase.firestore.DocumentId;

public class ExerciseListModel {

    @DocumentId
    private String exercise_list_id;
    private String name, image_url, link;
    private long number, reps, rest, sets, start_weight;

    public ExerciseListModel(String exercise_list_id, String name, String image_url, String link, long number, long reps, long rest, long sets, long start_weight) {
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

    public void setExercise_list_id(String exercise_list_id) {
        this.exercise_list_id = exercise_list_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getReps() {
        return reps;
    }

    public void setReps(long reps) {
        this.reps = reps;
    }

    public long getRest() {
        return rest;
    }

    public void setRest(long rest) {
        this.rest = rest;
    }

    public long getSets() {
        return sets;
    }

    public void setSets(long sets) {
        this.sets = sets;
    }

    public long getStart_weight() {
        return start_weight;
    }

    public void setStart_weight(long start_weight) {
        this.start_weight = start_weight;
    }
}

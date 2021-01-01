package com.example.fitnessapp.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Program {

    private String program_name, program_name_key,
            coach_name, coach_name_key,
            fitness_center, fitness_center_key,
            days, days_key,
            exercises, exercises_key,
            start_date, start_date_key,
            end_date, end_date_key;

    public Program(String program_name, String program_name_key, String coach_name, String coach_name_key, String fitness_center, String fitness_center_key, String days, String days_key, String exercises, String exercises_key, String start_date, String start_date_key, String end_date, String end_date_key) {
        this.program_name = program_name;
        this.program_name_key = program_name_key;
        this.coach_name = coach_name;
        this.coach_name_key = coach_name_key;
        this.fitness_center = fitness_center;
        this.fitness_center_key = fitness_center_key;
        this.days = days;
        this.days_key = days_key;
        this.exercises = exercises;
        this.exercises_key = exercises_key;
        this.start_date = start_date;
        this.start_date_key = start_date_key;
        this.end_date = end_date;
        this.end_date_key = end_date_key;
    }

    public void createProgram() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser userID = firebaseAuth.getCurrentUser();
        String userId = userID.getUid();
        CollectionReference programRef = FirebaseFirestore.getInstance()
                .collection("users").document(userId)
                .collection("ProgramList");

        Map<String, Object> program = new HashMap<>();

        program.put(program_name_key, this.program_name);
        program.put(coach_name_key, this.coach_name);
        program.put(fitness_center_key, this.fitness_center);
        program.put(days_key, this.days);
        program.put(exercises_key, this.exercises);
        program.put(start_date_key, this.start_date);
        program.put(end_date_key, this.end_date);

        programRef.add(program);

    }

}

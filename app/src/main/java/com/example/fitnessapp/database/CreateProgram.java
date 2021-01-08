package com.example.fitnessapp.database;

import androidx.annotation.NonNull;

import com.example.fitnessapp.mvvm.ProgramListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CreateProgram {

    private String programName, coachName, fitnessCenter, daysNumber, exercisesNumber, start_date, end_date, image_url;

    public CreateProgram(String programName, String coachName, String fitnessCenter, String daysNumber, String exercisesNumber, String start_date, String end_date, String image_url) {
        this.programName = programName;
        this.coachName = coachName;
        this.fitnessCenter = fitnessCenter;
        this.daysNumber = daysNumber;
        this.exercisesNumber = exercisesNumber;
        this.start_date = start_date;
        this.end_date = end_date;
        this.image_url = image_url;
    }

    public CreateProgram() {
    }

    public void createProgram() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        CollectionReference programRef = FirebaseFirestore.getInstance()
                .collection("user").document(userId)
                .collection("ProgramList");

        Map<String, Object> program = new HashMap<>();

        program.put("programName", this.programName);
        program.put("coachName", this.coachName);
        program.put("fitnessCenter", this.fitnessCenter);
        program.put("daysNumber", this.daysNumber);
        program.put("exercisesNumber", this.exercisesNumber);
        program.put("start_date", this.start_date);
        program.put("end_date", this.end_date);
        program.put("image_url", this.image_url);
        programRef.add(program);
    }

}

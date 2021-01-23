package com.example.fitnessapp.create;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateExercise {

    private String name, image_url, link;
    private long number, reps, rest, sets, start_weight;
    private DocumentReference exerciseRef;
    private Activity activity;
    private View view;
    private NavController controller;


    public CreateExercise() {
    }

    public void createExercise(Activity activity, View view, int i, String userId, String programListId, String dayListId,
                               EditText number, EditText name, EditText sets, EditText reps, EditText rest,
                               EditText start_weight, EditText link){

        this.number = Integer.parseInt(number.getText().toString());
        this.name = name.getText().toString();
        this.sets = Integer.parseInt(sets.getText().toString());
        this.reps = Integer.parseInt(reps.getText().toString());
        this.rest = Integer.parseInt(rest.getText().toString());
        this.start_weight = Integer.parseInt(start_weight.getText().toString());
        this.link = link.getText().toString();


        this.activity = activity;
        this.view = view;
        controller = Navigation.findNavController(view);

        this.exerciseRef = FirebaseFirestore.getInstance()
                .collection("user").document(userId)
                .collection("ProgramList").document(programListId)
                .collection("DayList").document(dayListId);

        Map<String, Object> exercise = new HashMap<>();
        exercise.put("number", this.number);
        exercise.put("name", this.name);
        exercise.put("reps", this.sets);
        exercise.put("rest", this.reps);
        exercise.put("sets", this.rest);
        exercise.put("start_weight", this.start_weight);
        exercise.put("link", this.link);

        exerciseRef.set(exercise).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                controller.navigate(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Failed creating exercise" + e.getMessage(), 1) .show();
            }
        });



    }


}

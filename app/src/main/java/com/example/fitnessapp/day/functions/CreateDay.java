package com.example.fitnessapp.day.functions;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CreateDay {
    private NavController controller;
    private View view;
    private Activity activity;

    private String userId, programListId, day_name;
    private int day_number, day_exercise_number;

    public CreateDay(View view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    public void addDay(String userId, String programListId) {
        this.userId = userId;
        this.programListId = programListId;
        this.day_name = day_name;
        this.day_number = day_number;
        this.day_exercise_number = day_exercise_number;

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.day_create_day_view);
        dialog.getWindow().getAttributes().windowAnimations = R.style.alert_dialog_animation;
        dialog.setCancelable(true);
        dialog.show();
        TextView dayNumber, dayName, day_exercise_number;
        Button addDay;
        dayNumber = dialog.findViewById(R.id.create_day_day_number);
        dayName = dialog.findViewById(R.id.create_day_name);
        day_exercise_number = dialog.findViewById(R.id.create_day_number_of_exercises);
        addDay = dialog.findViewById(R.id.create_day_add_day);

        addDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference dayRef = FirebaseFirestore.getInstance()
                        .collection("user").document(userId)
                        .collection("ProgramList").document(programListId)
                        .collection("DayList");

                Map<String, Object> day = new HashMap<>();
                day.put("day_number", Integer.parseInt(dayNumber.getText().toString()));
                day.put("day_name", dayName.getText().toString());
                day.put("day_exercise_number", Integer.parseInt(day_exercise_number.getText().toString()));
                dayRef.add(day).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: " + documentReference.getId());
                        dialog.cancel();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.cancel();
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
            }
        });
    }
}

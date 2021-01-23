package com.example.fitnessapp.create;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateDay {
    private Activity activity;
    private View view;
    private String userId, programListId;
    private String day_name, day_number, day_exercise_number;

    public CreateDay(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    public void addDay(String userId, String programListId) {
        this.userId = userId;
        this.programListId = programListId;
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
                day.put("day_number", dayNumber.getText().toString());
                day.put("day_name", dayName.getText().toString());
                day.put("day_exercise_number", day_exercise_number.getText().toString());
                dayRef.add(day).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful())
                            dialog.cancel();
                    }
                });
            }
        });
    }
}

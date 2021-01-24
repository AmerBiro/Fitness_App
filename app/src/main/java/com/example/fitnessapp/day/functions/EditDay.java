package com.example.fitnessapp.day.functions;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import com.example.fitnessapp.R;
import com.example.fitnessapp.mvvm.adapter.DayListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class EditDay {

    private NavController controller;
    private View view;
    private Activity activity;

    private String programListId, dayId, day_names;
    private int day_numbers, day_exercise_numbers;
    private DayListAdapter adapter;

    public EditDay(View view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    public void setAdapter(DayListAdapter adapter) {
        this.adapter = adapter;
    }

    public void editDay(String programListId, String dayId, String day_name, int day_number, int day_exercise_number) {
        this.programListId = programListId;
        this.dayId = dayId;
        this.day_names = day_name;
        this.day_numbers = day_number;
        this.day_exercise_numbers = day_exercise_number;

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.day_add_day_view);
        dialog.getWindow().getAttributes().windowAnimations = R.style.alert_dialog_animation;
        dialog.setCancelable(true);
        dialog.show();
        TextView number, name, exercise_number;
        Button editDay;
        number = dialog.findViewById(R.id.create_day_day_number);
        name = dialog.findViewById(R.id.create_day_name);
        exercise_number = dialog.findViewById(R.id.create_day_number_of_exercises);
        editDay = dialog.findViewById(R.id.create_day_add_day);

        number.setText(day_number + "");
        name.setText(day_name);
        exercise_number.setText(day_exercise_number + "");

        editDay.setText("Edit");

        editDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference dayRef = FirebaseFirestore.getInstance()
                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("ProgramList").document(programListId)
                        .collection("DayList").document(dayId);

                Map<String, Object> day = new HashMap<>();
                day.put("day_number", Integer.parseInt(number.getText().toString()));
                day.put("day_name", name.getText().toString());
                day.put("day_exercise_number", Integer.parseInt(exercise_number.getText().toString()));

                dayRef.update(day).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: " + "Successfully updated");
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                adapter.notifyDataSetChanged();
            }
        });
    }



}

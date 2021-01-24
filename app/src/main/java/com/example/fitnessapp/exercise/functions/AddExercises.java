package com.example.fitnessapp.exercise.functions;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AddExercises {

    private View view;
    private Activity activity;

    public AddExercises(View view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    public void addExercises(String programListId, String dayListId) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.add_exercises);
        dialog.getWindow().getAttributes().windowAnimations = R.style.alert_dialog_animation;
        dialog.setCancelable(true);
        dialog.show();
        TextView number_of_exercises_to_be_added;
        Button addExercise;
        number_of_exercises_to_be_added = dialog.findViewById(R.id.number_of_exercises_to_be_added);
        addExercise = dialog.findViewById(R.id.add_exercises);

        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference exerciseRef = FirebaseFirestore.getInstance()
                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("ProgramList").document(programListId)
                        .collection("DayList").document(dayListId)
                        .collection("ExerciseList");

                HashMap<String, Object> exerciseMap = new HashMap<>();
                exerciseMap.put("image_url", "");
                exerciseMap.put("name", "");
                exerciseMap.put("sets", 3);
                exerciseMap.put("reps", 8);
                exerciseMap.put("rest", 45);
                exerciseMap.put("start_weight", 0);
                exerciseMap.put("link", "");

                for (int i = 0; i < Integer.parseInt(number_of_exercises_to_be_added.getText().toString()); i++) {
                    exerciseMap.put("number", i + 1);
                    exerciseRef.add(exerciseMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    dialog.cancel();
                }
            }
        });
    }
}

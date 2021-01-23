package com.example.fitnessapp.exercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fitnessapp.R;
import com.example.fitnessapp.create.CreateExercise;
import com.example.fitnessapp.databinding.ExerciseCreateExerciseBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class CreateExerciseView extends Fragment {

    private @NonNull
    ExerciseCreateExerciseBinding
     binding;
    private NavController controller;
    private String userId, programListId, dayListId;
    private CreateExercise exercise;
    private EditText[] s, si;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ExerciseCreateExerciseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        userId = CreateExerciseViewArgs.fromBundle(getArguments()).getUserId();
        programListId = CreateExerciseViewArgs.fromBundle(getArguments()).getProgramListId();
        dayListId = CreateExerciseViewArgs.fromBundle(getArguments()).getDayListId();
        s = new EditText[2];
        si = new EditText[5];

        Log.d(TAG, "onViewCreated: " + userId + ", " + programListId + ", " + dayListId);
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.createExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, image_url, link;
                int number, reps, rest, sets, start_weight;
                CollectionReference exerciseRef;

                s[0] = binding.exerciseName;;
                s[1] = binding.link;

                si[0] = binding.exerciseNumber;
                si[1] = binding.sets;
                si[2] = binding.reps;
                si[3] = binding.rest;
                si[4] = binding.startWeight;

                for (int i = 0; i < s.length; i++){
                    if (s[i].getText().toString().trim().isEmpty())
                        s[i].setText("");
                }

                for (int i = 0; i < si.length; i++){
                    if (si[i].getText().toString().trim().isEmpty())
                        s[i].setText("0");
                }


                name = binding.exerciseName.getText().toString();
                link = binding.link.getText().toString();
                number = Integer.parseInt(binding.exerciseNumber.getText().toString());
                sets = Integer.parseInt(binding.sets.getText().toString());
                reps = Integer.parseInt(binding.reps.getText().toString());
                rest = Integer.parseInt(binding.rest.getText().toString());
                start_weight = Integer.parseInt(binding.startWeight.getText().toString());


                exerciseRef = FirebaseFirestore.getInstance()
                        .collection("user").document(userId)
                        .collection("ProgramList").document(programListId)
                        .collection("DayList").document(dayListId).collection("ExerciseList");

                Map<String, Object> exercise = new HashMap<>();
                exercise.put("number", number);
                exercise.put("name", name);
                exercise.put("reps", sets);
                exercise.put("rest", reps);
                exercise.put("sets", rest);
                exercise.put("start_weight", start_weight);
                exercise.put("link", link);

                exerciseRef.add(exercise).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference reference) {
                        controller.navigate(R.id.action_createExercise_to_exerciseListViwer);
                        controller.navigateUp();
                        controller.popBackStack();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
            }
        });

    }

}
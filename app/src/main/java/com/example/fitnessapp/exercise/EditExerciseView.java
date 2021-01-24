package com.example.fitnessapp.exercise;

import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.fitnessapp.databinding.ExerciseEditExerciseViewBinding;
import com.example.fitnessapp.exercise.functions.GetExerciseData;
import com.example.fitnessapp.mvvm.model.ExerciseListModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

public class EditExerciseView extends Fragment {

    private @NonNull
    ExerciseEditExerciseViewBinding
            binding;
    private NavController controller;
    private ExerciseListModel exerciseListModel;
    private GetExerciseData getExerciseData;
    private String programListId, dayListId, exerciseListId;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ExerciseEditExerciseViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        programListId = EditExerciseViewArgs.fromBundle(getArguments()).getProgramListId();
        dayListId = EditExerciseViewArgs.fromBundle(getArguments()).getDayListId();
        exerciseListId = EditExerciseViewArgs.fromBundle(getArguments()).getExerciseListId();
        position = EditExerciseViewArgs.fromBundle(getArguments()).getPosition();
        Log.d(TAG, "onViewCreated: " + programListId + ", " + dayListId + ", " + exerciseListId + ", " + position);
        getExerciseData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getExerciseData(){
        DocumentReference exerciseRef = FirebaseFirestore.getInstance()
                .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ProgramList").document(programListId)
                .collection("DayList").document(dayListId)
                .collection("ExerciseList").document(exerciseListId);
        exerciseRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                exerciseListModel = value.toObject(ExerciseListModel.class);
                getExerciseData = new GetExerciseData(exerciseListModel);

                if (!getExerciseData.getImage_url().trim().isEmpty()){
                    Picasso
                            .get()
                            .load(getExerciseData.getImage_url())
                            .fit()
                            .into(binding.editExerciseImage);
                }

                binding.editExerciseNumber.setText(getExerciseData.getNumber() + "");
                binding.editExerciseName.setText(getExerciseData.getName());
                binding.editExerciseSets.setText(getExerciseData.getSets() + "");
                binding.editExerciseReps.setText(getExerciseData.getReps() + "");
                binding.editExerciseRest.setText(getExerciseData.getRest() + "");
                binding.editExerciseStartWeight.setText(getExerciseData.getStart_weight() + "");
                binding.editExerciseLink.setText(getExerciseData.getLink());

            }
        });
    }



}
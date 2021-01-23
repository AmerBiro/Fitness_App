package com.example.fitnessapp.exercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.ExerciseExerciseListViewBinding;
import com.example.fitnessapp.mvvm.adapter.ExerciseListAdapter;
import com.example.fitnessapp.mvvm.model.ExerciseListModel;
import com.example.fitnessapp.mvvm.model.ProgramListModel;
import com.example.fitnessapp.mvvm.viewmodel.ProgramListViewModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ExerciseListView extends Fragment implements ExerciseListAdapter.OnExerciseListItemClicked, View.OnClickListener {

    private @NonNull
    ExerciseExerciseListViewBinding
            binding;
    private NavController controller;
    private String userId, programListId, dayListId;
    private int position;

    private FirebaseFirestore firebaseFirestore;
    private ProgramListViewModel programListViewModel;
    private List<ExerciseListModel> exerciseListModels = new ArrayList<>();

    private RecyclerView recyclerView;
    private ExerciseListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ExerciseExerciseListViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        firebaseFirestore = FirebaseFirestore.getInstance();

        userId = ExerciseListViewArgs.fromBundle(getArguments()).getUserId();
        programListId = ExerciseListViewArgs.fromBundle(getArguments()).getProgramListId();
        dayListId = ExerciseListViewArgs.fromBundle(getArguments()).getDayListId();
        position = ExerciseListViewArgs.fromBundle(getArguments()).getPosition();

//        Log.d(TAG, "onViewCreated: " + userId + ", " + programListId + ", " + dayListId + ", " + position);

        recyclerViewSetup();

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        programListViewModel = new ViewModelProvider(getActivity()).get(ProgramListViewModel.class);
        programListViewModel.getProgramListModelData().observe(getViewLifecycleOwner(), new Observer<List<ProgramListModel>>() {
            @Override
            public void onChanged(List<ProgramListModel> programListModels) {

                Query exerciseListRef = firebaseFirestore
                        .collection("events").document(userId)
                        .collection("ProgramList").document(programListId)
                        .collection("DayList").document(dayListId)
                        .collection("ExerciseList").orderBy("number");

                Log.d(TAG, "test: " + userId + ", " + programListId + ", " + dayListId + ", " + position);

                exerciseListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Log.d(TAG, "onEvent: " + value.getDocumentChanges().toString());
                        exerciseListModels = value.toObjects(ExerciseListModel.class);
//                        Log.d(TAG, "daySize: " + dayListModels.size());
                        adapter.setExerciseListModels(exerciseListModels);
                        adapter.notifyDataSetChanged();
                    }
                });

//                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//                    @Override
//                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                        return false;
//                    }
//
//                    @Override
//                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//                        String dayName, dayNumber, day_exercise_number, dayId;
//                        dayNumber = dayListModels.get(viewHolder.getAdapterPosition()).getDay_number();
//                        dayName = dayListModels.get(viewHolder.getAdapterPosition()).getDay_name();
//                        day_exercise_number = dayListModels.get(viewHolder.getAdapterPosition()).getDay_exercise_number();
//                        dayId = dayListModels.get(viewHolder.getAdapterPosition()).getDayListId();
//
//                        DocumentReference dayRef = firebaseFirestore
//                                .collection("user").document(userId)
//                                .collection("ProgramList").document(programListId)
//                                .collection("DayList").document(dayId);
////                        Log.d(TAG, "getDayListId: " + dayListModels.get(viewHolder.getAdapterPosition()).getDayListId());
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                        builder.setTitle("Delete student");
//                        builder.setMessage("Are you sure that you want to delete the day below\n"
//                                + "Day " + dayNumber + ", " + day_exercise_number + " exercise" + " \n" + dayName + "?")
//                                .setCancelable(true)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dayRef.delete();
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                        adapter.notifyDataSetChanged();
//                                    }
//                                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
//                            @Override
//                            public void onCancel(DialogInterface dialog) {
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                        AlertDialog alert = builder.create();
//                        alert.show();
//                    }
//                }).attachToRecyclerView(recyclerView);
            }
        });
    }








    @Override
    public void onStart() {
        super.onStart();

        binding.addExercise.setOnClickListener(this);
    }

    private void recyclerViewSetup() {
        recyclerView = binding.recyclerview;
        adapter = new ExerciseListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_exercise:
                ExerciseListViewDirections.ActionExerciseListViwerToCreateExercise action =
                        ExerciseListViewDirections.actionExerciseListViwerToCreateExercise();
                action.setUserId(userId);
                action.setProgramListId(programListId);
                action.setDayListId(dayListId);
                controller.navigate(action);
                break;
            default:
        }
    }
}
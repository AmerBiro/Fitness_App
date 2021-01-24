package com.example.fitnessapp.exercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.ExerciseExerciseListViewBinding;
import com.example.fitnessapp.exercise.functions.AddExercises;
import com.example.fitnessapp.exercise.functions.GetExerciseData;
import com.example.fitnessapp.mvvm.adapter.ExerciseListAdapter;
import com.example.fitnessapp.mvvm.model.ExerciseListModel;
import com.example.fitnessapp.program.ProgramListViewDirections;
import com.example.fitnessapp.program.functions.GetProgramData;
import com.google.firebase.auth.FirebaseAuth;
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
    private String programListId, dayListId;
    private AddExercises addExercises;
    private List<ExerciseListModel> exerciseListModels = new ArrayList<>();
    private GetExerciseData getExerciseData;

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

        programListId = ExerciseListViewArgs.fromBundle(getArguments()).getProgramListId();
        dayListId = ExerciseListViewArgs.fromBundle(getArguments()).getDayListId();
        addExercises = new AddExercises(view, getActivity());
        recyclerViewSetup();
        onSwiped();
        getExerciseData();
    }

    private void getExerciseData(){
        Query exerciseRef = FirebaseFirestore.getInstance()
                .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ProgramList").document(programListId)
                .collection("DayList").document(dayListId)
                .collection("ExerciseList")
                .orderBy("number");

        exerciseRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                exerciseListModels = value.toObjects(ExerciseListModel.class);
                Log.d(TAG, "onEvent: " + exerciseListModels.size());
                if (exerciseListModels.size() == 0){
                    addExercises.addExercises(programListId, dayListId);
                }
                adapter.setExerciseListModels(exerciseListModels);
                adapter.notifyDataSetChanged();
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

    private void onSwiped(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                getExerciseData = new GetExerciseData(exerciseListModels, viewHolder.getAdapterPosition());
                if (direction == 4){

                }else{
                    ExerciseListViewDirections.ActionExerciseListViwerToEditExerciseView action =
                            ExerciseListViewDirections.actionExerciseListViwerToEditExerciseView();
                    action.setProgramListId(programListId);
                    action.setDayListId(dayListId);
                    action.setExerciseListId(getExerciseData.getExercise_list_id());
                    action.setPosition(viewHolder.getAdapterPosition());
                    controller.navigate(action);
                }

            }
        }).attachToRecyclerView(recyclerView);
    }


    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_exercise:
                addExercises.addExercises(programListId, dayListId);
//                ExerciseListViewDirections.ActionExerciseListViwerToCreateExercise action =
//                        ExerciseListViewDirections.actionExerciseListViwerToCreateExercise();
//                action.setUserId(userId);
//                action.setProgramListId(programListId);
//                action.setDayListId(dayListId);
//                controller.navigate(action);
                break;
            default:
        }
    }
}
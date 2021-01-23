package com.example.fitnessapp.day;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.create.CreateDay;
import com.example.fitnessapp.databinding.DayDayListViewBinding;
import com.example.fitnessapp.mvvm.adapter.DayListAdapter;
import com.example.fitnessapp.mvvm.model.DayListModel;
import com.example.fitnessapp.mvvm.model.ProgramListModel;
import com.example.fitnessapp.mvvm.viewmodel.ProgramListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class DayListView extends Fragment implements View.OnClickListener, DayListAdapter.OnDayListItemClicked {

    private @NonNull
    DayDayListViewBinding
            binding;
    private NavController controller;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private ProgramListViewModel programListViewModel;
    private int position;
    private String userId;
    private List<DayListModel> dayListModels = new ArrayList<>();
    private String programListId;
    private CreateDay day;

    private RecyclerView recyclerView;
    private DayListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DayDayListViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        position = DayListViewArgs.fromBundle(getArguments()).getPosition();
        controller = Navigation.findNavController(view);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        day = new CreateDay(getActivity(),view);
        recyclerViewSetup();
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        programListViewModel = new ViewModelProvider(getActivity()).get(ProgramListViewModel.class);
        programListViewModel.getProgramListModelData().observe(getViewLifecycleOwner(), new Observer<List<ProgramListModel>>() {
            @Override
            public void onChanged(List<ProgramListModel> programListModels) {
                userId = firebaseUser.getUid();
                programListId = programListModels.get(position).getProgramListId();
                Query dayListRef = firebaseFirestore
                        .collection("user").document(userId)
                        .collection("ProgramList").document(programListId)
                        .collection("DayList").orderBy("day_number");

                dayListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Log.d(TAG, "onEvent: " + value.getDocumentChanges().toString());
                        dayListModels = value.toObjects(DayListModel.class);
//                        Log.d(TAG, "daySize: " + dayListModels.size());
                        adapter.setDayListModels(dayListModels);
                        adapter.notifyDataSetChanged();
                    }
                });

                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        String dayName, dayNumber, day_exercise_number, dayId;
                        dayNumber = dayListModels.get(viewHolder.getAdapterPosition()).getDay_number();
                        dayName = dayListModels.get(viewHolder.getAdapterPosition()).getDay_name();
                        day_exercise_number = dayListModels.get(viewHolder.getAdapterPosition()).getDay_exercise_number();
                        dayId = dayListModels.get(viewHolder.getAdapterPosition()).getDayListId();

                        DocumentReference dayRef = firebaseFirestore
                                .collection("user").document(userId)
                                .collection("ProgramList").document(programListId)
                                .collection("DayList").document(dayId);
//                        Log.d(TAG, "getDayListId: " + dayListModels.get(viewHolder.getAdapterPosition()).getDayListId());

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Delete student");
                        builder.setMessage("Are you sure that you want to delete the day below\n"
                                + "Day " + dayNumber + ", " + day_exercise_number + " exercise" + " \n" + dayName + "?")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dayRef.delete();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        adapter.notifyDataSetChanged();
                                    }
                                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                adapter.notifyDataSetChanged();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }).attachToRecyclerView(recyclerView);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.about.setOnClickListener(this);
        binding.addDay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                DayListViewDirections.ActionDayListViwerToCurrentProgramViwer action =
                        DayListViewDirections.actionDayListViwerToCurrentProgramViwer();
                action.setPosition(position);
                controller.navigate(action);
                break;
            case R.id.add_day:
                day.addDay(userId, programListId);
            default:
        }
    }

    private void recyclerViewSetup() {
        recyclerView = binding.recyclerview;
        adapter = new DayListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position) {
        Log.d(TAG, "dayListOnItemClicked: " + position);
        String dayListId = dayListModels.get(position).getDayListId();
        DayListViewDirections.ActionDayListViwerToExerciseListViwer action =
                DayListViewDirections.actionDayListViwerToExerciseListViwer();
        action.setPosition(position);
        action.setProgramListId(programListId);
        action.setDayListId(dayListId);
        action.setUserId(userId);
        controller.navigate(action);

    }
}
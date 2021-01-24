package com.example.fitnessapp.day;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.DayDayListViewBinding;
import com.example.fitnessapp.day.functions.AddDay;
import com.example.fitnessapp.day.functions.DeleteDay;
import com.example.fitnessapp.day.functions.EditDay;
import com.example.fitnessapp.day.functions.GetDayData;
import com.example.fitnessapp.mvvm.adapter.DayListAdapter;
import com.example.fitnessapp.mvvm.model.DayListModel;

import com.google.firebase.auth.FirebaseAuth;

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
    private RecyclerView recyclerView;
    private DayListAdapter adapter;

    private List<DayListModel> dayListModels = new ArrayList<>();
    private AddDay addDay;
    private GetDayData getDayData;
    private DeleteDay deleteDay;
    private EditDay editDay;

    private String userId;
    private int position;
    private String programListId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DayDayListViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        position = DayListViewArgs.fromBundle(getArguments()).getPosition();
        programListId = DayListViewArgs.fromBundle(getArguments()).getProgramListId();
//        Log.d(TAG, "onViewCreated: " +userId + ", " +  programListId + ", " + position);

        addDay = new AddDay(view, getActivity());
        editDay = new EditDay(view, getActivity());
        recyclerViewSetup();
        getDayListData();
        onSwiped();
    }

    private void getDayListData(){
        Query dayListRef = FirebaseFirestore.getInstance()
                .collection("user").document(userId)
                .collection("ProgramList").document(programListId)
                .collection("DayList")
                .orderBy("day_number");

        dayListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d(TAG, "onEvent: " + value.getDocumentChanges().toString());
                dayListModels = value.toObjects(DayListModel.class);
                adapter.setDayListModels(dayListModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.addDay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_day:
                addDay.addDay(userId, programListId);
            default:
        }
    }

    private void recyclerViewSetup() {
        recyclerView = binding.recyclerview;
        adapter = new DayListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        deleteDay = new DeleteDay(getActivity(), adapter);
        editDay.setAdapter(adapter);
    }

    private void onSwiped(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                getDayData = new GetDayData(dayListModels, viewHolder.getAdapterPosition());
                if (direction == 4){
                    deleteDay.deleteDay(programListId,
                            getDayData.getDayListId(),
                            getDayData.getDay_number() + "",
                            getDayData.getDay_name(),
                            getDayData.getDay_exercise_number() + "");
                }else{
                    editDay.editDay(programListId,
                            getDayData.getDayListId(),
                            getDayData.getDay_name(),
                            getDayData.getDay_number(),
                            getDayData.getDay_exercise_number());
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClicked(int position) {
        Log.d(TAG, "dayListOnItemClicked: " + position);
        DayListViewDirections.ActionDayListViwerToExerciseListViwer action =
                DayListViewDirections.actionDayListViwerToExerciseListViwer();
        String dayListId = dayListModels.get(position).getDayListId();
        action.setPosition(position);
        action.setProgramListId(programListId);
        action.setDayListId(dayListId);
        controller.navigate(action);
    }
}
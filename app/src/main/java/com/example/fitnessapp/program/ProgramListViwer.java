package com.example.fitnessapp.program;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.fitnessapp.database.AddDay;
import com.example.fitnessapp.databinding.ProgramCurrentProgramViwerBinding;
import com.example.fitnessapp.databinding.ProgramProgramListViwerBinding;
import com.example.fitnessapp.day.DayListViwerArgs;
import com.example.fitnessapp.functions.AlertDialogShower;
import com.example.fitnessapp.mvvm.DayListAdapter;
import com.example.fitnessapp.mvvm.DayListModel;
import com.example.fitnessapp.mvvm.ProgramListAdapter;
import com.example.fitnessapp.mvvm.ProgramListModel;
import com.example.fitnessapp.mvvm.ProgramListViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ProgramListViwer extends Fragment implements ProgramListAdapter.OnProgramListItemClicked {

    private @NonNull
    ProgramProgramListViwerBinding binding;

    private NavController controller;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private ProgramListViewModel programListViewModel;
    private int position;
    private String userId, programListId, name, start_date, end_date;

    private RecyclerView recyclerView;
    private ProgramListAdapter adapter;

    private AlertDialogShower shower;

    private List<ProgramListModel> programListModelsGetter;

    private StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ProgramProgramListViwerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        shower = new AlertDialogShower();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewSetup();
    }



    @Override
    public void onStart() {
        super.onStart();
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_home2_to_createProgram);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        programListViewModel = new ViewModelProvider(getActivity()).get(ProgramListViewModel.class);
        programListViewModel.getProgramListModelData().observe(getViewLifecycleOwner(), new Observer<List<ProgramListModel>>() {
            @Override
            public void onChanged(List<ProgramListModel> programListModels) {
                adapter.setProgramListModels(programListModels);
                adapter.notifyDataSetChanged();
                programListModelsGetter = programListModels;
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
//        Log.d(TAG, "onChanged: " + position + ", " + programListId);

        ProgramListViwerDirections.ActionHome2ToDayListViwer action =
                ProgramListViwerDirections.actionHome2ToDayListViwer();
        action.setPosition(position);
        controller.navigate(action);
    }

    private void recyclerViewSetup() {
        recyclerView = binding.recyclerview;
        adapter = new ProgramListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                position = viewHolder.getAdapterPosition();
                programListId = programListModelsGetter.get(position).getProgramListId();
                name = programListModelsGetter.get(position).getProgramName();
                start_date = programListModelsGetter.get(position).getStart_date();
                end_date = programListModelsGetter.get(position).getEnd_date();

                DocumentReference programRef = firebaseFirestore
                        .collection("user").document(userId)
                        .collection("ProgramList").document(programListId);

//                Log.d(TAG, "onSwiped: " + position + ", " + programListId + ", " + name + ", " + start_date +", " + end_date);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Program");
                builder.setMessage("Are you sure that you want to delete the program below\n"
                        + name + "\n"
                        + "From: " +  start_date + "\n"
                        + "To: " + end_date + "?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                programRef.delete();
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
}
package com.example.fitnessapp.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.functions.AlertDialogShower;
import com.example.fitnessapp.mvvm.adapters.ProgramAdapter;
import com.example.fitnessapp.mvvm.model.ProgramModel;
import com.example.fitnessapp.mvvm.view_model.ProgramListViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class Home extends Fragment {

    private @NonNull
    com.example.fitnessapp.databinding.HomeHomeBinding binding;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private CollectionReference programRef;

    private ProgramAdapter adapter;
    private RecyclerView recyclerView;
    private AlertDialogShower shower;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = com.example.fitnessapp.databinding.HomeHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        shower = new AlertDialogShower();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        AdapterSetup();
        RecyclerViewSetUp();
        adapter.startListening();

        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shower.addProgramDialog(getActivity());
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    private void AdapterSetup() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        programRef = db.collection("users").document(user.getUid()).collection("ProgramList");
        Query query = programRef.orderBy("start_date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ProgramModel> options = new FirestoreRecyclerOptions.Builder<ProgramModel>()
                .setQuery(query, ProgramModel.class)
                .build();

        adapter = new ProgramAdapter(options);
    }

    private void RecyclerViewSetUp() {
        recyclerView = binding.listProgramRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }
}
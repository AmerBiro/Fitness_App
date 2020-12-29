package com.example.fitnessapp.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.functions.AlertDialogShower;
import com.example.fitnessapp.mvvm.adapters.ProgramListAdapter;
import com.example.fitnessapp.mvvm.model.ProgramListModel;
import com.example.fitnessapp.mvvm.view_model.ProgramListViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.List;

public class Home extends Fragment {

    private @NonNull com.example.fitnessapp.databinding.HomeHomeBinding binding;

    private RecyclerView recyclerView;
    private ProgramListViewModel programListViewModel;
    private AlertDialogShower shower;

    private ProgramListAdapter adapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = com.example.fitnessapp.databinding.HomeHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = binding.listProgramRecyclerView;
        adapter = new ProgramListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        shower = new AlertDialogShower();
    }


    @Override
    public void onStart() {
        super.onStart();
        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shower.addProgramDialog(getActivity());
            }
        });
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
            }
        });
    }
}
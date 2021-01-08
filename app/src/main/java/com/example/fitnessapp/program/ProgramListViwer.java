package com.example.fitnessapp.program;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.ProgramCurrentProgramViwerBinding;
import com.example.fitnessapp.databinding.ProgramProgramListViwerBinding;
import com.example.fitnessapp.functions.AlertDialogShower;
import com.example.fitnessapp.mvvm.ProgramListAdapter;
import com.example.fitnessapp.mvvm.ProgramListModel;
import com.example.fitnessapp.mvvm.ProgramListViewModel;

import java.util.List;

public class ProgramListViwer extends Fragment implements ProgramListAdapter.OnProgramListItemClicked {

    private @NonNull
    ProgramProgramListViwerBinding binding;

    private NavController controller;
    private RecyclerView recyclerView;
    private ProgramListViewModel programListViewModel;
    private ProgramListAdapter adapter;
    private AlertDialogShower shower;

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
        recyclerView = binding.programListRecyclerView;
        adapter = new ProgramListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
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
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        ProgramListViwerDirections.ActionHome2ToDayListViwer action =
                ProgramListViwerDirections.actionHome2ToDayListViwer();
        action.setPosition(position);
        controller.navigate(action);
    }
}
package com.example.fitnessapp.program;

import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.ProgramCreateProgramBinding;
import com.example.fitnessapp.databinding.ProgramCurrentProgramViwerBinding;
import com.example.fitnessapp.mvvm.ProgramListModel;
import com.example.fitnessapp.mvvm.ProgramListViewModel;

import java.util.List;

import static android.content.ContentValues.TAG;

public class CurrentProgramViwer extends Fragment {

    private @NonNull
    ProgramCurrentProgramViwerBinding
     binding;
    private ProgramListViewModel programListViewModel;
    private NavController controller;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ProgramCurrentProgramViwerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        position = CurrentProgramViwerArgs.fromBundle(getArguments()).getPosition();
        Log.d(TAG, "currentProgramView: " + position);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        programListViewModel = new ViewModelProvider(getActivity()).get(ProgramListViewModel.class);
        programListViewModel.getProgramListModelData().observe(getViewLifecycleOwner(), new Observer<List<ProgramListModel>>() {
            @Override
            public void onChanged(List<ProgramListModel> programListModels) {
                binding.programName.setText(programListModels.get(position).getProgramName());
                binding.coatchName.setText(programListModels.get(position).getCoachName());
                binding.programFitnessCenter.setText(programListModels.get(position).getFitnessCenter());
                binding.programDays.setText(programListModels.get(position).getDaysNumber());
                binding.programExercises.setText(programListModels.get(position).getExercisesNumber());
                binding.programStartDate.setText(programListModels.get(position).getStart_date());
                binding.programEndDate.setText(programListModels.get(position).getEnd_date());

                Glide
                        .with(getActivity())
                        .load(programListModels.get(position).getImage_url())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_image)
                        .into(binding.image);
            }
        });
    }

}
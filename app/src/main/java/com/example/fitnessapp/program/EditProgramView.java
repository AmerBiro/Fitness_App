package com.example.fitnessapp.program;

import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.fitnessapp.databinding.ProgramEditProgramViewBinding;
import com.example.fitnessapp.mvvm.model.ProgramListModel;
import com.example.fitnessapp.mvvm.viewmodel.ProgramListViewModel;
import com.example.fitnessapp.program.functions.GetProgramData;

import java.util.List;

import static android.content.ContentValues.TAG;

public class EditProgramView extends Fragment {

    private @NonNull
    ProgramEditProgramViewBinding
     binding;
    private ProgramListViewModel programListViewModel;
    private NavController controller;
    private int position;
    private GetProgramData getProgramData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ProgramEditProgramViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        position = EditProgramViewArgs.fromBundle(getArguments()).getPosition();
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
                getProgramData = new GetProgramData(programListModels, position);
                binding.programName.setText(getProgramData.getProgramName());
                binding.coatchName.setText(getProgramData.getCoachName());
                binding.programFitnessCenter.setText(getProgramData.getFitnessCenter());
                binding.programDays.setText(getProgramData.getDaysNumber());
                binding.programExercises.setText(getProgramData.getExercisesNumber());
                binding.programStartDate.setText(getProgramData.getStart_date());
                binding.programEndDate.setText(getProgramData.getEnd_date());

                Glide
                        .with(getActivity())
                        .load(getProgramData.getImage_url())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_image)
                        .into(binding.image);
            }
        });
    }

}
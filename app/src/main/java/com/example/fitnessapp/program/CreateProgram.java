package com.example.fitnessapp.program;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.database.Program;
import com.example.fitnessapp.databinding.ProgramCreateProgramBinding;

public class CreateProgram extends Fragment {

    private @NonNull
    ProgramCreateProgramBinding binding;
    Program program;
    NavController navController;
    private String program_name, coach_name, fitness_center, days, exercises, start_date, end_date;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ProgramCreateProgramBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.createProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                program_name = binding.createProgramName.getText().toString();
                coach_name = binding.createProgramCoatchName.getText().toString();
                fitness_center = binding.createProgramFitnessCenter.getText().toString();
                days = binding.createProgramDays.getText().toString();
                exercises = binding.createProgramExercises.getText().toString();
                start_date = binding.createProgramStartDate.getText().toString();
                end_date = binding.createProgramEndDate.getText().toString();

                program = new Program(program_name, "program_name", coach_name, "coach_name", fitness_center, "fitness_center", days, "days", exercises, "exercises", start_date, "start_date", end_date, "end_date");

                program.createProgram();
                navController.navigate(R.id.action_createProgram_to_home2);
            }
        });

        binding.cancelCreateProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_createProgram_to_home2);
            }
        });

    }

}
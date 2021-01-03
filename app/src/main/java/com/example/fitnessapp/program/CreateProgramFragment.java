package com.example.fitnessapp.program;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.ProgramCreateProgramBinding;
import com.example.fitnessapp.database.ImageHandler;

public class CreateProgramFragment extends Fragment {

    private @NonNull ProgramCreateProgramBinding binding;
    private com.example.fitnessapp.database.CreateProgram createProgram;
    private ImageHandler handler;
    private NavController navController;
    private String program_name, coach_name, fitness_center, days, exercises, start_date, end_date, imageString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ProgramCreateProgramBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        handler = new ImageHandler();
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

                createProgram = new com.example.fitnessapp.database.CreateProgram(program_name, "program_name",
                        coach_name, "coach_name",
                        fitness_center, "fitness_center",
                        days, "days",
                        exercises, "exercises",
                        start_date, "start_date",
                        end_date, "end_date",
                        handler.getImageUri());

                createProgram.createProgram();
                navController.navigate(R.id.action_createProgram_to_home2);
            }
        });

        binding.cancelCreateProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_createProgram_to_home2);
            }
        });

        binding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(1000);
            }
        });

    }

    public void openGallery(int requestCode){
        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGallery, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                handler.uploadeImageToFirebase(data, getActivity(), binding.image);
            }
        }
    }


}
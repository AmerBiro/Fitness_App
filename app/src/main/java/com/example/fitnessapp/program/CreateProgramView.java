package com.example.fitnessapp.program;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.MainActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.database.CreateProgram;
import com.example.fitnessapp.database.ImageHandler;
import com.example.fitnessapp.databinding.ProgramCreateProgramBinding;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateProgramView extends Fragment implements View.OnClickListener {

    private @NonNull
    ProgramCreateProgramBinding binding;
    private CreateProgram createProgram;
    private ImageHandler handler;
    private NavController navController;
    private String programName, coachName, fitnessCenter, daysNumber, exercisesNumber, start_date, end_date, image_url;
    private DatePickerDialog.OnDateSetListener startDate, endDate;


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
        binding.create.setOnClickListener(this);
        binding.imageCreateProgram.setOnClickListener(this);
        binding.startDate.setOnClickListener(this);
        binding.endDate.setOnClickListener(this);


    }

    public void openGallery(int requestCode) {
//        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(openGallery, requestCode);

        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "select picture"), 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            handler.uploadeImageToFirebase(data, getActivity(), binding.imageCreateProgram);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_create_program:
            openGallery(1000);
            break;
            case R.id.create:
                programName = binding.programName.getText().toString();
                coachName = binding.coatchName.getText().toString();
                fitnessCenter = binding.programFitnessCenter.getText().toString();
                daysNumber = binding.numberDays.getText().toString();
                exercisesNumber = binding.numberExercises.getText().toString();
                start_date = binding.startDate.getText().toString();
                end_date = binding.endDate.getText().toString();

                createProgram = new CreateProgram(
                        programName, coachName, fitnessCenter, daysNumber, exercisesNumber,
                        start_date, end_date, handler.getImageUri());
                createProgram.createProgram();
                navController.navigate(R.id.action_createProgram_to_home2);
                break;
            default:
        }

    }
}
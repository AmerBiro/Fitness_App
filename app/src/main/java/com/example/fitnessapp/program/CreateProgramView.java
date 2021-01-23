package com.example.fitnessapp.program;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.create.ImageHandler;
import com.example.fitnessapp.databinding.ProgramCreateProgramViewBinding;
import com.example.fitnessapp.functions.FieldChecker;
import com.example.fitnessapp.program.functions.CreateProgram;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class CreateProgramView extends Fragment implements View.OnClickListener {

    private @NonNull
    ProgramCreateProgramViewBinding binding;
    private CreateProgram createProgram;
    private NavController controller;
    private DatePickerDialog.OnDateSetListener startDate, endDate;
    private Uri uri;
    private int imagePosition;
    private int years, months, days;
    private FieldChecker checker;
    private EditText [] fields;
    private String [] errorMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ProgramCreateProgramViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        createProgram = new CreateProgram();
        createProgram.setViewController(controller, view);
        imagePosition = 1000;
        checker = new FieldChecker();
        fields = new EditText[8];
        errorMessage = new String[1];

        errorMessage[0] = "Invalid Input";

        fields[0] = binding.programNumber;
        fields[1] = binding.programName;
        fields[2] = binding.coatchName;
        fields[3] = binding.programFitnessCenter;
        fields[4] = binding.numberDays;
        fields[5] = binding.numberExercises;
        fields[6] = binding.startDate;
        fields[7] = binding.endDate;

        Calendar start_date = Calendar.getInstance();
        years = start_date.get(Calendar.YEAR);
        months = start_date.get(Calendar.MONTH);
        days = start_date.get(Calendar.DATE);

        binding.startDateSelector.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), (view1, year, month, dayOfMonth) -> {
                months = month + 1;
                days = dayOfMonth;
                years = year;
                String date = days + "/" + months + "/" + years;
                binding.startDate.setText(date);
            }, years, months, days);
            datePickerDialog.show();
        });

        binding.endDateSelector.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), (view1, year, month, dayOfMonth) -> {
                months = month + 1;
                days = dayOfMonth;
                years = year;
                String date = days + "/" + months + "/" + years;
                binding.endDate.setText(date);
            }, years, months, days);
            datePickerDialog.show();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.create.setOnClickListener(this);
        binding.imageCreateProgram.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_create_program:
                openGallery(imagePosition);
                break;
            case R.id.create:
                if (uri != null){
                    if (!checker.isEmpty(fields, errorMessage)){
                        createProgram.setParameters(
                                Integer.parseInt(binding.programNumber.getText().toString()),
                                binding.programName.getText().toString(),
                                binding.coatchName.getText().toString(),
                                binding.programFitnessCenter.getText().toString(),
                                Integer.parseInt(binding.numberDays.getText().toString()),
                                Integer.parseInt(binding.numberExercises.getText().toString()),
                                binding.startDate.getText().toString(),
                                binding.endDate.getText().toString(),
                                uri
                        );
                        createProgram.createProgram(binding.create, binding.progressBarProgram, R.id.action_createProgram_to_home2);
                    }
                }else{
                    Toast.makeText(getActivity(), "An image must be selected" , 0).show();
                    return;
                }
                break;
            default:
        }
    }

    public void openGallery(int requestCode) {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "select picture"), imagePosition);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imagePosition && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            String image_url = uri.toString();
            Picasso
                    .get()
                    .load(image_url)
                    .fit()
                    .into(binding.imageCreateProgram);
        }
    }




}
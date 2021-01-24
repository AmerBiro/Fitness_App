package com.example.fitnessapp.program;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.ProgramEditProgramViewBinding;
import com.example.fitnessapp.functions.FieldChecker;
import com.example.fitnessapp.mvvm.model.ProgramListModel;
import com.example.fitnessapp.mvvm.viewmodel.ProgramListViewModel;
import com.example.fitnessapp.program.functions.CreateProgram;
import com.example.fitnessapp.program.functions.EditProgram;
import com.example.fitnessapp.program.functions.GetProgramData;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class EditProgramView extends Fragment implements View.OnClickListener {

    private @NonNull
    ProgramEditProgramViewBinding
            binding;
    private ProgramListViewModel programListViewModel;
    private int position;
    private GetProgramData getProgramData;
    private EditProgram editProgram;
    private NavController controller;
    private DatePickerDialog.OnDateSetListener startDate, endDate;
    private Uri uri;
    private int imagePosition;
    private int years, months, days;
    private FieldChecker checker;
    private EditText[] fields;
    private String[] errorMessage;

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
        Log.d(TAG, "onViewCreated: " + position);
        editProgram = new EditProgram();
        editProgram.setViewController(controller, view);
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
        binding.edit.setOnClickListener(this);
        binding.imageEditProgram.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        programListViewModel = new ViewModelProvider(getActivity()).get(ProgramListViewModel.class);
        programListViewModel.getProgramListModelData().observe(getViewLifecycleOwner(), new Observer<List<ProgramListModel>>() {
            @Override
            public void onChanged(List<ProgramListModel> programListModels) {
                getProgramData = new GetProgramData(programListModels, position);
                binding.programNumber.setText(getProgramData.getNumber() + "");
                binding.programName.setText(getProgramData.getProgramName());
                binding.coatchName.setText(getProgramData.getCoachName());
                binding.programFitnessCenter.setText(getProgramData.getFitnessCenter());
                binding.numberDays.setText(getProgramData.getDaysNumber() + "");
                binding.numberExercises.setText(getProgramData.getExercisesNumber() + "");
                binding.startDate.setText(getProgramData.getStart_date());
                binding.endDate.setText(getProgramData.getEnd_date());

                Picasso
                        .get()
                        .load(getProgramData.getImage_url())
                        .fit()
                        .into(binding.imageEditProgram);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_edit_program:
                openGallery(imagePosition);
                break;
            case R.id.edit:
                if (!checker.isEmpty(fields, errorMessage)) {
                    editProgram.setParameters(
                            getProgramData.getProgramListId(),
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
                    editProgram.editProgram(binding.edit, binding.progressBarProgram, R.id.action_currentProgramViwer_to_home2);
                } else {
                    Toast.makeText(getActivity(), "An image must be selected", 0).show();
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
                    .into(binding.imageEditProgram);
        }
    }


}
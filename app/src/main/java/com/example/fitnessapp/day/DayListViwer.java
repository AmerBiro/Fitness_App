package com.example.fitnessapp.day;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.DayDayListViwerBinding;

import static android.content.ContentValues.TAG;


public class DayListViwer extends Fragment implements View.OnClickListener {

    private @NonNull
    DayDayListViwerBinding
     binding;
    private NavController controller;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DayDayListViwerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        position = DayListViwerArgs.fromBundle(getArguments()).getPosition();
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.about.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about:
                DayListViwerDirections.ActionDayListViwerToCurrentProgramViwer action =
                        DayListViwerDirections.actionDayListViwerToCurrentProgramViwer();
                action.setPosition(position);
                controller.navigate(action);
                break;
            default:
        }
    }
}
package com.example.fitnessapp.functions;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class DatePicker {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH);
    private int day = calendar.get(Calendar.DAY_OF_MONTH);

    public DatePicker() {
    }

    public void datePicker(Activity activity, TextView textView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                android.R.style.Theme_Holo_Panel,
                dateSetListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                textView.setVisibility(View.VISIBLE);
                month++;
                String date = dayOfMonth + "/" + month + "/" + year;
                textView.setText(date);
            }
        };
    }

}

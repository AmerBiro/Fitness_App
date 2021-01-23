package com.example.fitnessapp.day.functions;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.example.fitnessapp.mvvm.adapter.DayListAdapter;
import com.example.fitnessapp.mvvm.adapter.ProgramListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class DeleteDay {

    private Activity activity;
    private DayListAdapter adapter;

    public DeleteDay(Activity activity, DayListAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void deleteDay(String programListId, String dayId, String dayNumber, String dayName, String day_exercise_number) {
        Log.d(TAG, "deleteDay: " + programListId + ", " + dayId);
        DocumentReference dayRef = FirebaseFirestore.getInstance()
                .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ProgramList").document(programListId)
                .collection("DayList").document(dayId);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Delete day");
        builder.setMessage("Are you sure that you want to delete the following day?\n"
                + "Day " + dayNumber + ", " + day_exercise_number + " exercise" + " \n"
                + dayName)
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dayRef.delete();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        adapter.notifyDataSetChanged();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                adapter.notifyDataSetChanged();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

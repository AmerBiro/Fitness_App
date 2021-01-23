package com.example.fitnessapp.program.functions;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.fitnessapp.mvvm.adapter.ProgramListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteProgram {

    private String programListId, name, start_date, end_date;
    private Activity activity;
    private ProgramListAdapter adapter;

    public DeleteProgram(Activity activity, ProgramListAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void deleteProgram(String programListId, String name, String start_date, String end_date) {
        this.programListId = programListId;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;

        DocumentReference programRef = FirebaseFirestore.getInstance()
                .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ProgramList").document(this.programListId);

//                Log.d(TAG, "onSwiped: " + position + ", " + programListId + ", " + name + ", " + start_date +", " + end_date);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Delete Program");
        builder.setMessage("Are you sure that you want to delete the following program:\n"
                + name + "\n"
                + "From: " +  start_date + "\n"
                + "To: " + end_date + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        programRef.delete();
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

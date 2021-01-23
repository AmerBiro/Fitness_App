package com.example.fitnessapp.program.functions;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class EditProgram {

    private String programId, programName, coachName, fitnessCenter, daysNumber, exercisesNumber, start_date, end_date;
    private Uri uri;
    private int number;

    public void editProgram(String programId, int number, String programName, String coachName, String fitnessCenter, String daysNumber, String exercisesNumber, String start_date, String end_date, Uri uri) {
        this.number = number;
        this.programName = programName;
        this.coachName = coachName;
        this.fitnessCenter = fitnessCenter;
        this.daysNumber = daysNumber;
        this.exercisesNumber = exercisesNumber;
        this.start_date = start_date;
        this.end_date = end_date;
        this.uri = uri;
        this.programId = programId;

        StorageReference programImage = FirebaseStorage.getInstance().getReference()
                .child("user")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(programId);

        programImage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                programImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String image_url = uri.toString();

                        DocumentReference updateProgramRef = FirebaseFirestore.getInstance()
                                .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .collection("ProgramList").document(programId);

                        Map<String, Object> program = new HashMap<>();

                        program.put("number", number);
                        program.put("programName", programName);
                        program.put("coachName", coachName);
                        program.put("fitnessCenter", fitnessCenter);
                        program.put("daysNumber", daysNumber);
                        program.put("exercisesNumber", exercisesNumber);
                        program.put("start_date", start_date);
                        program.put("end_date", end_date);
                        program.put("image_url", image_url);

                        updateProgramRef.update(program).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + "Error updating program " + e.getMessage());
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + "Error downloading image " + e.getMessage());
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + "Error uploading image " + e.getMessage());
            }
        });

    }


}

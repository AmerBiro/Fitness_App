package com.example.fitnessapp.program.functions;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CreateProgram {

    private String programName, coachName, fitnessCenter, start_date, end_date;
    private int number, daysNumber, exercisesNumber;
    private Uri uri;
    private NavController controller;
    private View view;



    public void setViewController(NavController controller, View view) {
        this.controller = controller;
        this.view = view;
        this.controller = Navigation.findNavController(view);
    }

    public void setParameters(int number, String programName, String coachName, String fitnessCenter,
                              int daysNumber, int exercisesNumber,
                         String start_date, String end_date,
                         Uri uri) {
        this.number = number;
        this.programName = programName;
        this.coachName = coachName;
        this.fitnessCenter = fitnessCenter;
        this.daysNumber = daysNumber;
        this.exercisesNumber = exercisesNumber;
        this.start_date = start_date;
        this.end_date = end_date;
        this.uri = uri;
    }

    public CreateProgram() {
    }

    public void createProgram(Button button, ProgressBar progressBar, int action) {
        CollectionReference programRef = FirebaseFirestore.getInstance()
                .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ProgramList");
        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Map<String, Object> program = new HashMap<>();

        program.put("number", this.number);
        program.put("programName", this.programName);
        program.put("coachName", this.coachName);
        program.put("fitnessCenter", this.fitnessCenter);
        program.put("daysNumber", this.daysNumber);
        program.put("exercisesNumber", this.exercisesNumber);
        program.put("start_date", this.start_date);
        program.put("end_date", this.end_date);

        programRef.add(program).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "onSuccess: " + "Adding a program document successfully " +  documentReference.get());

                CollectionReference dayRef = FirebaseFirestore.getInstance()
                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("ProgramList").document(documentReference.getId())
                        .collection("DayList");

                HashMap<String, Object> day = new HashMap<>();

                for (int i = 0; i<daysNumber; i++){
                    day.put("day_number", i+1);
                    day.put("day_name", "");
                    day.put("day_exercise_number", 0);
                    dayRef.add(day);
                }

                StorageReference programImage = FirebaseStorage.getInstance().getReference()
                        .child("user")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("ProgramList")
                        .child(documentReference.getId())
                        .child(documentReference.getId());

                programImage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess: " + "Successfully uploading program image");

                        programImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.d(TAG, "onSuccess: " + "Successfully downloading program image");

                                DocumentReference programImageRef = FirebaseFirestore.getInstance()
                                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection("ProgramList").document(documentReference.getId());

                                Map<String, Object> programImage = new HashMap<>();
                                programImage.put("image_url", uri.toString());

                                programImageRef.update(programImage).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: " + "Successfully updating program data with program image url");
                                        progressBar.setVisibility(View.INVISIBLE);
                                        controller.navigate(action);
                                        controller.navigateUp();
                                        controller.popBackStack();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + "Error updating program image with image url " + e.getMessage());
                                        progressBar.setVisibility(View.INVISIBLE);
                                        button.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + "Error downloading program image " + e.getMessage());
                                progressBar.setVisibility(View.INVISIBLE);
                                button.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + "Error uploading program image  " + e.getMessage());
                        progressBar.setVisibility(View.INVISIBLE);
                        button.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + "Error adding program document " +  e.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
            }
        });
    }

}

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class EditProgram {

    private String programId, programName, coachName, fitnessCenter, start_date, end_date;
    private int number, daysNumber, exercisesNumber;
    private Uri uri;
    private NavController controller;
    private View view;

    public void setViewController(NavController controller, View view) {
        this.controller = controller;
        this.view = view;
        this.controller = Navigation.findNavController(view);
    }

    public void setParameters(String programId,
                              int number, String programName, String coachName, String fitnessCenter,
                              int daysNumber, int exercisesNumber,
                              String start_date, String end_date,
                              Uri uri) {
        this.programId = programId;
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

    public EditProgram() {
    }

    public void editProgram(Button button, ProgressBar progressBar, int action) {

        DocumentReference editProgramRef = FirebaseFirestore.getInstance()
                .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ProgramList").document(programId);

        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Map<String, Object> editProgram = new HashMap<>();

        editProgram.put("number", this.number);
        editProgram.put("programName", this.programName);
        editProgram.put("coachName", this.coachName);
        editProgram.put("fitnessCenter", this.fitnessCenter);
        editProgram.put("daysNumber", this.daysNumber);
        editProgram.put("exercisesNumber", this.exercisesNumber);
        editProgram.put("start_date", this.start_date);
        editProgram.put("end_date", this.end_date);

        editProgramRef.update(editProgram).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: " + "Updating successfully " + programId);

                if (uri != null){
                    StorageReference programImage = FirebaseStorage.getInstance().getReference()
                            .child("user")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("ProgramList")
                            .child(programId)
                            .child(programId);

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
                                            .collection("ProgramList").document(programId);

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
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    controller.navigate(action);
                    controller.navigateUp();
                    controller.popBackStack();
                }

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

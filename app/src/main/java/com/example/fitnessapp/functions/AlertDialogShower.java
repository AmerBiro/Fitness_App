package com.example.fitnessapp.functions;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AlertDialogShower {

    private Activity activity;
    private View view;
    private String userId, programListId;
    private String day_name, day_number, day_exercise_number;

    public AlertDialogShower(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    public AlertDialogShower() {

    }

    public void forgotPasswordDialog(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.alert_dialog_forgot_password);
        dialog.getWindow().getAttributes().windowAnimations = R.style.alert_dialog_animation;
        EditText email = dialog.findViewById(R.id.id_email);
        Button send_a_link = dialog.findViewById(R.id.id_send_a_link);
        Button cancel = dialog.findViewById(R.id.id_cancel);
        dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        send_a_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()) {
                    email.setError("Please, enter your email");
                    return;
                } else {
                    Toast.makeText(activity, "A link has been sent to you", 0).show();
                    dialog.cancel();
                }
            }
        });
    }

    public void createAccountDialog(Activity activity, View view) {
        NavController navController = Navigation.findNavController(view);
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.alert_dialog_create_account);
        dialog.getWindow().getAttributes().windowAnimations = R.style.alert_dialog_animation;
        dialog.show();
        LinearLayout email = dialog.findViewById(R.id.create_Account_email);
        LinearLayout microsoft = dialog.findViewById(R.id.create_Account_microsoft);
        LinearLayout google = dialog.findViewById(R.id.create_Account_google);
        LinearLayout facebook = dialog.findViewById(R.id.create_Account_facebook);
        Button cancel = dialog.findViewById(R.id.create_Account_cancel);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registration_to_create_Account);
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }


    public void addDay(String userId, String programListId) {
        this.userId = userId;
        this.programListId = programListId;
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.day_create_day_view);
        dialog.getWindow().getAttributes().windowAnimations = R.style.alert_dialog_animation;
        dialog.setCancelable(true);
        dialog.show();
        TextView dayNumber, dayName, day_exercise_number;
        Button addDay;
        dayNumber = dialog.findViewById(R.id.create_day_day_number);
        dayName = dialog.findViewById(R.id.create_day_name);
        day_exercise_number = dialog.findViewById(R.id.create_day_number_of_exercises);
        addDay = dialog.findViewById(R.id.create_day_add_day);

        addDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference dayRef = FirebaseFirestore.getInstance()
                        .collection("user").document(userId)
                        .collection("ProgramList").document(programListId)
                        .collection("DayList");

                Map<String, Object> day = new HashMap<>();
                day.put("day_number", dayNumber.getText().toString());
                day.put("day_name", dayName.getText().toString());
                day.put("day_exercise_number", day_exercise_number.getText().toString());
                dayRef.add(day).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful())
                            dialog.cancel();
                    }
                });
            }
        });

//        addDay.setOnClickListener(this);
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.create_day_add_day:
//                CollectionReference dayRef = FirebaseFirestore.getInstance()
//                        .collection("user").document(userId)
//                        .collection("ProgramList").document(programListId)
//                        .collection("DayList");
//
//                Map<String, Object> day = new HashMap<>();
//                if (day_number.trim().isEmpty())
//                    day_number = "0";
//                day.put("day_number", day_number);
//                day.put("day_name", day_name);
//                day.put("day_exercise_number", day_exercise_number);
//                dayRef.add(day);
//                break;
//            default:
//        }
//    }
}

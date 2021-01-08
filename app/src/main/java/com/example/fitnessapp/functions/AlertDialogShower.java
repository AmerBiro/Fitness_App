package com.example.fitnessapp.functions;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AlertDialogShower {


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
                }else{
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



}

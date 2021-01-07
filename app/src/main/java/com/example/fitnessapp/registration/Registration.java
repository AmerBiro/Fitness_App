package com.example.fitnessapp.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.RegistrationRegistrationBinding;
import com.example.fitnessapp.functions.AlertDialogShower;
import com.example.fitnessapp.functions.FieldChecker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Registration extends Fragment {

    private @NonNull RegistrationRegistrationBinding binding;
    private NavController navController;
    private FieldChecker checker;
    private AlertDialogShower shower;
    private EditText [] fields;
    private String [] errormessage;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationRegistrationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        shower = new AlertDialogShower();
        checker = new FieldChecker();
        fields = new EditText[2];
        errormessage = new String[2];
        fields[0] = binding.idUsername; fields[1] = binding.idPassword;
        errormessage[0] = "Invalid username"; errormessage[1] = "Invalid password";
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        binding.idForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shower.forgotPasswordDialog(getActivity());
            }
        });

        binding.idCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shower.createAccountDialog(getActivity(), v);
            }
        });

    }

    public void signIn(){
        if (checker.isEmpty(fields, errormessage))
            return;
        else{
            firebaseAuth.signInWithEmailAndPassword(fields[0].getText().toString(), fields[1].getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            navController.navigate(R.id.action_registration_to_home2);
                            Toast.makeText(getContext(), "A new account is created successfully", 0).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), 0).show();
                    return;
                }
            });
        }
    }

}
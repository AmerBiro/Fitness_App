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

import com.example.fitnessapp.databinding.RegistrationRegistrationBinding;
import com.example.fitnessapp.functions.AlertDialogShower;
import com.example.fitnessapp.functions.FieldChecker;


public class Registration extends Fragment {

    private @NonNull RegistrationRegistrationBinding binding;
    private NavController navController;
    private FieldChecker checker;
    private AlertDialogShower shower;
    private EditText [] fields;
    private String [] errormessage;

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
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.idSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
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

    public void check(){
        if (checker.isEmpty(fields, errormessage))
            return;
    }

}
package com.example.fitnessapp.registration;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.RegistrationCreateAccountBinding;
import com.example.fitnessapp.functions.ButtonCreateAccount;
import com.example.fitnessapp.functions.DatePicker;
import com.example.fitnessapp.functions.FieldChecker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Create_Account extends Fragment {


    private @NonNull RegistrationCreateAccountBinding binding;
    private FieldChecker checker;
    private DatePicker datePicker;
    private ButtonCreateAccount buttonCreateAccount;
    private EditText [] fields, password;
    private String [] errormessage;
    private NavController navController;
    View createAccountButton;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationCreateAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        createAccountButton = view.findViewById(R.id.id_create_account_button);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        checker = new FieldChecker();
        datePicker = new com.example.fitnessapp.functions.DatePicker();
        fields = new EditText[5];
        password = new EditText[2];
        errormessage = new String[5];
        fields[0] = binding.idFirstName;
        fields[1] = binding.idLastName;
        fields[2] = binding.idEmail;
        fields[3] = binding.idPassword;
        fields[4] = binding.idConfirmPassword;
        errormessage[0] = "Invalid first name";
        errormessage[1] = "Invalid last name";
        errormessage[2] = "Invalid email address";
        errormessage[3] = "Invalid password";
        errormessage[4] = "Invalid password";
        password[0] = binding.idPassword;
        password[1] = binding.idConfirmPassword;
    }

    @Override
    public void onStart() {
        super.onStart();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check())
                    createAccount(v);
            }
        });

        binding.idSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.idDate);
            }
        });
    }

    public Boolean check(){
        if (checker.isEmpty(fields, errormessage) ||
                !checker.correspondingPasswordIsCorrect(password) ||
                !checker.genderIsSelected(getActivity(), binding.idRadioGroup, binding.idGenderNotSelected) ||
                checker.dateIsEmpty(binding.idDate)
        )
            return false;
        else{
            return true;
        }
    }

    public void createAccount(View v) {
        buttonCreateAccount = new ButtonCreateAccount(getActivity(), v);
        buttonCreateAccount.buttonActivited();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    buttonCreateAccount.buttonFinished();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            navController.navigate(R.id.action_create_Account_to_body2);
                        }
                    },1500);
                }
            },2000);
    }





}
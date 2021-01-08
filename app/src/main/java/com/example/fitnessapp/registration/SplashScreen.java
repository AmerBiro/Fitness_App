package com.example.fitnessapp.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.HomeSplashScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends Fragment {
    private FirebaseAuth firebaseAuth;
    private static final String userStatus = "";
    private NavController navController;
    private @NonNull HomeSplashScreenBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomeSplashScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(view);
        binding.status.setText("Checking User Account...");
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    navController.navigate(R.id.action_splashScreen_to_registration);
                }
            },2000);

//            binding.status.setText("Creating Account...");
//            firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        binding.status.setText("Account Created...");
//                        navController.navigate(R.id.action_splashScreen_to_home2);
//                    }else
//                        Log.d(userStatus, "onComplete: " + task.getException());
//                }
//            });
        }else{
            binding.status.setText("Logged in...");
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    navController.navigate(R.id.action_splashScreen_to_home2);
//                }
//            },2000);
            navController.navigate(R.id.action_splashScreen_to_home2);

        }
    }
}
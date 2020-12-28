package com.example.fitnessapp.functions;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class User {
    public User() {
    }

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();

    private String createAccountStatus;

    private ProgressBar progressBar;
    private TextView textView;
    private Button checkMark;
    private Button error;

    public void createUser(View view, String email, String password) {
        NavController navController = Navigation.findNavController(view);
        progressBar = view.findViewById(R.id.id_progressBar);
        textView = view.findViewById(R.id.id_create_account_text_view);
        checkMark = view.findViewById(R.id.id_check_mark);
        error = view.findViewById(R.id.id_error);

        progressBar.setVisibility(View.VISIBLE);
        checkMark.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        textView.setText("Please wait...");

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("Done");
                                    progressBar.setVisibility(View.GONE);
                                    checkMark.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            navController.navigate(R.id.action_create_Account_to_body2);
                                        }
                                    },1500);
                                }
                            },1500);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(createAccountStatus, "createAccountStatus: " + e.getMessage());
//                Toast.makeText(activity, "Account not created! " + e.getMessage(), 1).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Error");
                                progressBar.setVisibility(View.GONE);
                                checkMark.setVisibility(View.GONE);
                                error.setVisibility(View.VISIBLE);
                            }
                        },1500);
                    }
                });

    }


    public void signIn(Activity activity, ProgressBar progressBar, EditText email, EditText password) {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, "Sign in successfully", 0).show();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Sign in error" + e.getMessage(), 1).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public Boolean signOut(Activity activity) {
        if (user != null) {
            mAuth.signOut();
            Toast.makeText(activity, "Signed out successfully", 0).show();
            return true;
        } else return false;
    }


    public void verifyUser(Activity activity) {
        if (user != null) {
            if (!user.isEmailVerified()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("You have not verified your email yet!");
                builder.setMessage("Do you want to verify your email?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(activity, "A verification email has been sent to: \n" + user.getEmail(), 0).show();
                                        mAuth.signOut();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(activity, "Email verification was not sent!" + e.getMessage(), 1).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(activity, "You cannot see or edit your profile before verifying your email!", 1).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
            }
        }
    }


    public void updateEmail(Activity activity, String newEmail) {
        user.updateEmail(newEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(activity, "Email updated successfully1", 0).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Email not updated " + e.getMessage(), 1).show();
            }
        });
    }


    public void resetPassword(Activity activity, View v) {
        final EditText resetPassword = new EditText(v.getContext());
        resetPassword.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        final AlertDialog.Builder resetPasswordDialog = new AlertDialog.Builder(v.getContext());
        resetPasswordDialog.setTitle("Reset Password");
        resetPasswordDialog.setMessage("You can receive a link to reset your password by entering your email down below");
        resetPasswordDialog.setView(resetPassword);
        resetPasswordDialog.setPositiveButton("Send me a reset link", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = resetPassword.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(activity, "You have not entered your email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity, "A reset password link is sent to " + email, 0).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "An error has been occured!\n" + e.getMessage(), 1).show();
                    }
                });
            }
        });
        resetPasswordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        resetPasswordDialog.create().show();
    }


    public void deleteUser(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Delete account")
                .setMessage("Are you sure you want to delete the following profile " + user.getEmail() + "?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity, "The following profile: " + user.getEmail() + " has been deleted successfully", 0).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Unable to delete profile " + e.getMessage(), 1).show();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}

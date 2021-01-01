package com.example.fitnessapp.buttons;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.fitnessapp.R;

public class ButtonCreateAccount {

    private ProgressBar progressBar;
    private TextView textView;
    private Button button;

    public ButtonCreateAccount(Activity activity, View view){
        progressBar = view.findViewById(R.id.id_progressBar);
        textView = view.findViewById(R.id.id_create_account_text_view);
        button = view.findViewById(R.id.id_check_mark);
    }

    public void buttonActivited(){
        progressBar.setVisibility(View.VISIBLE);
        textView.setText("Please wait...");
    }

    public void buttonFinished(){
        progressBar.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
        textView.setText("Done");
    }

}

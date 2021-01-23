package com.example.fitnessapp.functions;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class FieldChecker {

    private String gender;
    private int selected;
    private RadioButton radioButton;
    private Boolean status = false;
    private Boolean b_password = false;

    public FieldChecker() {

    }

    public Boolean isEmpty(EditText[] fields, String [] errorMessage){
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getText().toString().isEmpty()) {
                    fields[i].setError(errorMessage[0]);
                }
            }
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getText().toString().isEmpty()) {
                    status = true;
                    return true;
                }else status = false;
            }
        return status;
    }

    public Boolean correspondingPasswordIsCorrect(EditText[] password){
        if (password[0].getText().toString().equals(password[1].getText().toString())) {
            status = true;
            return true;
        }else {
            password[1].setError("Incorrect password");
            status = false;
        }
        return status;
    }

    public Boolean dateIsEmpty(TextView textView){
        if (textView.getText().toString().isEmpty() || textView.getText().toString().equals("xx/xx/xxxx")){
            textView.setVisibility(View.VISIBLE);
            textView.setError("A birth date must be selected");
            return true;
        } else return false;
    }

    public Boolean genderIsSelected(Activity activity, RadioGroup radioGroup, TextView genderIsSelected){
        this.selected = radioGroup.getCheckedRadioButtonId();
        if (selected == -1){
            genderIsSelected.setText("Gender not selected");
            genderIsSelected.setVisibility(View.VISIBLE);
            return false;
        } else {
            this.radioButton = activity.findViewById(selected);
            this.gender = radioButton.getText().toString();
            genderIsSelected.setVisibility(View.INVISIBLE);
            return true;
        }
    }


}

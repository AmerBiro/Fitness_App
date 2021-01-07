package com.example.fitnessapp.buttons;

import android.view.View;

public interface CustomButton {

    public void setView(View view);

    public void setDefaultText(String s);

    public void onClick(String s);

    public void onSuccess(String s);

    public void onFailure(String s);

    public void onRepeat(String s);

    public View getView();



}

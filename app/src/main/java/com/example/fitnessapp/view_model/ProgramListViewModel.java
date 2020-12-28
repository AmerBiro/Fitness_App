package com.example.fitnessapp.view_model;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnessapp.firebase_repo.FirebaseRepository;
import com.example.fitnessapp.model.ProgramListModel;

import java.util.List;


public class ProgramListViewModel extends ViewModel implements FirebaseRepository.OnFireStoreTaskComplete {

    private MutableLiveData<List<ProgramListModel>> programListModelData = new MutableLiveData<>();

    public LiveData<List<ProgramListModel>> getProgramListModelData() {
        return programListModelData;
    }


    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public ProgramListViewModel() {
        firebaseRepository.getProgramData();
    }

    @Override
    public void programListDataAdded(List<ProgramListModel> programListModelsList) {
        programListModelData.setValue(programListModelsList);
    }

    @Override
    public void onError(Exception e) {

    }
}
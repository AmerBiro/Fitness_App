package com.example.fitnessapp.mvvm.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnessapp.mvvm.firebase_repo.FirebaseRepository;
import com.example.fitnessapp.mvvm.model.ProgramModel;

import java.util.List;


public class ProgramListViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<ProgramModel>> programListModelData = new MutableLiveData<>();

    public LiveData<List<ProgramModel>> getProgramListModelData() {
        return programListModelData;
    }

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public ProgramListViewModel() {
        firebaseRepository.getQuizData();
    }

    @Override
    public void programListDataAdded(List<ProgramModel> programListModels) {
        programListModelData.setValue(programListModels);
    }

    @Override
    public void onError(Exception e) {

    }
}

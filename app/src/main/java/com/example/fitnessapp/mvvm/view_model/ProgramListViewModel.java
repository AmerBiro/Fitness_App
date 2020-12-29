package com.example.fitnessapp.mvvm.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnessapp.mvvm.firebase_repo.FirebaseRepository;
import com.example.fitnessapp.mvvm.model.ProgramListModel;

import java.util.List;


public class ProgramListViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<ProgramListModel>> programListModelData = new MutableLiveData<>();

    public LiveData<List<ProgramListModel>> getProgramListModelData() {
        return programListModelData;
    }

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public ProgramListViewModel() {
        firebaseRepository.getQuizData();
    }

    @Override
    public void programListDataAdded(List<ProgramListModel> programListModelsList) {
        programListModelData.setValue(programListModelsList);
    }

    @Override
    public void onError(Exception e) {

    }
}

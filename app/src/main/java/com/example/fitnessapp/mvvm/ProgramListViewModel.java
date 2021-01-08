package com.example.fitnessapp.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ProgramListViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<ProgramListModel>> programListModelData = new MutableLiveData<>();

    public LiveData<List<ProgramListModel>> getProgramListModelData() {
        return programListModelData;
    }

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public ProgramListViewModel() {
        firebaseRepository.getProgramListData();
    }

    @Override
    public void programListDataAdded(List<ProgramListModel> programListModels) {
        programListModelData.setValue(programListModels);
    }

    @Override
    public void onError(Exception e) {

    }
}

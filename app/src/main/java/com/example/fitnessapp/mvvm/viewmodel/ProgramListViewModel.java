package com.example.fitnessapp.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnessapp.mvvm.FirebaseRepository;
import com.example.fitnessapp.mvvm.model.DayListModel;
import com.example.fitnessapp.mvvm.model.ProgramListModel;

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
    public void dayListDataAdded(List<DayListModel> dayListModels) {

    }

    @Override
    public void onError(Exception e) {

    }
}

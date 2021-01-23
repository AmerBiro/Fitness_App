package com.example.fitnessapp.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnessapp.mvvm.FirebaseRepository;
import com.example.fitnessapp.mvvm.model.DayListModel;
import com.example.fitnessapp.mvvm.model.ProgramListModel;

import java.util.List;


public class DayListViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<DayListModel>> dayListModelData = new MutableLiveData<>();

    public LiveData<List<DayListModel>> getDayListModelData() {
        return dayListModelData;
    }

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public DayListViewModel() {
        firebaseRepository.getProgramListData();
    }

    @Override
    public void programListDataAdded(List<ProgramListModel> programListModels) {

    }

    @Override
    public void dayListDataAdded(List<DayListModel> dayListModels) {
        dayListModelData.setValue(dayListModels);
    }

    @Override
    public void onError(Exception e) {

    }
}

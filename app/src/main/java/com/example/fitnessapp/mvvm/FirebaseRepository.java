package com.example.fitnessapp.mvvm;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.fitnessapp.mvvm.model.DayListModel;
import com.example.fitnessapp.mvvm.model.ProgramListModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static android.content.ContentValues.TAG;


public class FirebaseRepository {

    public FirebaseRepository() {
    }

    private FirebaseRepository.OnFirestoreTaskComplete onFirestoreTaskComplete;

    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private Query programListRef = FirebaseFirestore.getInstance()
            .collection("user").document(userId)
            .collection("ProgramList")
            .orderBy("number");

    public FirebaseRepository(FirebaseRepository.OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void getProgramListData() {
        programListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d(TAG, "onEvent: " + value.getDocumentChanges().toString());
                List<ProgramListModel> programListModels = value.toObjects(ProgramListModel.class);
                onFirestoreTaskComplete.programListDataAdded(programListModels);
            }
        });
    }


    public interface OnFirestoreTaskComplete {
        void programListDataAdded(List<ProgramListModel> programListModels);

        void onError(Exception e);
    }

}

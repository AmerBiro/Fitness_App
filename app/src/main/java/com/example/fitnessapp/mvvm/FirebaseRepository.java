package com.example.fitnessapp.mvvm;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    private FirebaseRepository.OnFirestoreTaskComplete onFirestoreTaskComplete;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private String userId = firebaseUser.getUid();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Query programListRef = firebaseFirestore
            .collection("user").document(userId)
            .collection("ProgramList").orderBy("start_date");

    public FirebaseRepository(FirebaseRepository.OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void getProgramListData() {
        programListRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d(TAG, "onEvent: " + value.getDocumentChanges().toString());
                List<ProgramListModel> questionsListModels = value.toObjects(ProgramListModel.class);
                onFirestoreTaskComplete.programListDataAdded(questionsListModels);
            }
        });
    }



    public interface OnFirestoreTaskComplete{
        void programListDataAdded(List<ProgramListModel> programListModels);
        void onError(Exception e);
    }

}

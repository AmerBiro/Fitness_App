package com.example.fitnessapp.firebase_repo;

import androidx.annotation.NonNull;

import com.example.fitnessapp.model.ProgramListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseRepository {

    private OnFireStoreTaskComplete onFireStoreTaskComplete;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference programRef = firebaseFirestore.collection("program");

    public FirebaseRepository(OnFireStoreTaskComplete onFireStoreTaskComplete) {
        this.onFireStoreTaskComplete = onFireStoreTaskComplete;
    }

    public void getProgramData(){
        programRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    onFireStoreTaskComplete.programListDataAdded(task.getResult().toObjects(ProgramListModel.class));
                }else {
                    onFireStoreTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public interface OnFireStoreTaskComplete{
        void programListDataAdded(List<ProgramListModel> programListModelsList);
        void onError(Exception e);
    }

}

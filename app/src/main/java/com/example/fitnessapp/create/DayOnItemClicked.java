//package com.example.fitnessapp.database;
//
//import android.app.Activity;
//import android.content.DialogInterface;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class DayOnItemClicked {
//
//    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//    private String userId, programListId;
//
//
//    public void dayOnSwipeDelete(String userId, String programListId, String documentId, Activity activity) {
//        this.userId = userId;
//        this.programListId = programListId;
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//                DocumentReference dayRef = firebaseFirestore
//                        .collection("user").document(userId)
//                        .collection("ProgramList").document(programListId)
//                        .collection("DayList").document(documentId);
////                        Log.d(TAG, "getDayListId: " + dayListModels.get(viewHolder.getAdapterPosition()).getDayListId());
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                builder.setTitle("Delete student");
//                builder.setMessage("Are you sure that you want to delete the following day\n"
//                        + "Day " + dayListModels.get(viewHolder.getAdapterPosition()).getDay_number()
//                        + ", " + dayListModels.get(viewHolder.getAdapterPosition()).getDay_exercise_number()
//                        + " " + "exercise")
//                        .setCancelable(true)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dayRef.delete();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                adapter.notifyDataSetChanged();
//                            }
//                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
//            }
//        }).attachToRecyclerView(recyclerView);
//    }
//}

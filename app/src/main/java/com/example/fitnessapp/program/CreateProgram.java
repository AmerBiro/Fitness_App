package com.example.fitnessapp.program;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.R;
import com.example.fitnessapp.database.Program;
import com.example.fitnessapp.databinding.ProgramCreateProgramBinding;
import com.example.fitnessapp.functions.ImageHandler;
import com.example.fitnessapp.mvvm.adapters.ProgramAdapter;
import com.example.fitnessapp.mvvm.model.ProgramModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class CreateProgram extends Fragment {

    private @NonNull ProgramCreateProgramBinding binding;
    private Program program;
    private NavController navController;
    private String program_name, coach_name, fitness_center, days, exercises, start_date, end_date, imageString;
    ImageHandler handler = new ImageHandler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ProgramCreateProgramBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.createProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                program_name = binding.createProgramName.getText().toString();
                coach_name = binding.createProgramCoatchName.getText().toString();
                fitness_center = binding.createProgramFitnessCenter.getText().toString();
                days = binding.createProgramDays.getText().toString();
                exercises = binding.createProgramExercises.getText().toString();
                start_date = binding.createProgramStartDate.getText().toString();
                end_date = binding.createProgramEndDate.getText().toString();


                program = new Program(program_name, "program_name",
                        coach_name, "coach_name",
                        fitness_center, "fitness_center",
                        days, "days",
                        exercises, "exercises",
                        start_date, "start_date",
                        end_date, "end_date",
                        handler.getImageUri());

                program.createProgram();
                navController.navigate(R.id.action_createProgram_to_home2);
            }
        });

        binding.cancelCreateProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_createProgram_to_home2);
            }
        });

        binding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(1000);
            }
        });

    }

    public void openGallery(int requestCode){
        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGallery, requestCode);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {

                handler.uploadeImageToFirebase(data, getActivity(), binding.image);
//                uploadeImageToFirebase(data);
            }
        }
    }

//    public void uploadeImageToFirebase(Intent data){
//
//        Uri imageUri = data.getData();
//
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        String userId = user.getUid();
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//        String imageId = Double.toString((System.currentTimeMillis() / 1000));
//
//        final StorageReference profilePicture = storageReference.child("users/" + userId + "/" + imageId + ".jpg");
//
//        profilePicture.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                profilePicture.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Map<String, Object> image = new HashMap<>();
//                        image.put("program_image_url", uri);
//                        imageString = uri.toString();
//                        Glide
//                                .with(getActivity())
//                                .load(uri)
//                                .centerCrop()
//                                .placeholder(R.drawable.placeholder_image)
//                                .into(binding.image);
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), "Failed uploading image!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

}
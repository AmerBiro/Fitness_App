package com.example.fitnessapp.database;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ImageHandler {

    private String image_url;

    public void uploadeImageToFirebase(Intent data, Activity activity, ImageView imageView){
        Uri imageUri = data.getData();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        String imageId = Double.toString((System.currentTimeMillis() / 1000));

        final StorageReference programImage = storageReference.child("user/" + userId + "/" + imageId + ".jpg");

        programImage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                programImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map<String, Object> image = new HashMap<>();
                        image.put("image_url", uri);
                        image_url = uri.toString();
                        Glide
                                .with(activity)
                                .load(uri)
                                .centerCrop()
                                .placeholder(R.drawable.placeholder_image)
                                .into(imageView);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Failed uploading image!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getImageUri(){
        return this.image_url;
    }



}

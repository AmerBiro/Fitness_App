package com.example.fitnessapp.functions;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddImages {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    private String userId = user.getUid();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public void uploadeImageToFirebase(Uri imageUri, String imageName, Activity activity, ImageView imageView) {
        final StorageReference profilePicture = storageReference.child("users/"+userId+"/" + imageName  +   ".jpg");
        profilePicture.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(activity, "Image has been uploaded", 0).show();
                profilePicture.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map<String, Object> image = new HashMap<>();
                        image.put("program_image_url", uri);
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
}

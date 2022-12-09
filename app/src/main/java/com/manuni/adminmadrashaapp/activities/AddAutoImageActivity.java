package com.manuni.adminmadrashaapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.manuni.adminmadrashaapp.ImageModel;
import com.manuni.adminmadrashaapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddAutoImageActivity extends AppCompatActivity {
    private CardView uploadImageAuto;
    private final int REQ = 1;
    private Uri imUri;
    private Bitmap bitmap;
    private ImageView galleryImageViewAuto;
    private Button uploadGalleryBtnAuto;
    private StorageReference storageReference, mStorageRef;
    private UploadTask uploadTask;
    private String downloadUrl = "";
    private DatabaseReference databaseReference;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auto_image);


        dialog = new ProgressDialog(this);
        dialog.setTitle("Auto Slider Image");
        dialog.setMessage("Uploading...");
        dialog.setCancelable(true);

        storageReference = FirebaseStorage.getInstance().getReference().child("SliderImage");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("SliderImage");

        galleryImageViewAuto = findViewById(R.id.galleryImageViewAuto);

        uploadGalleryBtnAuto = findViewById(R.id.uploadGalleryBtnAuto);

        uploadImageAuto = findViewById(R.id.uploadImageAuto);
        uploadImageAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        uploadGalleryBtnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }

    private void check() {
        if (bitmap == null) {
            Toast.makeText(this, "Select a photo first!", Toast.LENGTH_SHORT).show();
        } else {
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(this, "Uploading one file!", Toast.LENGTH_SHORT).show();
            } else {
                uploadToStorage();
            }

        }
    }

    private String getFileExtension(Uri uri) {
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    private void uploadToStorage() {
        dialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

        byte[] image = baos.toByteArray();
        mStorageRef = storageReference.child(image + "." + getFileExtension(imUri));
        uploadTask = mStorageRef.putBytes(image);

        uploadTask.addOnCompleteListener(AddAutoImageActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadToDatabase();
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    private void uploadToDatabase() {
        dialog.show();
        String uniqueKey = databaseReference.push().getKey();
        ImageModel data = new ImageModel(downloadUrl, uniqueKey);

        databaseReference.child(uniqueKey).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.dismiss();
                Toast.makeText(AddAutoImageActivity.this, "Image uploaded!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddAutoImageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void openGallery() {
        try {
            Intent imgPick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(imgPick, REQ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {

            try {
                imUri = data.getData();
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            galleryImageViewAuto.setImageBitmap(bitmap);
        }
    }
}
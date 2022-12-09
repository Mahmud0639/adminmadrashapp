package com.manuni.adminmadrashaapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.manuni.adminmadrashaapp.ImageData;
import com.manuni.adminmadrashaapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadImage extends AppCompatActivity {
    private CardView pickGalleryImage;
    private final int REQ = 1;
    private Uri imgUri;
    private Bitmap bitmap;
    private ImageView galleryImageView;
    private Spinner SpinnerId;
    private String category;
    private Button uploadGalleryBtn;
    private String downloadUrl = null;
    private ProgressDialog dialog;
    private UploadTask uploadTask;

    //firebase
    private StorageReference storageReference, mStorage;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);


        pickGalleryImage = findViewById(R.id.pickGalleryImage);
        galleryImageView = findViewById(R.id.galleryImageView);
        SpinnerId = findViewById(R.id.imageCategory);
        uploadGalleryBtn = findViewById(R.id.UploadImageBtn);

        //progress dialog code
        dialog = new ProgressDialog(this);
        dialog.setTitle("Images");
        dialog.setMessage("Uploading...");

        //firebase instance
        storageReference = FirebaseStorage.getInstance().getReference().child("gallery");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("gallery");

        //code for spinner
        String[] items = new String[]{"Select category", "মাদ্রাসার তথ্যসমূহ", "শিক্ষক এবং ছাত্রতথ্য", "প্রচার ও প্রকাশনাসমূহ", "অন্যান্য"};
        SpinnerId.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));
        SpinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = SpinnerId.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pickGalleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        if (bitmap == null) {
            Toast.makeText(this, "Select a photo", Toast.LENGTH_SHORT).show();
        } else if (category.equals("Select category")) {
            Toast.makeText(this, "Category required", Toast.LENGTH_SHORT).show();
        } else {
            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(this, "Uploading one item", Toast.LENGTH_SHORT).show();
            }else {
                uploadToStorage();
            }

        }
    }
    private String getFileExtension(Uri uri){
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(getContentResolver().getType(uri));
    }
    private void uploadToStorage() {
        dialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

        byte[] image = baos.toByteArray();
        mStorage = storageReference.child(image + "."+getFileExtension(imgUri) + System.currentTimeMillis());
        uploadTask = mStorage.putBytes(image);
        uploadTask.addOnCompleteListener(UploadImage.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

    private void uploadToDatabase(){
        dialog.show();
        String key = databaseReference.push().getKey();
        ImageData data = new ImageData(downloadUrl, key);

        databaseReference.child(category).child(key).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.dismiss();
                Toast.makeText(UploadImage.this,"Image uploaded!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UploadImage.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(UploadImage.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
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
                imgUri = data.getData();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            galleryImageView.setImageBitmap(bitmap);
        }
    }
}
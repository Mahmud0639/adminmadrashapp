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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.manuni.adminmadrashaapp.Faculty.TeacherData;
import com.manuni.adminmadrashaapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddTeachers extends AppCompatActivity {
    private ImageView addTeacherImage;
    private EditText addTeacherName, addTeacherEmail,addTeacherPost;
    private Spinner addTeacherCategory;
    private Button addTeacherBtn;
    private final int Req = 1;
    private Bitmap bitmap = null;
    private String category;
    private String name, email, post, downloadUrl= "";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;
    private UploadTask uploadTask;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);

        addTeacherImage = findViewById(R.id.addTeacherImage);
        addTeacherName = findViewById(R.id.addTeacherName);
        addTeacherEmail = findViewById(R.id.addTeacherEmail);
        addTeacherPost = findViewById(R.id.addTeacherPost);
        addTeacherCategory = findViewById(R.id.addTeacherCategory);
        addTeacherBtn = findViewById(R.id.addTeacherBtn);

        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] items = new String[]{"Select Category","কুরআন ও তাজবিদ","আরবি সাহিত্য","উর্দু কিতাব","ইসলামিয়াত","বাংলা","ইংরেজি","গণিত", "কম্পিউটার প্রশিক্ষণ", "হেফজ বিভাগ", "কেরাত বিভাগ"};
        addTeacherCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));
        addTeacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addTeacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
            private void openGallery() {
                try {
                    Intent pickImageNow = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickImageNow,Req);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        addTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });


    }

    private void checkValidation() {
        name = addTeacherName.getText().toString();
        email = addTeacherEmail.getText().toString();
        post = addTeacherPost.getText().toString();

        if (name.isEmpty()){
            addTeacherName.setError("Name required");
            addTeacherName.requestFocus();
        }else if (email.isEmpty()){
            addTeacherEmail.setError("Email required");
            addTeacherEmail.requestFocus();
        }else if (post.isEmpty()){
            addTeacherPost.setError("Post required");
            addTeacherPost.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(this, "Provide a category to continue", Toast.LENGTH_SHORT).show();
        }else if (bitmap == null){
            insertData();

        }else {
            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(this, "Progressing...", Toast.LENGTH_SHORT).show();
            }else {
                uploadImage();
            }


        }
    }

    private String getFileExtension(Uri uri){
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    private void uploadImage() {
        pd.setMessage("Uploading...");
        pd.setTitle("Wait a bit");
        pd.show();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] finalImage = byteArrayOutputStream.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Teachers").child(finalImage+getFileExtension(imageUri));
        uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(AddTeachers.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(AddTeachers.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void insertData(){
        pd.setTitle("Wait a bit");
        pd.setMessage("Uploading...");
        pd.show();
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();

        TeacherData teacherData = new TeacherData(name, email, post, downloadUrl, uniqueKey);
        dbRef.child(uniqueKey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(AddTeachers.this, "New teacher added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddTeachers.this,UpdateFaculty.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddTeachers.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Req && resultCode==RESULT_OK){
            try {
                imageUri = data.getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addTeacherImage.setImageBitmap(bitmap);
        }
    }
}
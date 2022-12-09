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
import android.widget.EditText;
import android.widget.ImageView;
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
import com.manuni.adminmadrashaapp.NoticeData;
import com.manuni.adminmadrashaapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Upload_Notice extends AppCompatActivity {
    private CardView addImage;
    private final int Req = 1;
    private Bitmap bitmap;
    private ImageView noticeImageView;
    private EditText noticeTitle;
    private Button uploadNoticeButton;
    private DatabaseReference reference ,dbRef;
    private StorageReference storageReference;
    String downloadUrl = "";
    private ProgressDialog pd;
    private UploadTask uploadTask;
    private Uri imuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__notice);
        noticeImageView = findViewById(R.id.noticeImageView);
        noticeTitle = findViewById(R.id.noticeTitle);
        uploadNoticeButton = findViewById(R.id.UploadNoticeButton);
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);

        addImage = findViewById(R.id.noticeUpload);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        uploadNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noticeTitle.getText().toString().isEmpty()){
                    noticeTitle.setError("Title Needed");
                    noticeTitle.requestFocus();
                }else if (bitmap == null){
                    uploadData();

                
                }else {
                    if (uploadTask != null && uploadTask.isInProgress()){
                        Toast.makeText(Upload_Notice.this, "Progressing...", Toast.LENGTH_SHORT).show();
                    }else {
                        uploadImage();
                    }

                }
            }
        });
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
        filePath = storageReference.child("Notice").child(finalImage+getFileExtension(imuri));
        uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(Upload_Notice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    uploadData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(Upload_Notice.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        pd.setTitle("Wait a bit");
        pd.setMessage("Uploading...");
        pd.show();
        dbRef = reference.child("Notices");
        final String uniqueKey = dbRef.push().getKey();

        String title = noticeTitle.getText().toString();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String date = simpleDateFormat.format(calendar.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        NoticeData noticeData = new NoticeData(title,downloadUrl,time,date,uniqueKey);
        dbRef.child(uniqueKey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(Upload_Notice.this, "Notice uploaded", Toast.LENGTH_SHORT).show();
                GoTomain();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Upload_Notice.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GoTomain() {
        startActivity(new Intent(Upload_Notice.this,MainActivity.class));
        finish();
    }


    private void openGallery() {
        try {
            Intent pickImageNow = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickImageNow,Req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Req && resultCode==RESULT_OK){
            try {
                imuri = data.getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imuri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            noticeImageView.setImageBitmap(bitmap);
        }
    }

}
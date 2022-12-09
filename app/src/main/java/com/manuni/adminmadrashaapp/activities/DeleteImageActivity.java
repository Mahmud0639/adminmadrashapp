package com.manuni.adminmadrashaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manuni.adminmadrashaapp.R;
import com.squareup.picasso.Picasso;

public class DeleteImageActivity extends AppCompatActivity {
    private String Image;
    private ImageView imageDeleteId;
    private Button deleteImageId;
    private String category;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_image);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("gallery");

        deleteImageId = findViewById(R.id.deleteImageId);
        imageDeleteId = findViewById(R.id.imageDeleteId);

        category = getIntent().getStringExtra("category");

        Image = getIntent().getStringExtra("image");
        try {
            Picasso.get().load(Image).placeholder(R.drawable.impl5).into(imageDeleteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        deleteImageId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = getIntent().getStringExtra("key");
                databaseReference.child(category).child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(DeleteImageActivity.this, "Image deleted", Toast.LENGTH_SHORT).show();
                        gotoPrevious();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DeleteImageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void gotoPrevious() {
        startActivity(new Intent(DeleteImageActivity.this,ImageDeleteActivity.class));
        finish();
    }

}


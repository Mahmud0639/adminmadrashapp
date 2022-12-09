package com.manuni.adminmadrashaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.manuni.adminmadrashaapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView addNotice, addGalleryImage, addFaculty, addDelete, deleteImage, addAutoImage, deleteAutoImageCard;
    Button Logoutme;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        addNotice = findViewById(R.id.addNotice);
        addGalleryImage = findViewById(R.id.addGalleryImage);
        addFaculty = findViewById(R.id.addFaculty);
        addDelete = findViewById(R.id.addDelete);
        Logoutme = findViewById(R.id.Logoutme);
        deleteImage = findViewById(R.id.deleteImage);
        addAutoImage = findViewById(R.id.addAutoImage);
        deleteAutoImageCard = findViewById(R.id.deleteAutoImageCard);


        addNotice.setOnClickListener(this);
        addGalleryImage.setOnClickListener(this);
        addFaculty.setOnClickListener(this);
        addDelete.setOnClickListener(this);
        Logoutme.setOnClickListener(this);
        deleteImage.setOnClickListener(this);
        addAutoImage.setOnClickListener(this);
        deleteAutoImageCard.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNotice:
                startActivity(new Intent(MainActivity.this, Upload_Notice.class));
                break;

            case R.id.addGalleryImage:
                startActivity(new Intent(MainActivity.this, UploadImage.class));
                break;
            case R.id.addFaculty:
                startActivity(new Intent(MainActivity.this, UpdateFaculty.class));
                break;
            case R.id.addDelete:
                startActivity(new Intent(MainActivity.this, DeleteNoticeActivity.class));
                break;
            case R.id.Logoutme:
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.deleteImage:
                startActivity(new Intent(MainActivity.this, ImageDeleteActivity.class));
                break;
            case R.id.addAutoImage:
                startActivity(new Intent(MainActivity.this, AddAutoImageActivity.class));
                break;
            case R.id.deleteAutoImageCard:
                startActivity(new Intent(MainActivity.this,DeleteAutoImageActivity.class));
                break;

        }
    }
}

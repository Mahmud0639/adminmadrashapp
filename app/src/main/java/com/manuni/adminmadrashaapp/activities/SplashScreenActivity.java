package com.manuni.adminmadrashaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.manuni.adminmadrashaapp.R;

public class SplashScreenActivity extends AppCompatActivity {
    private ProgressBar progressBarId;
    private int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        progressBarId = findViewById(R.id.progressBarId);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startProgressing();
                goMainActivity();
            }
        });
        thread.start();
    }

    private void goMainActivity() {
        startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
        finish();
    }

    private void startProgressing() {
        for (progress = 20; progress <= 100; progress = progress+20){
            try {
                Thread.sleep(1000);
                progressBarId.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
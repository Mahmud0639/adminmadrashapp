package com.manuni.adminmadrashaapp.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.manuni.adminmadrashaapp.R;
import com.manuni.adminmadrashaapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private TextView createAnId;
    private ProgressDialog dialog;
    private Button loginId;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait...");
        dialog.setMessage("Logging in");


        loginId = findViewById(R.id.loginId);
        createAnId = findViewById(R.id.loginId);



        binding.loginId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                singIn();
            }
        });
    }

    private void singIn() {
        if (binding.loginEmailBoxId.getEditText().getText().toString().isEmpty()){
            binding.loginEmailBoxId.setError("Empty");
            binding.loginEmailBoxId.requestFocus();
        }
       else if (binding.LogInPasswordBox.getEditText().getText().toString().isEmpty()){
            binding.LogInPasswordBox.setError("Empty");
           binding.LogInPasswordBox.requestFocus();
        }else {
            //dialog.show();
            binding.lottie.playAnimation();
            String email,pass;
            email = binding.loginEmailBoxId.getEditText().getText().toString().trim();
            pass = binding.LogInPasswordBox.getEditText().getText().toString().trim();


            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete( Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        //dialog.dismiss();
                        binding.lottie.pauseAnimation();
                        startActivity(new Intent(LoginActivity.this,SplashScreenActivity.class));
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e) {
                    //dialog.dismiss();
                    binding.lottie.pauseAnimation();
                    Toast.makeText(LoginActivity.this,e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

    }
}
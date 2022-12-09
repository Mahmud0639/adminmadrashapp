package com.manuni.adminmadrashaapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manuni.adminmadrashaapp.User;
import com.manuni.adminmadrashaapp.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
//    private EditText nameBox, emailBox, passwordBox;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait");
        dialog.setMessage("Creating accounting...");


        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        binding.alreadyHaveId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });

        binding.submitBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        if (binding.nameBox.getEditText().getText().toString().isEmpty()){
            binding.nameBox.setError("Name required");
            binding.nameBox.requestFocus();
        }
        else if (binding.emailBox.getEditText().getText().toString().isEmpty()){
            binding.emailBox.setError("Email required");
            binding.emailBox.requestFocus();
        }
       else if (binding.passwordBox.getEditText().getText().toString().isEmpty()){
            binding.passwordBox.setError("Password required");
            binding.passwordBox.requestFocus();
        }else {
            dialog.show();
            String name = binding.nameBox.getEditText().getText().toString().trim();
            String email = binding.emailBox.getEditText().getText().toString().trim();
            String pass = binding.passwordBox.getEditText().getText().toString().trim();

            User user = new User(name, email, pass);
            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        databaseReference.child("User").child(databaseReference.push().getKey()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        dialog.dismiss();
                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }



    }
}
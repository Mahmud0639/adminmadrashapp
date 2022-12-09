package com.manuni.adminmadrashaapp.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manuni.adminmadrashaapp.AutoImageAdapter;
import com.manuni.adminmadrashaapp.ImageModel;
import com.manuni.adminmadrashaapp.R;

import java.util.ArrayList;
import java.util.List;

public class DeleteAutoImageActivity extends AppCompatActivity {
    private DatabaseReference reference;
    private List<ImageModel> list;
    private RecyclerView autoImageRecycler;
    private AutoImageAdapter autoImageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_auto_image);

        autoImageRecycler = findViewById(R.id.autoImageRecycler);

        reference = FirebaseDatabase.getInstance().getReference().child("SliderImage");



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   ImageModel data = dataSnapshot.getValue(ImageModel.class);
                   list.add(data);
                }
                autoImageRecycler.setHasFixedSize(true);
                autoImageRecycler.setLayoutManager(new LinearLayoutManager(DeleteAutoImageActivity.this));
                autoImageAdapter = new AutoImageAdapter(DeleteAutoImageActivity.this,list);
                autoImageRecycler.setAdapter(autoImageAdapter);
                autoImageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
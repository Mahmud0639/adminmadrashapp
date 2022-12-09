package com.manuni.adminmadrashaapp.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manuni.adminmadrashaapp.ImageAdapter;
import com.manuni.adminmadrashaapp.ImageData;
import com.manuni.adminmadrashaapp.R;

import java.util.ArrayList;
import java.util.List;

public class ImageDeleteActivity extends AppCompatActivity {
    private List<ImageData> list1, list2, list3, list4;
    private DatabaseReference databaseReference;
    private RecyclerView madrasahTotthoRecycler, sikkhokhSatroRecycler, procharProkashonaRecycler, onnannoRecycler;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_delete);

        madrasahTotthoRecycler = findViewById(R.id.madrasahTotthoRecycler);
        sikkhokhSatroRecycler = findViewById(R.id.sikkhokhSatroRecycler);
        procharProkashonaRecycler = findViewById(R.id.procharProkashonaRecycler);
        onnannoRecycler = findViewById(R.id.onnannoRecycler);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("gallery");

        madrasahTottho();
        sikkhokhTottho();
        procharProkasona();
        onnanno();
    }

    private void madrasahTottho() {
        databaseReference.child("মাদ্রাসার তথ্যসমূহ").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                list1.clear();
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    String data = (String) dataSnapshot.getValue();
//                    list.add(data);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ImageData data = dataSnapshot.getValue(ImageData.class);
                    list1.add(0,data);
                }
                //independenceRecycler.setHasFixedSize(true);
                madrasahTotthoRecycler.setLayoutManager(new GridLayoutManager(ImageDeleteActivity.this, 3));
                adapter = new ImageAdapter(ImageDeleteActivity.this, list1, "মাদ্রাসার তথ্যসমূহ");
                madrasahTotthoRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ImageDeleteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sikkhokhTottho() {
        databaseReference.child("শিক্ষক এবং ছাত্রতথ্য").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                list2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ImageData data = dataSnapshot.getValue(ImageData.class);
                    list2.add(0,data);
                }
                //convocationRecycler.setHasFixedSize(true);
                sikkhokhSatroRecycler.setLayoutManager(new GridLayoutManager(ImageDeleteActivity.this, 3));
                adapter = new ImageAdapter(ImageDeleteActivity.this, list2, "শিক্ষক এবং ছাত্রতথ্য");
                sikkhokhSatroRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ImageDeleteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void procharProkasona() {
        databaseReference.child("প্রচার ও প্রকাশনাসমূহ").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                list3.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ImageData data = dataSnapshot.getValue(ImageData.class);
                    list3.add(0,data);
                }
                //othersRecycler.setHasFixedSize(true);
                procharProkashonaRecycler.setLayoutManager(new GridLayoutManager(ImageDeleteActivity.this, 3));
                adapter = new ImageAdapter(ImageDeleteActivity.this, list3, "প্রচার ও প্রকাশনাসমূহ");
                procharProkashonaRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ImageDeleteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void onnanno(){
        databaseReference.child("অন্যান্য").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                list4.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ImageData data = dataSnapshot.getValue(ImageData.class);
                    list4.add(0,data);
                }
                //othersRecycler.setHasFixedSize(true);
                onnannoRecycler.setLayoutManager(new GridLayoutManager(ImageDeleteActivity.this, 3));
                adapter = new ImageAdapter(ImageDeleteActivity.this, list4, "অন্যান্য");
                onnannoRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ImageDeleteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

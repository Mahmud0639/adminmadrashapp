package com.manuni.adminmadrashaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manuni.adminmadrashaapp.Faculty.TeacherAdapter;
import com.manuni.adminmadrashaapp.Faculty.TeacherData;
import com.manuni.adminmadrashaapp.R;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {
    FloatingActionButton fab;
    private RecyclerView QuranDepartment, ArabicDepartment, urduDepartment, talimulDepartment, banglaDepartment, englishDepartment, mathematicsDepartment, computerDepartment, hefzoDepartment, keratDepartment;
    private LinearLayout QuranNoData, ArabicNoData, urduNoData, talimulNoData, banglaNoData, englishNoData, mathematicsNoData, computerNoData, hefzoNoData, keratNoData;
    private List<TeacherData> list1, list2, list3, list4, list5, list6, list7, list8, list9, list10;
    private DatabaseReference reference, dbRef;
    private TeacherAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);
        fab = findViewById(R.id.fab);


        QuranDepartment = findViewById(R.id.QuranDepartment);
        ArabicDepartment = findViewById(R.id.ArabicDepartment);
        urduDepartment = findViewById(R.id.urduDepartment);
        talimulDepartment = findViewById(R.id.talimulDepartment);
        banglaDepartment = findViewById(R.id.banglaDepartment);
        englishDepartment = findViewById(R.id.englishDepartment);
        mathematicsDepartment = findViewById(R.id.mathematicsDepartment);
        computerDepartment = findViewById(R.id.computerDepartment);
        hefzoDepartment = findViewById(R.id.hefzoDepartment);
        keratDepartment = findViewById(R.id.keratDepartment);

        QuranNoData = findViewById(R.id.QuranNoData);
        ArabicNoData = findViewById(R.id.ArabicNoData);
        urduNoData = findViewById(R.id.urduNoData);
        talimulNoData = findViewById(R.id.talimulNoData);
        banglaNoData = findViewById(R.id.banglaNoData);
        englishNoData = findViewById(R.id.englishNoData);
        mathematicsNoData = findViewById(R.id.mathematicsNoData);
        computerNoData = findViewById(R.id.computerNoData);
        hefzoNoData = findViewById(R.id.hefzoNoData);
        keratNoData = findViewById(R.id.keratNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("teacher");
        quranDepartment();
        arabicDepartment();
        UrduDepartment();
        TalimulDepartment();
        BanglaDepartment();
        EnglishDepartment();
        MathematicsDepartment();
        ComputerDepartment();
        HefzoDepartment();
        KeratDepartment();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateFaculty.this,AddTeachers.class));
            }
        });
    }

    private void ComputerDepartment() {
        dbRef = reference.child("কম্পিউটার প্রশিক্ষণ");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list8 = new ArrayList<>();
                if (!snapshot.exists()){
                    computerNoData.setVisibility(View.VISIBLE);
                    computerDepartment.setVisibility(View.GONE);
                }else {
                    computerNoData.setVisibility(View.GONE);
                    computerDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list8.add(data);
                    }
                    computerDepartment.setHasFixedSize(true);
                    computerDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list8,UpdateFaculty.this,"কম্পিউটার প্রশিক্ষণ");
                    computerDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void HefzoDepartment() {
        dbRef = reference.child("হেফজ বিভাগ");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list9 = new ArrayList<>();
                if (!snapshot.exists()){
                    hefzoNoData.setVisibility(View.VISIBLE);
                    hefzoDepartment.setVisibility(View.GONE);
                }else {
                    hefzoNoData.setVisibility(View.GONE);
                    hefzoDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list9.add(data);
                    }
                    hefzoDepartment.setHasFixedSize(true);
                    hefzoDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list9,UpdateFaculty.this,"হেফজ বিভাগ");
                    hefzoDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void KeratDepartment() {
        dbRef = reference.child("কেরাত বিভাগ");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list10 = new ArrayList<>();
                if (!snapshot.exists()){
                    keratNoData.setVisibility(View.VISIBLE);
                    keratDepartment.setVisibility(View.GONE);
                }else {
                    keratNoData.setVisibility(View.GONE);
                    keratDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list10.add(data);
                    }
                    keratDepartment.setHasFixedSize(true);
                    keratDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list10,UpdateFaculty.this,"কেরাত বিভাগ");
                    keratDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void quranDepartment() {
        dbRef = reference.child("কুরআন ও তাজবিদ");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()){
                    QuranNoData.setVisibility(View.VISIBLE);
                    QuranDepartment.setVisibility(View.GONE);
                }else {
                    QuranNoData.setVisibility(View.GONE);
                    QuranDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    QuranDepartment.setHasFixedSize(true);
                    QuranDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list1,UpdateFaculty.this,"কুরআন ও তাজবিদ");
                    QuranDepartment.setAdapter(adapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    } private void arabicDepartment() {
        dbRef = reference.child("আরবি সাহিত্য");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()){
                    ArabicNoData.setVisibility(View.VISIBLE);
                    ArabicDepartment.setVisibility(View.GONE);
                }else {
                    ArabicNoData.setVisibility(View.GONE);
                    ArabicDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    ArabicDepartment.setHasFixedSize(true);
                    ArabicDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list2,UpdateFaculty.this,"আরবি সাহিত্য");
                    ArabicDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    } private void UrduDepartment() {
        dbRef = reference.child("উর্দু কিতাব");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if (!snapshot.exists()){
                    urduNoData.setVisibility(View.VISIBLE);
                    urduDepartment.setVisibility(View.GONE);
                }else {
                    urduNoData.setVisibility(View.GONE);
                    urduDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    urduDepartment.setHasFixedSize(true);
                    urduDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list3,UpdateFaculty.this,"উর্দু কিতাব");
                    urduDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    } private void TalimulDepartment() {
        dbRef = reference.child("ইসলামিয়াত");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if (!snapshot.exists()){
                    talimulNoData.setVisibility(View.VISIBLE);
                    talimulDepartment.setVisibility(View.GONE);
                }else {
                    talimulNoData.setVisibility(View.GONE);
                    talimulDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    talimulDepartment.setHasFixedSize(true);
                    talimulDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list4,UpdateFaculty.this,"ইসলামিয়াত");
                    talimulDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    } private void BanglaDepartment() {
        dbRef = reference.child("বাংলা");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list5 = new ArrayList<>();
                if (!snapshot.exists()){
                    banglaNoData.setVisibility(View.VISIBLE);
                    banglaDepartment.setVisibility(View.GONE);
                }else {
                    banglaNoData.setVisibility(View.GONE);
                    banglaDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list5.add(data);
                    }
                    banglaDepartment.setHasFixedSize(true);
                    banglaDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list5,UpdateFaculty.this,"বাংলা");
                    banglaDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    } private void EnglishDepartment() {
        dbRef = reference.child("ইংরেজি");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list6 = new ArrayList<>();
                if (!snapshot.exists()){
                    englishNoData.setVisibility(View.VISIBLE);
                    englishDepartment.setVisibility(View.GONE);
                }else {
                    englishNoData.setVisibility(View.GONE);
                    englishDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list6.add(data);
                    }
                    englishDepartment.setHasFixedSize(true);
                    englishDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list6,UpdateFaculty.this,"ইংরেজি");
                    englishDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    } private void MathematicsDepartment() {
        dbRef = reference.child("গণিত");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list7 = new ArrayList<>();
                if (!snapshot.exists()){
                    mathematicsNoData.setVisibility(View.VISIBLE);
                    mathematicsDepartment.setVisibility(View.GONE);
                }else {
                    mathematicsNoData.setVisibility(View.GONE);
                    mathematicsDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list7.add(data);
                    }
                    mathematicsDepartment.setHasFixedSize(true);
                    mathematicsDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list7,UpdateFaculty.this,"গণিত");
                    mathematicsDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
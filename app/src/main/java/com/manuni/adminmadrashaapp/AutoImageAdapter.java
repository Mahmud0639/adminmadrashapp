package com.manuni.adminmadrashaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AutoImageAdapter extends RecyclerView.Adapter<AutoImageAdapter.AutoImageViewHolder> {
    private Context context;
    private List<ImageModel> list;


    public AutoImageAdapter(Context context, List<ImageModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AutoImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.auto_image_sample_layout, parent,false);
        return new AutoImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AutoImageViewHolder holder, int position) {
        ImageModel data = list.get(position);
        try {
            Glide.with(context).load(data.getImage()).placeholder(R.drawable.impl5).into(holder.autoImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.autoImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete?");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SliderImage");
                        databaseReference.child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Auto image deleted!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        notifyItemRemoved(position);
                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = null;
                try {
                    dialog = builder.create();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (dialog != null){
                    dialog.show();
                }



            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AutoImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView autoImage;
        private Button autoImageBtn;

        public AutoImageViewHolder(@NonNull View itemView) {
            super(itemView);
            autoImage = itemView.findViewById(R.id.autoImage);
            autoImageBtn = itemView.findViewById(R.id.autoImageBtn);

        }
    }
}

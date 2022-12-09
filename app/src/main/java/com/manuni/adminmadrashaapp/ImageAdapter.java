package com.manuni.adminmadrashaapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manuni.adminmadrashaapp.activities.DeleteImageActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    Context context;
    List<ImageData> list;
    String Category;

    public ImageAdapter(Context context, List<ImageData> list, String category){
        this.context = context;
        this.list = list;
        Category = category;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_layout,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageData data = list.get(position);
        try {
            Picasso.get().load(data.getImage()).placeholder(R.drawable.impl5).into(holder.myImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, DeleteImageActivity.class);
                    intent.putExtra("image",data.getImage());
                    intent.putExtra("key",data.getKey());
                    intent.putExtra("category",Category);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView myImage;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            myImage = itemView.findViewById(R.id.myImage);
        }
    }
}

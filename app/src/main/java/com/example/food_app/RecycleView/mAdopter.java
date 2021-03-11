package com.example.food_app.RecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app.Items;
import com.example.food_app.R;

import java.util.ArrayList;

public class mAdopter extends RecyclerView.Adapter<mAdopter.ExampleViewHolder>{
    private Context Context;
    private ArrayList<Items> DataList;

    public mAdopter(Context context, ArrayList<Items> exampleList) {
        Context = context;
        DataList = exampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(Context).inflate(R.layout.activity_food_recycleview, parent, false);
        return new ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Items currentItem = DataList.get(position);
        String imageUrl = currentItem.getImageurl();
        String creatorName = currentItem.getMcreater();
        int likeCount = currentItem.getLikes();
        holder.TextViewCreator.setText(creatorName);
        holder.TextViewLikes.setText("Likes: " + likeCount);
        Glide.with(Context)
                .load(imageUrl)
                .centerCrop()
                .into(holder.ImageView);
//        Picasso.get().load(imageUrl).fit().centerInside().into(holder.ImageView);
    }
    @Override
    public int getItemCount() {
        return DataList.size();
    }
    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView ImageView;
        public TextView TextViewCreator;
        public TextView TextViewLikes;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            ImageView  = itemView.findViewById(R.id.imageView);
            TextViewCreator  = itemView.findViewById(R.id.textTitle);
            TextViewLikes  = itemView.findViewById(R.id.textDesc);
        }
    }
}
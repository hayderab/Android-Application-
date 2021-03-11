package com.example.food_app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>{
    private Context mContext;
    private ArrayList<Items> mExampleList;

    private Activity MyActivity;

    public ExampleAdapter(Context context, ArrayList<Items> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_food_recycleview, parent, false);
        return new ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Items currentItem = mExampleList.get(position);
        String imageUrl = currentItem.getImageurl();
        String creatorName = currentItem.getMcreater();
        int likeCount = currentItem.getLikes();
        holder.mTextViewCreator.setText(creatorName);
        holder.mTextViewLikes.setText("Likes: " + likeCount);
//        Glide.with(MyActivity)
//                .load(imageUrl)
//                .centerCrop()
//                .into(holder.mImageView);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;
        public ExampleViewHolder(View itemView) {
            super(itemView);
//            mImageView = itemView.findViewById(R.id.image_view);
//            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
//            mTextViewLikes = itemView.findViewById(R.id.text_view_likes);

            mImageView  = itemView.findViewById(R.id.imageView);
            mTextViewCreator  = itemView.findViewById(R.id.textTitle);
            mTextViewLikes  = itemView.findViewById(R.id.textDesc);
        }
    }
}
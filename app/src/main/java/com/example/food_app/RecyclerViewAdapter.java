package com.example.food_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app.RecycleView.Fooditems;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    //private List<Items> list;
    private List<Fooditems> list;

    private OnCardListener mOnCardListener;

    private Context context;




    public RecyclerViewAdapter(List<Fooditems> list,Context context) {
        this.list = list;
        this.context = context;

    }



    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_food_recycleview, parent, false);

        return new MyViewHolder(view, mOnCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, final int position) {

//        Items currentItem = list.get(position);
        final Fooditems currentItem = list.get(position);
        final String imageUrl = currentItem.getPhotoReference();
        final ArrayList<String> geomatry = currentItem.getGeomatry();

        Log.d("TAG", "onBindViewHolder: data test ..." + geomatry);
//        final ArrayList<Array> new  = currentItem.getGeomatry();

        String placeName = currentItem.getName();
        final String Address = currentItem.getAddress();

        holder.titleTextView.setText(placeName);
        holder.textDisciption.setText(Address);

//        String creatorName = currentItem.getMcreater();
//        int likeCount = currentItem.getLikes();
//        holder.titleTextView.setText(creatorName);
//        holder.textDisciption.setText("Likes: " + likeCount);
//        Glide.with(Context)
//                .load(imageUrl)
//                .centerCrop()
//                .into(holder.ImageView);

//        Glide.with(Context)
//                .load(imageUrl)
//                .centerCrop()
//                .into(holder.ImageView);
//        Log.d("TAG", "onBindViewHolder: " + ImageUrl);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.ImageUrl);

        //click listner for Cardview
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // data to be used in other activity
                Log.d("TAG", "onClick: test " + Address );
                Intent intent  = new Intent(context, Map.class);
                intent.putExtra("places_name",Address);
                intent.putExtra("lat",geomatry.get(0));
                intent.putExtra("lng",geomatry.get(1));

                context.startActivity(intent);

            }
        });

        holder.favouritebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // data to be used in other activity
                Log.d("TAG", "onClick: favourite button clicked"  );
//                Intent intent  = new Intent(context, Map.class);
////                intent.putExtra("places_name",Address);
////                intent.putExtra("lat",geomatry.get(0));
////                intent.putExtra("lng",geomatry.get(1));
//
//                context.startActivity(intent);

            }
        });

    }
    @Override
    public int getItemCount()
    {
        return list.size();

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickListener.onItemClick(list.get(position));
//            }
//        });
    }



    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        TextView titleTextView, textDisciption;
        ImageView ImageUrl;
        OnCardListener onCardListener;
        ConstraintLayout parent_layout;
        ImageButton favouritebtn;


        public MyViewHolder(View view, OnCardListener onCardListener) {
            super(view);
            titleTextView = view.findViewById(R.id.textTitle);
            textDisciption = view.findViewById(R.id.textDesc);
            ImageUrl = view.findViewById(R.id.imageView);

            parent_layout = itemView.findViewById(R.id.parent_layout);
            favouritebtn = itemView.findViewById(R.id.favouritebtn);

            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClicked(getAdapterPosition());

        }

    }

    public interface OnCardListener {
        //https://www.youtube.com/watch?v=69C1ljfDvl0 making adapter clickable.

        void  onCardClicked (int position);


    }
}
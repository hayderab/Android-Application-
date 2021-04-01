package com.example.food_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.RecycleView.Fooditems;
import com.example.food_app.fragments.Favourite;
import com.squareup.picasso.Picasso;

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

        Log.d("TAG", "onBindViewHolder: data favourite_View ..." + geomatry);
//        final ArrayList<Array> new  = currentItem.getGeomatry();

        final String placeName = currentItem.getName();
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
                Log.d("TAG", "onClick: favourite_View " + Address );
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
//                Log.d("TAG", "onClick: favourite button clicked"  );
//                Intent intent  = new Intent(context, favourite_View.class);
//                intent.putExtra("title", placeName);
//                intent.putExtra("url", imageUrl);
//                intent.putExtra("places_name",Address);
//                intent.putExtra("lat",geomatry.get(0));
//                intent.putExtra("lng",geomatry.get(1));
//
//                context.startActivity(intent);
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                Fragment fragment = new Favourite();
                Bundle args = new Bundle();
                args.putString("title", placeName);
                args.putString("url", imageUrl);
                args.putString("places_name",Address);
                args.putString("lat",geomatry.get(0));
                args.putString("lng",geomatry.get(1));
                fragment.setArguments(args);

//                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.fav_frame_Container, fragment, "favourite_fragment");

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, fragment, "favourite_fragment")
                        .commit();

                Log.d("TAG", "onClick: " + "Favourite fragment");
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
            titleTextView = view.findViewById(R.id.favTitle);
            textDisciption = view.findViewById(R.id.favDesc);
            ImageUrl = view.findViewById(R.id.favImg);

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
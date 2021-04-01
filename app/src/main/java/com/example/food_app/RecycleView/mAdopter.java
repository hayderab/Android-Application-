package com.example.food_app.RecycleView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.R;
import com.example.food_app.RecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class mAdopter extends RecyclerView.Adapter<mAdopter.MyViewHolder>{
    private List<Items> list;

    private RecyclerViewAdapter.OnCardListener mOnCardListener;

    private Context context;




    public mAdopter(List<Items> list,Context context) {
        this.list = list;
        this.context = context;

    }



    @NonNull
    @Override
    public mAdopter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_fav_view, parent, false);

        return new mAdopter.MyViewHolder(view, mOnCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull mAdopter.MyViewHolder holder, final int position) {

        Items currentItem = list.get(position);
        final String imageUrl = currentItem.getPhotoReference();
        final String  lat  = currentItem.getLat();
        final String lng = currentItem.getLng();

        Log.d("TAG", "onBindViewHolder: data favourite_View ..." + "ccccccccccccccccccccccccccccccc");
//        final ArrayList<Array> new  = currentItem.getGeomatry();

        final String placeName = currentItem.getName();
        final String Address = currentItem.getAddress();

        holder.titleTextView.setText(placeName);
        holder.textDisciption.setText(Address);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.ImageUrl);


        //click listner for Cardview

    }
    @Override
    public int getItemCount()
    {
        return list.size();

    }



    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        TextView titleTextView, textDisciption;
        ImageView ImageUrl;
        RecyclerViewAdapter.OnCardListener onCardListener;
        ConstraintLayout parent_layout;
        ImageButton favouritebtn;


        public MyViewHolder(View view, RecyclerViewAdapter.OnCardListener onCardListener) {
            super(view);
            titleTextView = view.findViewById(R.id.favTitle);
            textDisciption = view.findViewById(R.id.favDesc);
            ImageUrl = view.findViewById(R.id.favImg);


            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClicked(getAdapterPosition());

        }

    }


}
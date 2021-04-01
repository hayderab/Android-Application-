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
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.R;
import com.example.food_app.RecyclerViewAdapter;
import com.example.food_app.database.FavDatabase;
import com.example.food_app.database.FavouriteEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class favAdapter extends RecyclerView.Adapter<favAdapter.MyViewHolder> {

        private Context context;
        private List<FavouriteEntity> favList ;  //data entity/table
        FavDatabase myFavdb; //storing the database
        private RecyclerViewAdapter.OnCardListener mOnCardListener;

        public favAdapter(Context context)
        {
                this.context = context;
//                this.mOnDeleteClickListener = listner;
        }

        // Function to getdata for recycleview adopter. ( from searched items)
        public void setFavList(List<FavouriteEntity> favList)
        {
                this.favList = favList;
                notifyDataSetChanged();
        }

        @NonNull
        @Override
        public favAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
                View view = LayoutInflater.from(context).inflate(R.layout.activity_fav_view, parent, false);

                return new MyViewHolder(view, mOnCardListener);
        }

        @Override
        public void onBindViewHolder(@NonNull final favAdapter.MyViewHolder holder, final int position)
        {
             final FavouriteEntity currentposition = favList.get(position);
             myFavdb = FavDatabase.getDbInstance(context);
            holder.titleTextView.setText(this.favList.get(position).address);
            holder.textDisciption.setText(this.favList.get(position).title);

            String imageUrl = this.favList.get(position).photoReference;

            //setting image to the image view
            Picasso.get().load(imageUrl).fit().centerInside().into(holder.ImageUrl);

            // deleting favourite.
            holder.deleteFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // data to be used in other activity
                    Log.d("TAG", "onClick: favourite_View " );
                    FavouriteEntity d = favList.get(holder.getAdapterPosition());  //getting the position of current item with deletebutton.
                    myFavdb.favouriteDoa().delete(d);                              // deleting the item;

                    // notify when data is deleted;
                    notifyDataSetChanged();
//                    int position = holder.getAdapterPosition();
                    Log.d("TAG", "onClick: Removeitem position " + position);
                    favList.remove(currentposition);

                }
            });


        }

        @Override
        public int getItemCount()
        {
            return  this.favList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            TextView titleTextView, textDisciption;
            ImageView ImageUrl;
            RecyclerViewAdapter.OnCardListener onCardListener;

            ImageButton deleteFav;   // delete button on favourite cardview;

            public MyViewHolder(View view , RecyclerViewAdapter.OnCardListener onCardListener) {
                super(view);
                titleTextView = view.findViewById(R.id.favTitle);
                textDisciption = view.findViewById(R.id.favDesc);
                ImageUrl = view.findViewById(R.id.favImg);
                deleteFav = itemView.findViewById(R.id.deleteFav);
                this.onCardListener = onCardListener;


            }


            //adapter onclick listner.
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
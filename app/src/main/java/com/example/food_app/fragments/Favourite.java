package com.example.food_app.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food_app.R;
import com.example.food_app.RecycleView.Items;
import com.example.food_app.RecycleView.favAdapter;
import com.example.food_app.RecycleView.mAdopter;
import com.example.food_app.database.FavDatabase;
import com.example.food_app.database.FavouriteEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Favourite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favourite extends Fragment {

    private ArrayList<Items> mDataList = new ArrayList<>();

    private mAdopter favRecycleViewAdopter;
    private RecyclerView recylerView;

    String lat,lng , places_name, title, imageUrl = "favourite_View";
    private SQLiteDatabase mDatabase;
    private favAdapter favAdapter;







    public Favourite() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Favourite newInstance() {
        Favourite fragment = new Favourite();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initRecyclerView(View view) {
//        recylerView = view.findViewById(R.id.recyclerView);
//        recylerView.setHasFixedSize(true);
//        recylerView.setLayoutManager ( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerView recyclerView = view.findViewById(R.id.favrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        favAdapter = new favAdapter(getContext());
        recyclerView.setAdapter(favAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favourite, container, false);
        Log.d("TAG", "onCreateView: " + "Favourite fragment");


//        recylerView = view.findViewById(R.id.favrecyclerView);
//        recylerView.setHasFixedSize(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
             title = bundle.getString("title", "");
             imageUrl = bundle.getString("url", "");
             places_name = bundle.getString("places_name", "");
             lat = bundle.getString("lat", "");
             lng = bundle.getString("lng", "");


//            recylerView.setLayoutManager ( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//
//            favRecycleViewAdopter = new mAdopter(mDataList, getContext());
//            mDataList.add(new Items(places_name, CreateName,imageUrl , lat,lng));
//            recylerView.setAdapter(favRecycleViewAdopter);
//            favRecycleViewAdopter.notifyDataSetChanged();
//            initRecyclerView(view);

            // saving the data recieveved from homeactivity to local db (FavouriteDatabase)
            SaveFavourite(places_name, title, imageUrl, lat, lng);

        }
        initRecyclerView(view);

        loadFavourite();
//        initRecyclerView(view);


        Log.d("TAG", "t.....t: " + places_name);
        Log.d("TAG", "t.....t: " + imageUrl);
        Log.d("TAG", "t.....t: " + lat);
        Log.d("TAG", "t.....t: " + lng);
//        Items items = new Items();


        return view;
    }




     private void SaveFavourite(String title, String Addresss, String ImgeUrl, String Lat, String Lng){

        // assigning values from to favourite database;
         FavDatabase db =  FavDatabase.getDbInstance(getContext().getApplicationContext());
         FavouriteEntity fav = new FavouriteEntity();
         fav.address = Addresss;
         fav.title = title;
         fav.photoReference = ImgeUrl;
         fav.lat = Lat;
         fav.lng = Lng;

         db.favouriteDoa().insertFavourite(fav);

        finish();
     }

    private void finish() {
    }


    private void loadFavourite (){
        FavDatabase db = FavDatabase.getDbInstance(getContext().getApplicationContext());
        List<FavouriteEntity> fav = db.favouriteDoa().getAll();
         favAdapter.setFavList(fav);
//        mDatabase.add(new fav);
//         Log.d("TAG", "loadFavourite: " + fav.get(0).Name);

     }


}
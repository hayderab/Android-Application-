package com.example.food_app.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.R;
import com.example.food_app.RecycleView.Fooditems;
import com.example.food_app.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class Home extends Fragment implements RecyclerViewAdapter.OnCardListener {

    //private ArrayList<Items> DataList = new ArrayList<>();
    private ArrayList<Fooditems> DataList = new ArrayList<>(); // getting food items dataclass.
//    private ArrayList<Array> locationArray = new ArrayList<>();
    private String query;
    private String  Lastquery;
    private String lat;
    private String lng;

    private RequestQueue RequestQueue;
    private RecyclerViewAdapter myRecyclerViewAdapter;
    private RecyclerView recylerView;




    private String foodQuery;


    public static Home newInstance() {
        Home fragment = new Home();

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        initRecyclerView(view);

        // Accessing the food name based on image search.
        SharedPreferences sp = getContext().getApplicationContext().getSharedPreferences("mySharedPrefers", getContext().MODE_PRIVATE);
        foodQuery = sp.getString("name", "");
        Log.d("TAG", "testdatafrom preference: " +  foodQuery);

        SharedPreferences locSharedPreferences = getContext().getApplicationContext().getSharedPreferences("LocationSharedPrefers", getContext().MODE_PRIVATE);
        lat = locSharedPreferences.getString("lat", "");
        lng = locSharedPreferences.getString("lng", "");




//        Bundle bundle = this.getArguments();

//        if(bundle == null  && text == null ){
//            Toast.makeText(getActivity(), "Search Through Image", Toast.LENGTH_SHORT).show();
//
//        }
//        else{
//
//        }
//        buildListData();
//        saveData();

//        if (bundle != null || text != null){
//            query= bundle.getString("foodResult");
//            Toast.makeText(getActivity(), "if bundle not null"+ query + "", Toast.LENGTH_SHORT).show();
//            Log.d("TAG", "test data: "+ text);
//
//
//
//
//            buildListData();
//
//
////            Lastquery = query;
//        }
//        else{
//
//            Toast.makeText(getActivity(), "Search Through Image", Toast.LENGTH_SHORT).show();
//        }

//        Log.d("TAG", "get data from camera fragment activity...: " + query);
        // making sure the query is not empty.
        if(foodQuery != ""){
             buildListData();
         }else{
             Toast.makeText(getActivity(), "Search Through Image", Toast.LENGTH_SHORT).show();

         }



//        RequestQueue  = Volley.newRequestQueue(this);
        initRecyclerView(view);  // calling recycle view.
        return view;
    }

    private void initRecyclerView(View view) {
        // calling recycleview adapter
        recylerView = view.findViewById(R.id.recyclerView); // list activity to show the recycleview content in order.
        recylerView.setHasFixedSize(true);
        recylerView.setLayoutManager ( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void buildListData() {


//        if (query == null){
//            query = Lastquery;
//        }
//        String url = "https://jsonkeeper.com/b/USDZ";
//          String url=          "https://jsonkeeper.com/b/E0H9";


        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+foodQuery+"&" +
                "fields=photos,formatted_address,name,opening_hours,rating,geometry&type=resuturant, " +
                "food&location="+lng+","+lat+"&radius=1000&key=AIzaSyDzb-onk7HWYnUs3JWZNRnAjYVdCQA9KTE";

        //JSONObject root = new JSONObject(json_string);

        // Mkaing get requestion to get the json data from google API.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //successText.setText("Response: " + response.toString());
                        try {
                            // getting json array based on result property.
                            JSONArray jsonArray = response.getJSONArray("results");
//
                            String photo_reference = null;
//
                            for (int i= 0; i < 4  ; i++ ){
                                JSONObject candidateObject = jsonArray.getJSONObject(i);
                                //getting photo list from candidateobject
                                JSONArray  photos  = candidateObject.getJSONArray("photos");
                                JSONObject  geometry  = candidateObject.getJSONObject("geometry");
//                                //Getting JsonLocation Object.
                                JSONObject location = geometry.getJSONObject("location");
                                Log.d("tag", " Json bmm Geomatry...........: " + location.getString("lat") + location.getString("lng"));

                                // adding location data to the list
                                 ArrayList<String> locdata = new ArrayList<String>();
                                    locdata.add(location.getString("lat"));
                                    locdata.add(location.getString("lng"));
                                    Log.d("tag", "ladata" + locdata);

                              // going through nested json array.
                            for (int j= 0; j < photos.length() ; j++ ){
                                //creating photo object
                                JSONObject photoObj = photos.getJSONObject(j);
                                //getting values from photos list
                                photo_reference = photoObj.getString("photo_reference");

                            }

                             // getting the image link.
                               String photoURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&" +
                                  "photoreference="+photo_reference+
                                  "&key=AIzaSyDzb-onk7HWYnUs3JWZNRnAjYVdCQA9KTE";

                                Fooditems fooditems = new Fooditems();
                                fooditems.setPhotoReference(photoURL);

                                String CreateName = candidateObject.getString("name");
                                String  Address = candidateObject.getString("formatted_address");


                                // calling recycle view adapter and set the data.
                                myRecyclerViewAdapter = new RecyclerViewAdapter(DataList, getContext());
                                DataList.add(new Fooditems(Address, CreateName,photoURL , locdata));
                                recylerView.setAdapter(myRecyclerViewAdapter);
                                myRecyclerViewAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("tag", "data not found" + error.getMessage());

                    }
                })
        {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                // Saves the Recents Searches in cache
                // Code Reference: https://medium.com/android-grid/how-to-use-volley-cache-android-studio-be59cba08861
                //----------------------------------------------------------------------------
            try {
                Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                if (cacheEntry == null) {
                    cacheEntry = new Cache.Entry();
                }
                final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                long now = System.currentTimeMillis();
                final long softExpire = now + cacheHitButRefreshed;
                final long ttl = now + cacheExpired;
                cacheEntry.data = response.data;
                cacheEntry.softTtl = softExpire;
                cacheEntry.ttl = ttl;
                String headerValue;
                headerValue = response.headers.get("Date");
                if (headerValue != null) {
                    cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }
                headerValue = response.headers.get("Last-Modified");
                if (headerValue != null) {
                    cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }
                cacheEntry.responseHeaders = response.headers;
                final String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                return Response.success(new JSONObject(jsonString), cacheEntry);
            } catch (UnsupportedEncodingException | JSONException e) {
                return Response.error(new ParseError(e));
            }
        }

            @Override
            protected void deliverResponse(JSONObject response) {
            super.deliverResponse(response);
        }

            @Override
            public void deliverError(VolleyError error) {
            super.deliverError(error);
        }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
            return super.parseNetworkError(volleyError);
        }//--------------------------------------------------------------------------------------------
        };
//        RequestQueue.add(jsonObjectRequest);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);



    }






//    public void loadData() {
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, getContext().MODE_PRIVATE);
//        text = sharedPreferences.getString(TEXT, "");
//
//
//    }





//    private void buildListData() {
//
//
////        DataList.add(new Items("Avengers", "tester", 1));
////        DataList.add(new Items("dfad", "tester", 1));
////        DataList.add(new Items("dfadf", "tester", 1));
//        String url = "https://pixabay.com/api/?key=20515751-ccec5da00e7f738c512da78c8&q=yellow+flowers&image_type=photo&pretty=true";
//        //JSONObject root = new JSONObject(json_string);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//
//                        //successText.setText("Response: " + response.toString());
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("hits");
////                            init();
//
//                            for (int i= 0; i < response.length(); i++ ){
////                                JSONObject itemsObject = jsonArray.getJSONObject(i);
////                                //Log.d("tag", "data" + cardObject.getString("code"));
//////                                Items items = new Items();
////                                String CreateName = itemsObject.getString("user");
////                                String ImageURl = itemsObject.getString("webformatURL");
////                                int likeCount = itemsObject.getInt("likes");
////                                Log.d("tag", ",,,,,,,,,,,,,,," + CreateName);
////                                DataList.add(new Items(ImageURl, CreateName, likeCount));
//
//
//                                myRecyclerViewAdapter = new RecyclerViewAdapter(DataList, getContext());
//                                recylerView.setAdapter(myRecyclerViewAdapter);
//
//                            }
////
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("tag", "data not found" + error.getMessage());
//
//                    }
//                });
//
////        RequestQueue.add(jsonObjectRequest);
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        queue.add(jsonObjectRequest);
//
//
//
//    }



//    @Override
//    public void onItemClick(Fooditems dataModel) {
//
////       Fragment fragment = DetailFragment.newInstance(dataModel.getMcreater());
////
////
////        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
////        // transaction.replace(R.id.frame_container, fragment, "detail_fragment");
////
////        transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("Home_fragment"));
////        transaction.add(R.id.frame_Container, fragment);
////        transaction.addToBackStack(null);
////        transaction.commit();
//    }

    @Override
    public void onCardClicked(int position) {
        //       Fragment fragment = DetailFragment.newInstance(dataModel.getMcreater());
        Log.d("TAG", "onCardClicked: test");
//
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        // transaction.replace(R.id.frame_container, fragment, "detail_fragment");
//
//        transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("Home_fragment"));
//        transaction.add(R.id.frame_Container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }
}
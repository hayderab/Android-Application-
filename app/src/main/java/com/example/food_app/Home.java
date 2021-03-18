package com.example.food_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.food_app.RecycleView.Fooditems;
import com.example.food_app.RecycleView.mAdopter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Home extends Fragment implements RecyclerViewAdapter.OnCardListener {

    //private ArrayList<Items> DataList = new ArrayList<>();
    private ArrayList<Fooditems> DataList = new ArrayList<>();
//    private ArrayList<Array> locationArray = new ArrayList<>();

    private RequestQueue RequestQueue;
    private RecyclerViewAdapter myRecyclerViewAdapter;
    private RecyclerView recylerView;

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
        buildListData();
//        RequestQueue  = Volley.newRequestQueue(this);

        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        recylerView = view.findViewById(R.id.recyclerView);
        recylerView.setHasFixedSize(true);
        recylerView.setLayoutManager ( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(DataList, getContext());
//        recyclerView.setAdapter(adapter);
    }

    private void buildListData() {


//        DataList.add(new Items("Avengers", "tester", 1));
//        DataList.add(new Items("dfad", "tester", 1));
//        DataList.add(new Items("dfadf", "tester", 1));
        String url = "https://jsonkeeper.com/b/USDZ";
//          String url=          "https://jsonkeeper.com/b/E0H9";

//        String url2 = "https://places.demo.api.here.com/places/v1/discover/search?q="+query+"&Geolocation=" +
//                "geo%3A52.43466472882982%2C-1.8463897705078125&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg#";
        //JSONObject root = new JSONObject(json_string);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        //successText.setText("Response: " + response.toString());
                        try {

                            // Getting whole json object
                            //JSONArray jsonArray = response.getJSONArray("candidates");
                            // getting Josn data based on result parameter.
                            JSONArray jsonArray = response.getJSONArray("results");
//                        JSONArray  photos = data.getJSONArray("photos");
//                            Log.d("tag", "printing" + jsonArray);
                            String photo_reference = null;
//                            String location = null;

//                        init();
                            for (int i= 0; i < 2  ; i++ ){

                                JSONObject candidateObject = jsonArray.getJSONObject(i);
//                                Log.d("tag", "testing" + candidateObject);

                                //getting photo list from candidateobject
                                JSONArray  photos  = candidateObject.getJSONArray("photos");
                                JSONObject  geometry  = candidateObject.getJSONObject("geometry");
//
//                                Log.d("tag", " Json object Geomatry...........: " + geometry);
                                //Getting JsonLocation Object.
                                JSONObject location = geometry.getJSONObject("location");
                                Log.d("tag", " Json bmm Geomatry...........: " + location.getString("lat") + location.getString("lng"));

                                 ArrayList<String> locdata = new ArrayList<String>();
                                    locdata.add(location.getString("lat"));
                                    locdata.add(location.getString("lng"));


                                    Log.d("tag", "ladata" + locdata);

//                                for (int j= 0; j < geometry.length() ; j++ ){
//                                    //creating photo object
////                                    JSONObject location = geometry.getJSONObject("location");
////                                    JSONObject southwest = geometry.getJSONObject("southwest");
//
//                                    //getting values from photos list
////                                    location = gallObj.getString("location");
//                                    Log.d("tag", " Json bmm Geomatry...........: " + location);
////                                    Log.d("tag", " Json bmm Geomatry...........: " + southwest);
//
//
//                                }


                            for (int j= 0; j < photos.length() ; j++ ){
                                //creating photo object
                                JSONObject photoObj = photos.getJSONObject(j);
                                //getting values from photos list
                                photo_reference = photoObj.getString("photo_reference");

                            }


                               String photoURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&" +
                                  "photoreference="+photo_reference+
                                  "&key=AIzaSyDzb-onk7HWYnUs3JWZNRnAjYVdCQA9KTE";

                                Fooditems fooditems = new Fooditems();
                                fooditems.setPhotoReference(photoURL);

                                String CreateName = candidateObject.getString("name");
                                String  Address = candidateObject.getString("formatted_address");

                                String Geomatry = candidateObject.getString("geometry");

                                //Log.d("tag", "data not found" + Geomatry );
                                int Rating = 0;

//                            fooditems.setName(candidateObject.getString("name").toString());
//                            fooditems.setAddress(candidateObject.getString("formatted_address").toString());

                                myRecyclerViewAdapter = new RecyclerViewAdapter(DataList, getContext());
                                DataList.add(new Fooditems(Address, CreateName,photoURL , locdata));
                                recylerView.setAdapter(myRecyclerViewAdapter);
                                myRecyclerViewAdapter.notifyDataSetChanged();
                            }
//



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
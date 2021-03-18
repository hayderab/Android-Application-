package com.example.food_app.RecycleView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.Items;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class recycleviewList extends AppCompatActivity {


    private RecyclerView RecylerView;
    private mAdopter adopter;
//    private ArrayList<Items> DataList;
    private ArrayList<Fooditems> DataList;

    private RequestQueue RequestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_list);

        RecylerView = findViewById(R.id.listRecycleView);
        RecylerView.setHasFixedSize(true);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));

        DataList = new ArrayList<>();

        RequestQueue = Volley.newRequestQueue(this);
        extractApiData();


    }


//    private void extractApiData() {
//
//        //final TextView textView = (TextView) findViewById(R.id.textView2);
////        RequestQueue queue = Volley.newRequestQueue(this);
//        //String url ="https://www.google.com";
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
//                            for (int i= 0; i < response.length(); i++ ){
//                                JSONObject itemsObject = jsonArray.getJSONObject(i);
//                                //Log.d("tag", "data" + cardObject.getString("code"));
////                                Items items = new Items();
//                                String CreateName = itemsObject.getString("user");
//                                String ImageURl = itemsObject.getString("webformatURL");
//                                int likeCount = itemsObject.getInt("likes");
//                                Log.d("tag", ",,,,,,,,,,,,,,," + CreateName);
//                                DataList.add(new Items(ImageURl, CreateName, likeCount));
//
//                            }
//
//                            adopter = new mAdopter(recycleviewList.this, DataList);
//                            RecylerView.setAdapter(adopter);
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
//        RequestQueue.add(jsonObjectRequest);
//
//    }
private void extractApiData() {

    //final TextView textView = (TextView) findViewById(R.id.textView2);
//    RequestQueue queue = Volley.newRequestQueue(this);
//
//    String query = "pizza";
//    String key = "";
//    String location = "";

//    String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=pizza" +
//            "&inputtype=textquery&fields=photos,formatted_address,name,opening_hours,rating&" +
//            "locationbias=circle:10000@3A52.43466472882982%2C-1.8463897705078125&pagetoken=5&" +
//            "key=AIzaSyDzb-onk7HWYnUs3JWZNRnAjYVdCQA9KTE";
//        String url = "https://jsonkeeper.com/b/2V8W";
    String url = "https://jsonkeeper.com/b/USDZ";
//          String url=          "https://jsonkeeper.com/b/E0H9";

//        String url2 = "https://places.demo.api.here.com/places/v1/discover/search?q="+query+"&Geolocation=" +
//                "geo%3A52.43466472882982%2C-1.8463897705078125&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg#";
    //JSONObject root = new JSONObject(json_string);

//    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//
//
//                    //successText.setText("Response: " + response.toString());
//                    try {
//
//                        // Getting whole json object
//                        //JSONArray jsonArray = response.getJSONArray("candidates");
//                        JSONArray jsonArray = response.getJSONArray("results");
//
////                        JSONArray  photos = data.getJSONArray("photos");
//                        Log.d("tag", "printing" + jsonArray);
//                        String photo_reference = null;
//
////                        init();
//                        for (int i= 0; i < 7  ; i++ ){
//
//                            //
//                            JSONObject candidateObject = jsonArray.getJSONObject(i);
//
//                            //getting photo list from candidateobject
//                            JSONArray  photos  = candidateObject.getJSONArray("photos");
//                            Log.d("tag", " photos: " + candidateObject);
//
////                            for (int j= 0; j < photos.length() ; j++ ){
////                                //creating photo object
////                                JSONObject photoObj = photos.getJSONObject(j);
////
////                                //getting values from photos list
////                                photo_reference = photoObj.getString("photo_reference");
////
////                            }
//
//                          String photoURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&" +
//                                  "photoreference="+photo_reference+
//                                  "&key=AIzaSyDzb-onk7HWYnUs3JWZNRnAjYVdCQA9KTE";
////
////                            Fooditems fooditems = new Fooditems();
////                            fooditems.setPhotoReference(photoURL);
//
////                            String CreateName = candidateObject.getString("name");
////                            String  Address = candidateObject.getString("formatted_address");
////                            int Rating = 0;
//
////                            fooditems.setName(candidateObject.getString("name").toString());
////                            fooditems.setAddress(candidateObject.getString("formatted_address").toString());
////                            DataList.add(new Fooditems(Address, CreateName));
//
////                            DataList.add(fooditems);
//                            Fooditems fooditems = new Fooditems();
//                            fooditems.setPhotoReference(photoURL);
//
//                            String CreateName = candidateObject.getString("name");
//                            String  Address = candidateObject.getString("formatted_address");
////                            String Geomatry = candidateObject.getString("geometry");;
//                            Log.d("tag", "data not found" + Geomatry );
//                            int Rating = 0;
//                            DataList.add(new Fooditems(Address, CreateName,photoURL , Geomatry));
//
//                        }
//
//                        adopter = new mAdopter(recycleviewList.this, DataList);
//                        RecylerView.setAdapter(adopter);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.d("tag", "data not found" + error.getMessage());
//
//                }
//            });
//
//
//    RequestQueue.add(jsonObjectRequest);
//
//
}
}
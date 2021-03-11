package com.example.food_app.RecycleView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
    private ArrayList<Items> DataList;
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


    private void extractApiData() {

        //final TextView textView = (TextView) findViewById(R.id.textView2);
//        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="https://www.google.com";
        String url = "https://pixabay.com/api/?key=20515751-ccec5da00e7f738c512da78c8&q=yellow+flowers&image_type=photo&pretty=true";
        //JSONObject root = new JSONObject(json_string);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        //successText.setText("Response: " + response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");
//                            init();
                            for (int i= 0; i < response.length(); i++ ){
                                JSONObject itemsObject = jsonArray.getJSONObject(i);
                                //Log.d("tag", "data" + cardObject.getString("code"));
//                                Items items = new Items();
                                String CreateName = itemsObject.getString("user");
                                String ImageURl = itemsObject.getString("webformatURL");
                                int likeCount = itemsObject.getInt("likes");
                                Log.d("tag", ",,,,,,,,,,,,,,," + CreateName);
                                DataList.add(new Items(ImageURl, CreateName, likeCount));

                            }

                            adopter = new mAdopter(recycleviewList.this, DataList);
                            RecylerView.setAdapter(adopter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("tag", "data not found" + error.getMessage());

                    }
                });

        RequestQueue.add(jsonObjectRequest);

    }
}
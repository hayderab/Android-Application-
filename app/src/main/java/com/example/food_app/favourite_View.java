package com.example.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class test extends AppCompatActivity {


    String lat;
    String lng;
    String Address, Title, ImageUrl;

    TextView tilte, description;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        imageView = findViewById(R.id.favImg);

//        getIntentData();


    }


    private void getIntentData(){
        //Getting data from another activity (RecycleView)
        Log.d("tag", "getIntentData: checking for incoming data");

        // Checking if there is any data  avilable
        if (getIntent().hasExtra("places_name")){
            Address = getIntent().getStringExtra("places_name");
            Title = getIntent().getStringExtra("title");
            ImageUrl = getIntent().getStringExtra("url");
            String coordination = getIntent().getStringExtra("places_coordination");

            lat = getIntent().getStringExtra("lat");
            lng = getIntent().getStringExtra("lng");
            Log.d("tag", "getIntentData........: " + lat +  " : " + lng );
            tilte = findViewById(R.id.favTitle);
            tilte.setText(Title);
            description = findViewById(R.id.favDesc);
            description.setText(Address);
            Log.d("TAG", "getIntentData: " + ImageUrl);
            Picasso.get().load(ImageUrl).fit().centerInside().into(imageView);

//            textView.setText(coordination);

        }
    }
}
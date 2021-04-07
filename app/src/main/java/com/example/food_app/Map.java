package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity  implements OnMapReadyCallback {
    TextView textView;
    private GoogleMap mMap;
    private boolean isLocationPremEnabled;
    private static final int PRMISSION_REQUEST_CODE  = 3002;
    String lat;
    String lng;
    String Address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //textView = findViewById(R.id.addressDisplay);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//
//        mapFragment.getMapAsync(Map.this);

        getIntentData();
//        getUserPremission();
        initMap();

    }

    public void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mymap);

        mapFragment.getMapAsync(Map.this);
    }

    private void getUserPremission() {
        // Getting user permission
        if (isLocationPremEnabled){
            Toast.makeText(this, "readyMap", Toast.LENGTH_SHORT).show();
        } else {
            // Checking if we got GPS permission
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PRMISSION_REQUEST_CODE);
                }

            }
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Making use of granted permission
        if (requestCode == PRMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            isLocationPremEnabled  = true;
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();

        }
    }

    private void getIntentData(){
        //Getting data from another activity (RecycleView)
        Log.d("tag", "getIntentData: checking for incoming data");

        // Checking if there is any data  avilable
        if (getIntent().hasExtra("places_name")){
            Address = getIntent().getStringExtra("places_name");
            String coordination = getIntent().getStringExtra("places_coordination");
            lat = getIntent().getStringExtra("lat");
            lng = getIntent().getStringExtra("lng");

            Log.d("tag", "getIntentData........: " + lat + lng );

//            textView.setText(coordination);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, "MAP Ready", Toast.LENGTH_SHORT).show();
        // Add a marker in Sydney and move the camera
        LatLng mylocation  = new LatLng(Double.parseDouble(lat) , Double.parseDouble(lng));
//        LatLngBounds BirminghamBounds = new LatLngBounds(
//                new LatLng(-44, 113), // SW bounds
//                new LatLng(-10, 154)  // NE bounds
//        );
        mMap.addMarker(new MarkerOptions()
                .position(mylocation)
                .title(Address));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 17));


    }
}
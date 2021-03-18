package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.food_app.RecycleView.recycleviewList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private boolean isLocationPremEnabled;
    public static final int PRMISSION_REQUEST_CODE  = 3002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom navigation
        BottomNavigationView navView = findViewById(R.id.bottomNav_view);
        navView.setOnNavigationItemSelectedListener(navListner);

        // setting a main Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, new Home()).commit();




        Fragment fragment = Home.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_Container, fragment, "Home_fragment");

    }



    //Listener  for navigation bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item){
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.homeFragment:
                            selectedFragment = new Home();

//                            startActivity(new Intent(MainActivity.this, recycleviewList.class));
                            break;
                        case R.id.camera_fragment:
                            //Toast.makeText(MainActivity.this, "Camera ............", Toast.LENGTH_SHORT).show();
                            getUserPremission();
//                            startActivity(new Intent(MainActivity.this, CameraView.class));
                            selectedFragment = new Camera();
                            break;

                        case R.id.favouritefragment:
                            selectedFragment = new Favourite();
                            break;

                    }
                    // start of the transaction
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout
                                    ,selectedFragment).commit();

                    return true;
                }
            };


    private void getUserPremission() {
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
        if (requestCode == PRMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            isLocationPremEnabled  = true;
            startActivity(new Intent(MainActivity.this, CameraView.class));

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();

        }
    }
}
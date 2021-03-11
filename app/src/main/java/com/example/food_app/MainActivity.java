package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.food_app.RecycleView.recycleviewList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom navigation
        BottomNavigationView navView = findViewById(R.id.bottomNav_view);
        navView.setOnNavigationItemSelectedListener(navListner);

        // setting a main Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, new Favourite()).commit();

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
                            startActivity(new Intent(MainActivity.this, recycleviewList.class));
                            break;
                        case R.id.camera_fragment:
                            //Toast.makeText(MainActivity.this, "Camera ............", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(MainActivity.this, CameraView.class));
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
}
package com.example.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.food_app.auth.login;

public class splashScreen extends AppCompatActivity {
    private  static  int SPLASH_SCREEN =1500; // time it take to transit do different activity.
    ImageView imageView;
    TextView textView1, textView2;
    Animation top, bottom;

    public static boolean isSplashScreenLoaded(boolean result) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        imageView = findViewById(R.id.img_view_logo);


        top = AnimationUtils.loadAnimation(this, R.anim.top);   //calling xml animation file.
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);

        imageView.setAnimation(top);                       //assigning animiation to imageview


        new Handler().postDelayed(new Runnable() // loading new activity after animation and splash screen
        {
            @Override
            public void run() {
                Intent intent = new Intent(splashScreen.this, login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }


}
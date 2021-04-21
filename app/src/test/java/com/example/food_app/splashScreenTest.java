package com.example.food_app;

import com.example.food_app.auth.signup;

import org.junit.Test;

import static org.junit.Assert.*;

public class splashScreenTest {


    public splashScreen mySplashScreen;

    private boolean result;


    @Test
    public void isSplashScreenLoaded(){
        result = true;
        assertEquals(true, splashScreen.isSplashScreenLoaded(result));

    }



}
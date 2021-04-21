package com.example.food_app;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {


    private MainActivity mainActivity;

    private boolean result;


    @Test
    public void isMainActivityLoaded(){
        result = true;
        mainActivity = new MainActivity();


        assertEquals(true, MainActivity.isMainActivityLoaded(result));

    }


}
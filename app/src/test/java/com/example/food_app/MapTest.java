package com.example.food_app;

import com.example.food_app.auth.login;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapTest {

    private Map myMap;

    private boolean result;


    @Test
    public void isMapLoaded(){
        result = true;
        myMap = new Map();

        assertEquals(true, Map.isMapLoaded(result));


    }


}
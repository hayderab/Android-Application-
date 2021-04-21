package com.example.food_app;

import com.example.food_app.auth.signup;

import org.junit.Test;

import static org.junit.Assert.*;

public class signupTest {

    private signup Sigup;

    private boolean result;

    @Test
    public void isRegistered(){
        result = true;
        Sigup = new signup();

        assertEquals(true, Sigup.isRegistered(result));
    }

}
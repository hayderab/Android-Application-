package com.example.food_app.fragments;

import android.app.Activity;

import com.example.food_app.auth.signup;

import org.junit.Test;

import static org.junit.Assert.*;

public class CameraTest {

    Activity activity;

    private load_dialog dialog;

    private boolean result;


    @Test
    public void custormProgressbar() {
        result = true;
        dialog = new load_dialog(activity);
        assertEquals(true, load_dialog.progressBarShowing(result));

    }

}
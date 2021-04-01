package com.example.food_app.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.food_app.R;

public class load_dialog {


     private Activity activity;
     private AlertDialog dialog;

    load_dialog(Activity myActivity){
        activity = myActivity;
    }


     void startLoadingDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null ));
        builder.setCancelable(true);

        dialog  = builder.create();
        dialog.show();


    }

     void dismissDialog(){
        dialog.dismiss();
    }

}

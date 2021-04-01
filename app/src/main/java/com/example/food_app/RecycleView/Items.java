package com.example.food_app.RecycleView;

import java.util.ArrayList;

public class Items {


    String address;
    String Name;
    String  photoReference ;
    String  lat;
    String lng ;

    public Items() {
    }



    public Items(String address, String Name, String photoReference, String lat, String lng) {
        this.address = address;
        this.Name = Name;
        this.photoReference = photoReference;
        this.lat = lat;
        this.lng = lng;

    }


    public String getAddress() {
        return address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public String getLat() {
        return lat;
    }


    public String getLng() {
        return lng;
    }




}



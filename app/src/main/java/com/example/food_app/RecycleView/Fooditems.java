package com.example.food_app.RecycleView;

import java.util.ArrayList;
import java.util.List;

public class Fooditems {

    String address;
    String Name;
    String  photoReference ;
    String  photowidth;
     ArrayList<String> Geomatry ;
//    String Geomatry ;

    int rating;


    public Fooditems() {}


    public ArrayList<String> getGeomatry() {
        return Geomatry;
    }

    public void setGeomatry(ArrayList<String> geomatry) {
        Geomatry = geomatry;
    }

    //public Fooditems(String address, String Name, String photoReference, int rating){
    public Fooditems(String address, String Name, String photoReference,ArrayList<String> Geomatry ){

        this.address = address;
        this.Name = Name;
        this.photoReference = photoReference;
        this.Geomatry = Geomatry;
//        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

//    public void setAddress(String address) {
//        this.address = address;
//    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
//
//    public String getPhotowidth() {
//        return photowidth;
//    }
//
//    public void setPhotowidth(String photowidth) {
//        this.photowidth = photowidth;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }


}


package com.example.food_app;

public class Items {


    String imageurl;
    String mcreater;
    int likes;


//    public Items() {
//    }


    public Items(String imageurl, String mcreater, int likes) {
        this.imageurl = imageurl;
        this.mcreater = mcreater;
        this.likes = likes;

    }

    public String getImageurl() {

        return imageurl;
    }


    public String getMcreater() {
        return mcreater;
    }


    public int getLikes() {

        return likes;
    }
}

//    String imageurl;
//    String value;
//    String  suit ;
//    String  code;


    // Constructor

//    public Items() {}
//    public Items(String imageurl, String value, String suit, String code){
//
//        this.imageurl = imageurl;
//        this.value = value;
//        this.suit = suit;
//        this.code = code;
//    }

//   // setter and getter for data handling.
//    public String getImageurl() {
//        return imageurl;
//    }
//
//    public void setImageurl(String imageurl) {
//        this.imageurl = imageurl;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    public String getSuit() {
//        return suit;
//    }
//
//    public void setSuit(String suit) {
//        this.suit = suit;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//}

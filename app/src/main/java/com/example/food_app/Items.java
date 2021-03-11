package com.example.food_app;

public class Cards {

    String imageurl;
    String value;
    String  suit ;
    String  code;


    // Constructor

    public Cards() {}
    public Cards(String imageurl, String value, String suit, String code){

        this.imageurl = imageurl;
        this.value = value;
        this.suit = suit;
        this.code = code;
    }

   // setter and getter for data handling.
    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

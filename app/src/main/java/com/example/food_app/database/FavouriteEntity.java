package com.example.food_app.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavouriteEntity {
// Table properties/columns

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "Address")
    public String address;

    @ColumnInfo(name = "Title")
    public String title;


    @ColumnInfo(name = "photolink")
    public String  photoReference ;


    @ColumnInfo(name = "lat")
    public String  lat;

    @ColumnInfo(name = "lng")
    public String lng ;




    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }



}

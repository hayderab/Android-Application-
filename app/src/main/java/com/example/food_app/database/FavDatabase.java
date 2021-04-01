package com.example.food_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// creating database for sorting favourite;
@Database(entities = {FavouriteEntity.class}, version = 1)
public abstract class FavDatabase extends RoomDatabase {

    public abstract FavouriteDoa favouriteDoa();

    // favourite database instance / used to access data in context.
    private static FavDatabase INSTANCE;

    public static FavDatabase getDbInstance(Context context){
        if(INSTANCE == null ){
            INSTANCE  = Room.databaseBuilder(context.getApplicationContext(), FavDatabase.class, "FavouriteFood")
            .allowMainThreadQueries()
            .build();
        }

        return INSTANCE;
    }


}

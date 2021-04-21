package com.example.food_app.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.food_app.database.FavDatabase;
import com.example.food_app.database.FavouriteDoa;
import com.example.food_app.database.FavouriteEntity;
import com.example.food_app.fragments.Favourite;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Attr;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FavDatabaseTest {
    public FavouriteDoa favouriteDoa;
    private FavDatabase db;

    FavouriteEntity fav = new FavouriteEntity();
   FavouriteEntity favour =  new FavouriteEntity();

   public void  insetIntodb(){

       FavouriteEntity favour =  new FavouriteEntity();
       favour.setAddress("Coventry University");
       favour.setTitle("test");
       favour.setLat("00000");
       favour.setLng("00000");
       favour.setPhotoReference("ddddd");
       favouriteDoa.insertFavourite(favour);


   }


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, FavDatabase.class).build();
        favouriteDoa = db.favouriteDoa();
    }


    @After
    public void closeDb() throws IOException {
        db.close();
    }


    @Test
    public void writeFavAndReadInList() throws InterruptedException  {


        // Saving to local database and checking if data in entered and it exit in the databaes

        favour.setAddress("Coventry University");
        favour.setTitle("test");
        favour.setLat("00000");
        favour.setLng("00000");
        favour.setPhotoReference("ddddd");
        favouriteDoa.insertFavourite(favour);

        List<FavouriteEntity> favList = favouriteDoa.loadFavByTitle("test");
        String title = favour.getTitle();

        assertTrue(title == "test");
        Log.d("TAG", "writeFavAndReadInList: "+ favList);

    }
    @Test
    public void deleteFavourite() throws InterruptedException  {
        insetIntodb();
        List<FavouriteEntity> favList = favouriteDoa.loadFavByTitle("test");
        FavouriteEntity d = favList.get(0);
        db.favouriteDoa().delete(d);
        List<FavouriteEntity> test = favouriteDoa.getAll();
        assertTrue(test.isEmpty());
    }






}
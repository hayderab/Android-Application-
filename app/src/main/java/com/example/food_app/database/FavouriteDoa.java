package com.example.food_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface FavouriteDoa {
// Action performed on database (database queries)
    @Query("SELECT * FROM FavouriteEntity")
    List<FavouriteEntity> getAll();

    @Query("SELECT * FROM FavouriteEntity WHERE uid IN (:userIds)")
    List<FavouriteEntity> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM FavouriteEntity")
    List<FavouriteEntity> collectionList ();

   @Insert
   Void insertFavourite(FavouriteEntity... FavouriteEntity);

   @Delete
    void delete (FavouriteEntity FavouriteEntity);

   @Delete
    void  deleteAll(List<FavouriteEntity> FavouriteEntity);
}

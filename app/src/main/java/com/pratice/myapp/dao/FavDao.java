package com.pratice.myapp.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.pratice.myapp.model.Favorite;

import java.util.List;

@androidx.room.Dao
public interface FavDao {
    @Insert
    void insert(Favorite fav);

    @Query("DELETE FROM favorite where user_id= :user_id and _id= :id")
    void delete(int user_id,String id);

    @Query("DELETE FROM favorite where user_id= :user_id")
    void deleteAllfav(int user_id);

    @Query("SELECT * FROM favorite WHERE user_id= :user_id ORDER BY _id ASC")
    List<Favorite> getAlluserFav(int user_id);

    @Query("SELECT _id FROM favorite WHERE user_id= :user_id ORDER BY _id ASC")
    List<String> getAlluserFav_ids(int user_id);

}

package com.pratice.myapp.dao;


import androidx.room.Insert;
import androidx.room.Query;

import com.pratice.myapp.model.Favorite;

import java.util.List;

@androidx.room.Dao
public interface FavDao {
    @Insert
    void insert(Favorite fav);

    @Query("DELETE FROM favorite where userId= :userId and favId= :id")
    void delete(int userId,String id);

    @Query("DELETE FROM favorite where userId= :userId")
    void deleteAllfav(int userId);

    @Query("SELECT * FROM favorite WHERE userId= :userId ORDER BY favId ASC")
    List<Favorite> getAlluserFav(int userId);

    @Query("SELECT favId FROM favorite WHERE userId= :userId ORDER BY favId ASC")
    List<String> getAlluserFav_ids(int userId);

}

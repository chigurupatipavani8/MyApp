package com.pratice.myapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pratice.myapp.model.User;

import java.util.List;

@androidx.room.Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Query("SELECT * FROM user")
    LiveData<List<User>> getUsers();
    @Query("SELECT * FROM user WHERE userId= :id")
    User getUserById(int id);
    @Query("SELECT * FROM user WHERE email= :email")
    User getUserByEmail(String email);
    @Query("SELECT * FROM user WHERE name= :name limit 1")
    User getUserByName(String name);
    @Query("SELECT * FROM user ORDER BY userId ASC")
    List<User> getAllCourses();
}

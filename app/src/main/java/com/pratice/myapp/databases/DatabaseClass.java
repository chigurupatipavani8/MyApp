package com.pratice.myapp.databases;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.pratice.myapp.dao.FavDao;
import com.pratice.myapp.dao.UserDao;

import com.pratice.myapp.model.Favorite;
import com.pratice.myapp.model.User;

@Database(entities = {User.class, Favorite.class}, version = 4)
public abstract class DatabaseClass extends RoomDatabase {
    private static DatabaseClass instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // this method is called when database is created
            // and below line is to populate our data.
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static synchronized DatabaseClass getInstance(Context context) {
        if (instance == null) {

            instance =  Room.databaseBuilder(context.getApplicationContext(), DatabaseClass.class, "my_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
//                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    // we are creating an async task class to perform task in background.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(DatabaseClass instance) {
            UserDao userDao = instance.userDao();
            FavDao favDao = instance.favDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
    public abstract UserDao userDao();
    public abstract FavDao favDao();
}

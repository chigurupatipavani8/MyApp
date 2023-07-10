package com.pratice.myapp.repository;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.pratice.myapp.dao.FavDao;
import com.pratice.myapp.dao.UserDao;
import com.pratice.myapp.databases.DatabaseClass;
import com.pratice.myapp.model.Favorite;
import com.pratice.myapp.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyRepository {
    private UserDao userDao;
    private FavDao favdao;
    private LiveData<List<FavDao>> allCourses;
    public MyRepository(Application application){
        DatabaseClass database = DatabaseClass.getInstance(application);
        userDao = database.userDao();
        favdao=database.favDao();
    }

    public String  insert(User user) {
        try {
            return (String) (new CreateAndUpdateAsyncTask(userDao).execute(user).get());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void addFav(Favorite favorite){
        new AddUserFavorite(favdao).execute(favorite);
    }
    public void deleteFav(Favorite favorite){
        new DeleteUserFavorite(favdao).execute(favorite);
    }
    public void deleteAllFav(int user_id){
        new DeleteAllUserFavorite(favdao).execute(user_id);
    }

    public List<Favorite> getUserFav(int user_id){
        try {
            return new FavList(favdao).execute(user_id).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getUserFav_ids(int user_id){
        try {
            return new FavList_ids(favdao).execute(user_id).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public User getUserByEmail(String email){
//        return userDao.getUserByEmail(email);
        try {
            return (User)(new GetUserByNameOrEmailAsyncTask(userDao).execute(email).get());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class FavList extends AsyncTask<Integer, Void, List<Favorite>> {
        private FavDao favDao;
        private FavList(FavDao favDao) {
            this.favDao=favDao;
        }

        @Override
        protected List<Favorite> doInBackground(Integer... models) {
            return favDao.getAlluserFav(models[0]);
        }
    }
    private static class FavList_ids extends AsyncTask<Integer, Void, List<String>> {
        private FavDao favDao;
        private FavList_ids(FavDao favDao) {
            this.favDao=favDao;
        }

        @Override
        protected List<String> doInBackground(Integer... models) {
            return favDao.getAlluserFav_ids(models[0]);
        }
    }


    private static class CreateAndUpdateAsyncTask extends AsyncTask<User, Void, String> {
        private UserDao userDao;
        private CreateAndUpdateAsyncTask(UserDao userdao) {
            this.userDao = userdao;
        }
        @Override
        protected String doInBackground(User... models) {
            try {
                userDao.insert(models[0]);
            }
            catch (SQLiteConstraintException e)
            {
                return e.getMessage();
            }
            return "success";
        }
    }
    private static class GetUserByNameOrEmailAsyncTask extends AsyncTask<String, Void, User> {
        private UserDao userDao;
        private GetUserByNameOrEmailAsyncTask(UserDao userdao) {
            this.userDao = userdao;
        }

        @Override
        protected User doInBackground(String... models) {
            return userDao.getUserByEmail(models[0]);
        }
    }

    private static class DeleteUserFavorite extends AsyncTask<Favorite, Void, Void> {
        private FavDao favDao;
        private DeleteUserFavorite(FavDao favdao) {
            this.favDao = favdao;
        }
        @Override
        protected Void doInBackground(Favorite... models) {
            favDao.delete(models[0].getUser_id(),models[0].get_id());
            return null;
        }
    }
    private static class DeleteAllUserFavorite extends AsyncTask<Integer, Void, Void> {
        private FavDao favDao;
        private DeleteAllUserFavorite(FavDao favdao) {
            this.favDao = favdao;
        }
        @Override
        protected Void doInBackground(Integer... models) {
            favDao.deleteAllfav(models[0]);
            return null;
        }
    }
    private static class AddUserFavorite extends AsyncTask<Favorite, Void, Void> {
        private FavDao favDao;
        private AddUserFavorite(FavDao favdao) {
            this.favDao = favdao;
        }
        @Override
        protected Void doInBackground(Favorite... models) {
            try {
                favDao.insert(models[0]);
            }
            catch (Exception e)
            {

            }
            return null;
        }
    }

}

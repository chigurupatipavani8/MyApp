package com.pratice.myapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pratice.myapp.model.Anime;
import com.pratice.myapp.model.Favorite;
import com.pratice.myapp.model.Genre;
import com.pratice.myapp.model.ResponseModel;
import com.pratice.myapp.model.User;
import com.pratice.myapp.network.ApiCreationAndCall;
import com.pratice.myapp.repository.MyRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewModel  extends AndroidViewModel {
    MutableLiveData<List<Genre>> genreList=new MutableLiveData<>();
    MutableLiveData<List<Anime>> animes=new MutableLiveData<>();
    List<Genre> genreData=new ArrayList<>();
    List<Anime> animeData=new ArrayList<>();
    MutableLiveData<User> user=new MutableLiveData<>();
    MutableLiveData<List<Favorite>> favList=new MutableLiveData<>();
    MutableLiveData<List<String>> faveListIds=new MutableLiveData<>();
    MutableLiveData<Favorite> fav=new MutableLiveData<>();
    List<Favorite> myFavList=new ArrayList<>();
    List<String> myFavListIds =new ArrayList<>();

    private MyRepository repository;
    User u;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository=new MyRepository(application);
    }
    public void intilazeGenreData() {
        if(genreList.getValue()==null || genreList.getValue().size()==0) {
            Call<List<Genre>> call = ApiCreationAndCall.getInstance().getApi().getGenre();
            call.enqueue(new Callback<List<Genre>>() {
                @Override
                public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                    if (response.body() != null) {
                        genreData = response.body();
                        genreList.setValue(genreData);
                    }
                }

                @Override
                public void onFailure(Call<List<Genre>> call, Throwable t) {
                    intilazeGenreData();
                }

            });
            if(animes.getValue()==null || animes.getValue().size()==0) {
                intilazeAnimeData();
            }
        }
    }
    public void intilazeAnimeData(){
        Call<ResponseModel> call1= ApiCreationAndCall.getInstance().getApi().getAnimes(1,2000);
        call1.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body()!=null){
                    animeData = response.body().getData();
                    animes.setValue(animeData);
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                intilazeAnimeData();
            }
        });
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("viewmodel","viewModel destroyed");
    }

    public LiveData<List<Genre>> getGenre(){
        return genreList;
    }
    public LiveData<List<Anime>> getAnime(){
        return animes;
    }

    public String addUser(User u){
        String message=repository.insert(u);
        return message;
    }

    public LiveData<User> getUser(String email){
        u=repository.getUserByEmail(email);
        user.setValue(u);
        return user;
    }
    public LiveData<User> getUserById(int userId){
        u=repository.getUserById(userId);
        user.setValue(u);
        return user;
    }

    public void addFav(Favorite f){
        repository.addFav(f);
    }
    public void deleteFav(Favorite f){
        repository.deleteFav(f);
    }
    public void deleteAllFav(int user_id){
        repository.deleteAllFav(user_id);
    }

    public LiveData<List<Favorite>> getAllFav(int user_id){
        myFavList=repository.getUserFav(user_id);
        if(myFavList==null){
            myFavList=new ArrayList<>();
        }
        favList.setValue(myFavList);
        return favList;
    }
    public LiveData<List<String>> getAllFavIds(int user_id){
        myFavListIds=repository.getUserFavIds(user_id);
        if(myFavListIds==null){
            myFavListIds=new ArrayList<>();
        }
        faveListIds.setValue(myFavListIds);
        return faveListIds;
    }

}

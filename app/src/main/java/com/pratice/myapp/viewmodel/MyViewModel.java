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
    MutableLiveData<List<Genre>> genre_list=new MutableLiveData<>();
    MutableLiveData<List<Anime>> animes=new MutableLiveData<>();
    List<Genre> genre_data=new ArrayList<>();
    List<Anime> anime_data=new ArrayList<>();
    MutableLiveData<User> user=new MutableLiveData<>();
    MutableLiveData<List<Favorite>> fav_list=new MutableLiveData<>();
    MutableLiveData<List<String>> fav_list_ids=new MutableLiveData<>();
    MutableLiveData<Favorite> fav=new MutableLiveData<>();
    List<Favorite> my_fav_list=new ArrayList<>();
    List<String> my_fav_list_ids =new ArrayList<>();

    private MyRepository repository;
    User u;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository=new MyRepository(application);
    }
    public void intilazeGenreData() {
        Call<List<Genre>> call = ApiCreationAndCall.getInstance().getApi().getGenre();
        call.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                if(response.body()!=null) {
                    genre_data = response.body();
                    genre_list.setValue(genre_data);
                }
            }
            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
            }
        });
    }
    public void intilazeAnimeData(){
        Call<ResponseModel> call1= ApiCreationAndCall.getInstance().getApi().getAnimes(1,1000);
        call1.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body()!=null){
                    anime_data = response.body().getData();
                    animes.setValue(anime_data);
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("viewmodel","viewModel destroyed");
    }

    public LiveData<List<Genre>> getGenre(){
        if(genre_list.getValue()==null || genre_list.getValue().size()==0){
            intilazeGenreData();
        }
        return genre_list;
    }
    public LiveData<List<Anime>> getAnime(){
        if(animes.getValue()==null || animes.getValue().size()==0){
            intilazeAnimeData();
        }
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
        my_fav_list=repository.getUserFav(user_id);
        if(my_fav_list==null){
            my_fav_list=new ArrayList<>();
        }
        fav_list.setValue(my_fav_list);
        return fav_list;
    }
    public LiveData<List<String>> getAllFav_ids(int user_id){
        my_fav_list_ids=repository.getUserFav_ids(user_id);
        if(my_fav_list_ids==null){
            my_fav_list_ids=new ArrayList<>();
        }
        fav_list_ids.setValue(my_fav_list_ids);
        return fav_list_ids;
    }

//    public LiveData<List<Anime>> getAnime(int page,int size,String genre){
//        Call<ResponseModel> call= ApiCreationAndCall.getInstance().getApi().getAnimes(page,size,"Fullmetal",genre,"ranking","asc");
//        call.enqueue(new Callback<ResponseModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                anime_data = response.body().getData();
//                animes.setValue(anime_data);
//            }
//            @Override
//            public void onFailure(Call<ResponseModel> call, Throwable t) {
//            }
//        });
//        return animes;
//    }
}

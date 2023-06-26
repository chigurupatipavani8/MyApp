package com.pratice.myapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pratice.myapp.model.Anime;
import com.pratice.myapp.model.Genre;
import com.pratice.myapp.model.ResponseModel;
import com.pratice.myapp.network.ApiCreationAndCall;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewModel  extends ViewModel {
    MutableLiveData<List<Genre>> genre_list=new MutableLiveData<>();
    MutableLiveData<List<Anime>> animes=new MutableLiveData<>();
    List<Genre> genre_data=new ArrayList<>();
    List<Anime> anime_data=new ArrayList<>();
//    MutableLiveData<List<String>> fav_list=new MutableLiveData<>();
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("viewmodel","viewModel destroyed");
    }

    public LiveData<List<Genre>> getGenre(){
        Call<List<Genre>> call= ApiCreationAndCall.getInstance().getApi().getGenre();
        call.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                genre_data = response.body();
                genre_list.setValue(genre_data);
            }
            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
            }
        });
        return genre_list;
    }
    public LiveData<List<Anime>> getAnime(int page,int size){
        Call<ResponseModel> call= ApiCreationAndCall.getInstance().getApi().getAnimes(page,size);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                anime_data = response.body().getData();
                animes.setValue(anime_data);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
        return animes;
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

package com.pratice.myapp.network;


import com.pratice.myapp.model.Genre;
import com.pratice.myapp.model.ResponseModel;

import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiCreationAndCall {
    public static ApiCreationAndCall instance;
    private final IMyApi api;
    private ApiCreationAndCall(){



        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .get()
//                    .addHeader("X-RapidAPI-Key", "1f345de1acmsh4bec8ed4a46a815p1fd876jsn81ad8b633c91")
//                    .addHeader("X-RapidAPI-Host", "anime-db.p.rapidapi.com")
                        .addHeader("X-RapidAPI-Key", "be9ac32bd6mshffe0f286b3486fbp158c2cjsncc79afae2d9b")
                        .addHeader("X-RapidAPI-Host", "anime-db.p.rapidapi.com")
//                        .addHeader("X-RapidAPI-Key", "13f9a66f85mshfee01a15889ebe8p1d0bc6jsne6e3a37b9c3c")
//                        .addHeader("X-RapidAPI-Host", "anime-db.p.rapidapi.com")
                    .build();
            return chain.proceed(request);
        });


        Retrofit ret=new Retrofit.Builder().baseUrl("https://anime-db.p.rapidapi.com/anime/").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        api=ret.create(IMyApi.class);
    }

    public static ApiCreationAndCall getInstance(){
        if(instance==null){
            instance=new ApiCreationAndCall();
        }
        return instance;
    }

    public IMyApi getApi(){
        return api;
    }

    public interface IMyApi{

        @GET("/anime")
        Call<ResponseModel> getAnimes(@Query("page") int page, @Query("size") int size);

        @GET("/genre")
        Call<List<Genre>> getGenre();




    }
}

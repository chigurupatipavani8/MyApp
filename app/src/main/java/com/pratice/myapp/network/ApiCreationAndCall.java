package com.pratice.myapp.network;

import com.pratice.myapp.model.Anime;
import com.pratice.myapp.model.Genre;
import com.pratice.myapp.model.ResponseModel;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiCreationAndCall {
    public static ApiCreationAndCall instance;
    private IMyApi api;
    private ApiCreationAndCall(){



        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .get()
                        .addHeader("X-RapidAPI-Key", "13f9a66f85mshfee01a15889ebe8p1d0bc6jsne6e3a37b9c3c")
                        .addHeader("X-RapidAPI-Host", "anime-db.p.rapidapi.com")
                        .build();
                return chain.proceed(request);
            }
        });
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient  okHttp=httpClient.addInterceptor(logging).build();

        Retrofit ret=new Retrofit.Builder().baseUrl("https://anime-db.p.rapidapi.com/anime/").addConverterFactory(GsonConverterFactory.create()).client(okHttp).build();
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
        public static final String baseUrl="https://anime-db.p.rapidapi.com/anime/";

        @GET("/anime")
        public Call<ResponseModel> getAnimes(@Query("page") int page, @Query("size") int size);
//        @GET("/anime")
//        public Call<ResponseModel> getAnimes(@Query("page") int page, @Query("size") int size,@Query("search") String search,@Query("genre") String genre,@Query("sortBy") String sortBy,@Query("sortOrder") String sortOrder);

        @GET("/genre")
        public Call<List<Genre>> getGenre();

//        @GET("/character/{id}")
//        public Call<SingleModel> getData(@Path("id") int id);
//        @GET("/character")
//        public Call<CompleteDataModel> getData();
//
//        @GET("/posts/{id}/comments")
//        public Call<List<Comments>> getComments(@Path("id") int postId);
//
//        @POST("/posts")
//        public Call<User> postData(@Body User u);
//
//        @PATCH("/posts/{id}")
//        public Call<User> patchData(@Path("id") int id,@Body User u,@Query("userId") Integer id);
//
//        @PUT("/posts/{id}")
//        public Call<User> putData(@Path("id") int id,@Body User u);
//
//        @DELETE("/posts/{id}")
//        public Call<User> deleteData(@Path("id") int id);


    }
}

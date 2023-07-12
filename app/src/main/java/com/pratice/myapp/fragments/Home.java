package com.pratice.myapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pratice.myapp.LoginActivity;
import com.pratice.myapp.MainActivity;
import com.pratice.myapp.R;
import com.pratice.myapp.adapter.HomeRecyclerViewAdapter;
import com.pratice.myapp.model.Anime;
import com.pratice.myapp.model.Favorite;
import com.pratice.myapp.model.Genre;
import com.pratice.myapp.model.User;
import com.pratice.myapp.network.CheckNetworkConnectivity;
import com.pratice.myapp.viewmodel.MyViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class Home extends Fragment implements CheckNetworkConnectivity.NetworkConnection{
    static int count;
    List<Genre> strs;
    List<Anime> animes;
    RecyclerView homeRecyclerView;
    HashMap<String,List<Anime>> genreAnime;
    HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    ProgressBar progressBar;
    MyViewModel viewModelStorage;
    TextView no_internet;

    CheckNetworkConnectivity checkNetworkConnectivity;
    CheckNetworkConnectivity.NetworkCallbackClass networkCallbackClass;
    User u;
    Gson gson;
    List<Favorite> fav_list;
    HashSet<String> fav_id_set;
    HashSet<String> fav_set;
    User user;
    Parcelable recylerViewState;
    Dialog dialog;
    LinearLayout linearLayout;
    ImageView imageView;
//    public Home(){
//
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelStorage=new ViewModelProvider(this).get(MyViewModel.class);
        checkNetworkConnectivity=new CheckNetworkConnectivity();
        dialog = new Dialog(getContext());
        networkCallbackClass=checkNetworkConnectivity.getInstance(this);
//        checkNetworkConnectivity.registerNetwork(getContext());
//        checkLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        strs=new ArrayList<>();
        genreAnime=new HashMap<>();
        progressBar=view.findViewById(R.id.progress);
        linearLayout=view.findViewById(R.id.recyclerView_linear_layout);
        imageView=view.findViewById(R.id.no_data);
        homeRecyclerView=view.findViewById(R.id.data_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        homeRecyclerView.setLayoutManager(linearLayoutManager);
        no_internet=view.findViewById(R.id.no_internet);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        checkNetworkConnectivity.registerNetwork(getContext());
        super.onResume();
        if(!isOnline()){
            onDisconnect();
            checkNetworkConnectivity.unRegisterNetwork(getContext());
        }
        if(recylerViewState!=null)
            homeRecyclerView.getLayoutManager().onRestoreInstanceState(recylerViewState);
//        checkOnline();
        if(!(genreAnime==null || animes==null || genreAnime.size()==0 || animes.size()==0)) {
            homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), genreAnime, strs,viewModelStorage);
            homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
        }
    }
    public void load(){
        if(count%3==0){
            viewModelStorage.getGenre().observe(getActivity(),(result) -> {
                strs=result;
                for(Genre g:strs){
                    genreAnime.put(g.get_id(),new ArrayList<>());
                }
                System.out.println(strs);
            });

            viewModelStorage.getAnime().observe(getActivity(),(result) -> {
                animes=result;
                for(Anime a: animes){
                    for(String ge:a.getGenres()){
                        List<Anime> pastList= null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            pastList = genreAnime.getOrDefault(ge,new ArrayList<>());
                        }
                        pastList.add(a);
                        genreAnime.put(ge,pastList);
                    }
                }
            });
        }
        count++;
    }

    public void checkLoad(){
        if(!(genreAnime==null || animes==null || genreAnime.size()==0 || animes.size()==0)) {
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            homeRecyclerView.setVisibility(View.VISIBLE);
            homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), genreAnime, strs,viewModelStorage);
            homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
            no_internet.setVisibility(View.GONE);

        }
        else{
            load();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkLoad();
                }
            },3000);
        }

    }

    @Override
    public void onPause(){
        checkNetworkConnectivity.unRegisterNetwork(getContext());

        super.onPause();
        recylerViewState = homeRecyclerView.getLayoutManager().onSaveInstanceState();

    }
    @Override
    public void onStop() {
        super.onStop();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        checkNetworkConnectivity.unRegisterNetwork(getContext());
    }
    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void setNo_internetText(){
        no_internet.setText("No Internet");
        no_internet.setVisibility(View.VISIBLE);
    }
    private void removeNo_internetText(){
        no_internet.setVisibility(View.GONE);
    }

    @Override
    public void onDisconnect() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(genreAnime==null || animes==null || genreAnime.size()==0 || animes.size()==0){
                    progressBar.setVisibility(View.GONE);
                    homeRecyclerView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }
                setNo_internetText();
                dialog.setContentView(R.layout.no_connection_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                ImageView image=dialog.findViewById(R.id.image);
                Button cancel=dialog.findViewById(R.id.cancel);
                image.setImageResource(R.drawable.ic_action_no_connection);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Better luck next time", Toast.LENGTH_SHORT).show();
                    }
                });
                if(getActivity()!=null)
                    dialog.show();

            }
        },2000);


    }

    @Override
    public void onConnect() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                removeNo_internetText();
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                dialog.dismiss();
                checkLoad();
            }});
    }

}
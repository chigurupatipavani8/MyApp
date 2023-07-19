package com.pratice.myapp.fragments;


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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;



import com.pratice.myapp.R;
import com.pratice.myapp.adapter.HomeRecyclerViewAdapter;
import com.pratice.myapp.model.Anime;
import com.pratice.myapp.model.Genre;
import com.pratice.myapp.network.CheckNetworkConnectivity;
import com.pratice.myapp.viewmodel.MyViewModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


public class Home extends Fragment implements CheckNetworkConnectivity.NetworkConnection{
    List<Genre> strs;
    List<Anime> animes;
    RecyclerView homeRecyclerView;
    TreeMap<String,List<Anime>> genreAnime;
    HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    ProgressBar progressBar;
    MyViewModel viewModelStorage;
    TextView noInternet;

    public static CheckNetworkConnectivity checkNetworkConnectivity;
    CheckNetworkConnectivity.NetworkCallbackClass networkCallbackClass;
    Parcelable recylerViewState;
//    Dialog dialog;
    LinearLayout linearLayout;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelStorage=new ViewModelProvider(this).get(MyViewModel.class);
        checkNetworkConnectivity=new CheckNetworkConnectivity();
//        dialog = new Dialog(getContext());
        networkCallbackClass=checkNetworkConnectivity.getInstance(this);
        checkNetworkConnectivity.registerNetwork(getContext());
        viewModelStorage.intilazeGenreData();
        load();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        strs=new ArrayList<>();
        genreAnime=new TreeMap<>();
        progressBar=view.findViewById(R.id.progress);
        linearLayout=view.findViewById(R.id.recyclerView_linear_layout);
        imageView=view.findViewById(R.id.no_data);
        homeRecyclerView=view.findViewById(R.id.data_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        homeRecyclerView.setLayoutManager(linearLayoutManager);
        noInternet=view.findViewById(R.id.no_internet);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(recylerViewState!=null)
            homeRecyclerView.getLayoutManager().onRestoreInstanceState(recylerViewState);
        if(genreAnime!=null && genreAnime.size()!=0 &&  strs!=null && strs.size()!=0) {
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            homeRecyclerView.setVisibility(View.VISIBLE);
            homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), genreAnime, new ArrayList(Arrays.asList(genreAnime.keySet().toArray())),viewModelStorage);
            homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
        }
    }
    public void load(){
            if( getActivity()!=null && !getActivity().isFinishing()) {
                viewModelStorage.getGenre().observe(getActivity(), (result) -> {
                    strs = result;
                    for (Genre g : strs) {
                        genreAnime.put(g.getId(), new ArrayList<>());
                    }
                    System.out.println(strs);
                    if(genreAnime!=null && genreAnime.size()!=0 &&  animes!=null && animes.size()!=0) {
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        homeRecyclerView.setVisibility(View.VISIBLE);
                        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), genreAnime, new ArrayList(Arrays.asList(genreAnime.keySet().toArray())), viewModelStorage);
                        homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
                        noInternet.setVisibility(View.GONE);
                    }
                });

                viewModelStorage.getAnime().observe(getActivity(), (result) -> {
                    animes = result;
                    for (Anime a : animes) {
                        for (String ge : a.getGenres()) {
                            List<Anime> pastList = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                pastList = genreAnime.getOrDefault(ge, new ArrayList<>());
                            }
                            pastList.add(a);
                            genreAnime.put(ge, pastList);
                        }
                    }
                    if(genreAnime!=null && genreAnime.size()!=0 &&  animes!=null && animes.size()!=0) {
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        homeRecyclerView.setVisibility(View.VISIBLE);
                        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), genreAnime, new ArrayList(Arrays.asList(genreAnime.keySet().toArray())), viewModelStorage);
                        homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
                        noInternet.setVisibility(View.GONE);
                    }
                });
            }
    }

//    public void checkLoad(){
//        if(genreAnime!=null && animes!=null && genreAnime.size()!=0 && animes.size()!=0) {
//            progressBar.setVisibility(View.GONE);
//            imageView.setVisibility(View.GONE);
//            homeRecyclerView.setVisibility(View.VISIBLE);
//            homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), genreAnime, strs,viewModelStorage);
//            homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
//            no_internet.setVisibility(View.GONE);
//
//        }
//        else{
//            load();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    checkLoad();
//                }
//            },5000);
//        }
//
//    }

    @Override
    public void onPause(){
        super.onPause();
        recylerViewState = homeRecyclerView.getLayoutManager().onSaveInstanceState();

    }
    @Override
    public void onDestroy() {
        checkNetworkConnectivity.unRegisterNetwork(getContext());
        checkNetworkConnectivity.invalidateNetworkCallback();
        super.onDestroy();

    }

//    public boolean isOnline() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
//    }

    private void setNoInternetText(){
        noInternet.setText("No Internet");
        noInternet.setVisibility(View.VISIBLE);
    }
    private void removeNoInternetText(){
        noInternet.setVisibility(View.GONE);
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
                setNoInternetText();
//                dialog.setContentView(R.layout.no_connection_layout);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.setCancelable(false);
//                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
//                ImageView image=dialog.findViewById(R.id.image);
//                Button cancel=dialog.findViewById(R.id.cancel);
//                image.setImageResource(R.drawable.ic_action_no_connection);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        Toast.makeText(getContext(), "Better luck next time", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                if(getActivity()!=null)
//                    dialog.show();

            }
        },500);


    }

    @Override
    public void onConnect() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                removeNoInternetText();
                progressBar.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
//                if(dialog.isShowing())
//                    dialog.dismiss();
                load();
            }});
    }

}
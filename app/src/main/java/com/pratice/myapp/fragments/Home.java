package com.pratice.myapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pratice.myapp.MainActivity;
import com.pratice.myapp.R;
import com.pratice.myapp.adapter.HomeRecyclerViewAdapter;
import com.pratice.myapp.model.Anime;
import com.pratice.myapp.model.Genre;
import com.pratice.myapp.viewmodel.MyViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Home extends Fragment {
    List<Genre> strs;
    List<Anime> animes;
    RecyclerView homeRecyclerView;
    HashMap<String,List<Anime>> genreAnime;
    HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    ProgressBar progressBar;
    Context context;
    public Home(Context context){
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyViewModel viewModelStorage=new ViewModelProvider(this).get(MyViewModel.class);
        viewModelStorage.getGenre().observe(getActivity(),(result) -> {
            strs=result;
            for(Genre g:strs){
                genreAnime.put(g.get_id(),new ArrayList<>());
            }
            System.out.println(strs);
        });

        viewModelStorage.getAnime(1,500).observe(getActivity(),(result) -> {
            animes=result;
            for(Anime a: animes){
                for(String ge:a.getGenres()){
                    List<Anime> pastList=genreAnime.get(ge);
                    pastList.add(a);
                    genreAnime.put(ge,pastList);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        strs=new ArrayList<>();
        genreAnime=new HashMap<>();
        progressBar=view.findViewById(R.id.progress);
        homeRecyclerView=view.findViewById(R.id.data_recyclerView);
        checkLoad();
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkOnline();
        if(!(genreAnime.size()==0 || animes.size()==0)) {
            homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), genreAnime, strs);
            homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
        }

    }
    public void checkLoad(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!(genreAnime.size()==0 || animes.size()==0)) {
                    progressBar.setVisibility(View.GONE);
                    homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(context, genreAnime, strs);
                    homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    homeRecyclerView.setLayoutManager(linearLayoutManager);
                }
                else{
                    checkLoad();
                }
            }
        },3000);
    }
    public void checkOnline(){
        Dialog dialog = new Dialog(getContext());
        if(!isOnline()){
            dialog.setContentView(R.layout.no_connection_layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
            ImageView image=dialog.findViewById(R.id.image);
            Button retry=dialog.findViewById(R.id.retry);
            Button cancel=dialog.findViewById(R.id.cancel);
            image.setImageResource(R.drawable.ic_action_no_connection);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Toast.makeText(context, "Better luck next time", Toast.LENGTH_SHORT).show();
                }
            });
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isOnline()){
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
        }
    }
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
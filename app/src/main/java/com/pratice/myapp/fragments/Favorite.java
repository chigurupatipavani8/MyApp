package com.pratice.myapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.pratice.myapp.R;
import com.pratice.myapp.adapter.FavoriteListAdapter;
import com.pratice.myapp.model.Anime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;


public class Favorite extends Fragment {

    SharedPreferences fav_sharedPreferences;
    HashSet<String> fav_set;
    List<Anime> fav_animes;
    Gson gson;
    RecyclerView recyclerView;
    Context context;
    public Favorite(Context context){
        fav_animes=new ArrayList<>();
        fav_set=new HashSet<>();
        this.gson=new Gson();
        fav_sharedPreferences= context.getSharedPreferences("fav_list", Context.MODE_PRIVATE);
        fav_set= (HashSet<String>) fav_sharedPreferences.getStringSet("fav",fav_set);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView=view.findViewById(R.id.favorite_list);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        fav_set= (HashSet<String>) fav_sharedPreferences.getStringSet("fav",fav_set);
        Iterator it=fav_set.iterator();
        fav_animes=new ArrayList<>();
        while (it.hasNext()){
            fav_animes.add(gson.fromJson((String) it.next(),Anime.class));
        }
        FavoriteListAdapter favoriteListAdapter=new FavoriteListAdapter(getContext(),fav_animes);
        recyclerView.setAdapter(favoriteListAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
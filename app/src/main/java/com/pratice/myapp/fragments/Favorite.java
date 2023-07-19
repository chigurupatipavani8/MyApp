package com.pratice.myapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.pratice.myapp.R;
import com.pratice.myapp.adapter.FavoriteListAdapter;
import com.pratice.myapp.model.Anime;
import com.pratice.myapp.model.User;
import com.pratice.myapp.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;


public class Favorite extends Fragment {

    int userId;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    MyViewModel viewModelStorage;
    List<com.pratice.myapp.model.Favorite> favorites;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favorites=new ArrayList<>();
        sharedPreferences= getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
        userId=sharedPreferences.getInt("user",-1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView=view.findViewById(R.id.favorite_list);
        viewModelStorage=new ViewModelProvider(this).get(MyViewModel.class);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        favorites=viewModelStorage.getAllFav(userId).getValue();
        FavoriteListAdapter favoriteListAdapter=new FavoriteListAdapter(getContext(),favorites,viewModelStorage);
        recyclerView.setAdapter(favoriteListAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
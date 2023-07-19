package com.pratice.myapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pratice.myapp.R;
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
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.GenreViewHolder>{

    TreeMap<String,List<Anime>>  genres;
    Context context;
    List<String> genre;
    private RecyclerView.RecycledViewPool viewPool;
    MyViewModel myViewModel;



    public HomeRecyclerViewAdapter(Context context, TreeMap<String,List<Anime>> genres, List<String> genre, MyViewModel myViewModel) {
        this.context=context;
        this.genres=genres;
        this.genre=genre;
        this.myViewModel=myViewModel;
        viewPool = new RecyclerView.RecycledViewPool();

    }
    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genres_layout, parent,false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.title.setText(genre.get(position));
        MovieRecyclerAdpter childAdpter=new MovieRecyclerAdpter(genres.get(genre.get(position)),context,myViewModel);
        holder.childRecyclerView.setAdapter(childAdpter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(holder.childRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        holder.childRecyclerView.setLayoutManager(linearLayoutManager);
//        holder.childRecyclerView.setRecycledViewPool(viewPool);
    }


    @Override
    public int getItemCount() {
        return genres.size();
    }
    class GenreViewHolder extends RecyclerView.ViewHolder{
       TextView title;
       RecyclerView childRecyclerView;
        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            childRecyclerView=itemView.findViewById(R.id.items);
        }
    }
}

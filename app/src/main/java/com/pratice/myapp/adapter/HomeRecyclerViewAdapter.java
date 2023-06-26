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

import com.pratice.myapp.R;
import com.pratice.myapp.model.Anime;
import com.pratice.myapp.model.Genre;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.GenreViewHolder>{

    HashMap<String,List<Anime>>  genres;
    Context context;
    List<Genre> genre;
    private RecyclerView.RecycledViewPool viewPool;
    SharedPreferences fav_sharedPreferences;
    SharedPreferences.Editor editor;
    HashSet<String> fav_set;
    HashSet<String> fav_id_set;
    public HomeRecyclerViewAdapter(Context context, HashMap<String,List<Anime>> genres, List<Genre> genre) {
        this.context=context;
        this.genres=genres;
        this.genre=genre;
        viewPool = new RecyclerView.RecycledViewPool();
        fav_set=new HashSet<>();
        fav_id_set=new HashSet<>();
        fav_sharedPreferences= context.getSharedPreferences("fav_list",Context.MODE_PRIVATE);
        this.fav_set= (HashSet<String>) fav_sharedPreferences.getStringSet("fav",fav_set);
        this.fav_id_set=(HashSet<String>)fav_sharedPreferences.getStringSet("fav_id",fav_id_set);

    }
    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genres_layout, parent,false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.title.setText(genre.get(position).get_id());
        MovieRecyclerAdpter childAdpter=new MovieRecyclerAdpter(genres.get(genre.get(position).get_id()),context,this.fav_id_set,this.fav_set);
        holder.childRecyclerView.setAdapter(childAdpter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(holder.childRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        holder.childRecyclerView.setLayoutManager(linearLayoutManager);
        holder.childRecyclerView.setRecycledViewPool(viewPool);
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

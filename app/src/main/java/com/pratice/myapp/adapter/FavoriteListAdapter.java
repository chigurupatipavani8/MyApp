package com.pratice.myapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pratice.myapp.R;
import com.pratice.myapp.model.Anime;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavViewHolder>{
    Context context;
    List<Anime> fav_list;
    SharedPreferences fav_sharedPreferences;
    SharedPreferences.Editor editor;
    HashSet<String> fav_set;
    HashSet<String> fav_id;
    Gson gson;

    public FavoriteListAdapter(Context context, List<Anime> fav_list){
        this.context=context;
        this.fav_list=fav_list;
        fav_set=new HashSet<>();
        fav_id=new HashSet<>();
        this.gson=new Gson();
        fav_sharedPreferences= context.getSharedPreferences("fav_list",Context.MODE_PRIVATE);
        editor=fav_sharedPreferences.edit();
        this.fav_set= (HashSet<String>) fav_sharedPreferences.getStringSet("fav",fav_set);
        this.fav_id=(HashSet<String>) fav_sharedPreferences.getStringSet("fav_id",fav_id);
    }
    @NonNull
    @Override
    public FavoriteListAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_view, parent,false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListAdapter.FavViewHolder holder, int position) {
        holder.title.setText(fav_list.get(position).getTitle());
        Glide.with(context)
                .load(fav_list.get(position).getThumb())
                .placeholder(R.drawable.brandimagefour)
                .error(R.drawable.sad)
                .centerCrop()
                .into(holder.image);
        holder.my_bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav_set= (HashSet<String>) fav_sharedPreferences.getStringSet("fav",fav_set);
                fav_id=(HashSet<String>) fav_sharedPreferences.getStringSet("fav_id",fav_id);
                String fav=gson.toJson(fav_list.get(holder.getAdapterPosition()));
                fav_set.remove(fav);
                fav_id.remove(fav_list.get(holder.getAdapterPosition()).get_id());
                fav_list.remove(holder.getAdapterPosition());
                editor.remove("fav");
                editor.putStringSet("fav_id",fav_id);
                editor.putStringSet("fav",fav_set);
                editor.commit();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fav_list.size();
    }
    class FavViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        ImageButton my_bin;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            my_bin=itemView.findViewById(R.id.my_bin);
        }
    }
}

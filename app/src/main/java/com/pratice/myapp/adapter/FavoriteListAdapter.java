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
import com.pratice.myapp.model.Favorite;
import com.pratice.myapp.viewmodel.MyViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavViewHolder>{
    Context context;
    List<Favorite> favList;

    HashSet<String> favSet;
    HashSet<String> favId;
    MyViewModel myViewModel;

    public FavoriteListAdapter(Context context, List<Favorite> favList, MyViewModel myViewModel){
        this.context=context;
        this.favList=favList;
        this.myViewModel=myViewModel;
        favSet=new HashSet<>();
        favId=new HashSet<>();

    }
    @NonNull
    @Override
    public FavoriteListAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_view, parent,false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListAdapter.FavViewHolder holder, int position) {
        holder.title.setText(favList.get(position).getTitle());
        Glide.with(context)
                .load(favList.get(position).getThumb())
                .placeholder(R.drawable.brandimagefour)
                .error(R.drawable.ic_action_error_image1)
                .centerCrop()
                .into(holder.image);
        holder.my_bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                myViewModel.deleteFav(favList.get(holder.getAdapterPosition()));
                favList.remove(holder.getAdapterPosition());

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favList.size();
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

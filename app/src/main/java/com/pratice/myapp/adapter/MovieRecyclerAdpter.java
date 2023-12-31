package com.pratice.myapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pratice.myapp.Item;
import com.pratice.myapp.R;
import com.pratice.myapp.model.Anime;
import com.pratice.myapp.model.Favorite;
import com.pratice.myapp.model.User;
import com.pratice.myapp.viewmodel.MyViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class MovieRecyclerAdpter extends RecyclerView.Adapter<MovieRecyclerAdpter.MovieViewHolder>{

    List<Anime> animes;
    Context context;
    SharedPreferences user_sharedPreferences;
    Gson gson;

    User u;
    String userString;
    MyViewModel myViewModel;
    List<Favorite> favorites;
    List<String> favorites_ids;


    public MovieRecyclerAdpter(List<Anime> animes, Context context,MyViewModel myViewModel) {
        this.animes = animes;
        this.context = context;
        this.myViewModel=myViewModel;
        gson=new Gson();
        user_sharedPreferences= context.getSharedPreferences("login",Context.MODE_PRIVATE);
        userString=user_sharedPreferences.getString("user","");
        u=gson.fromJson(userString,User.class);
        this.favorites=myViewModel.getAllFav(u.getUser_id()).getValue();
        this.favorites_ids=myViewModel.getAllFav_ids(u.getUser_id()).getValue();
    }

    @NonNull
    @Override
    public MovieRecyclerAdpter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Drawable drawable;
        Picasso.with(context)
                .load(animes.get(position).getImage())
                .placeholder(R.drawable.brandimagefour)
                .error(R.drawable.ic_action_error_image1)
                .centerCrop()
                .fit()
                .into(holder.imageView);
        String title=(animes.get(position).getTitle().length()<10)?animes.get(position).getTitle():animes.get(position).getTitle().substring(0,10);
        holder.title.setText(title);

        if(favorites_ids.contains(animes.get(holder.getAdapterPosition()).get_id())){
            drawable=holder.like.getCompoundDrawables()[0]!=null?holder.like.getCompoundDrawables()[0]:context.getDrawable(R.drawable.heart);
            drawable.setTint(context.getResources().getColor(R.color.red));
            holder.like.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        }
        else {
            drawable=holder.like.getCompoundDrawables()[0]!=null?holder.like.getCompoundDrawables()[0]:context.getDrawable(R.drawable.heart);
            drawable.setTint(context.getResources().getColor(R.color.black));
            holder.like.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        }
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_sharedPreferences= context.getSharedPreferences("login",Context.MODE_PRIVATE);
                userString=user_sharedPreferences.getString("user","");
                u=gson.fromJson(userString,User.class);
                Anime anime=animes.get(holder.getAdapterPosition());
                Favorite favorite=new Favorite(u.getUser_id(),anime.get_id(),anime.getImage(),anime.getTitle(),anime.getThumb());
                favorites_ids=myViewModel.getAllFav_ids(u.getUser_id()).getValue();

                if(!favorites_ids.contains(anime.get_id())){
                    myViewModel.addFav(favorite);
                    favorites.add(favorite);
                    favorites_ids.add(anime.get_id());
                    Toast.makeText(context, "you liked "+animes.get(holder.getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
                }
                else{
                    myViewModel.deleteFav(favorite);
                    favorites.remove(favorite);
                    favorites_ids.remove(anime.get_id());
                    Toast.makeText(context, "you unliked "+animes.get(holder.getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
                }
                favorites=myViewModel.getAllFav(u.getUser_id()).getValue();
                favorites_ids=myViewModel.getAllFav_ids(u.getUser_id()).getValue();
//                Iterator itr = fav_set.iterator();
//                Iterator it_id=fav_id_set.iterator();
                for(Favorite fav:favorites) {
                    System.out.println(fav);
                }
                System.out.println(favorites.size());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });



//        Dialog dialog = new Dialog(context);
//        holder.card .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.setContentView(R.layout.anime_layout);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////                dialog.setCancelable(false);
//                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
//                ImageView image=dialog.findViewById(R.id.image);
//                TextView title=dialog.findViewById(R.id.title);
//                TextView ranking=dialog.findViewById(R.id.ranking);
//                TextView episodes=dialog.findViewById(R.id.episodes);
//                TextView synopsis=dialog.findViewById(R.id.synopsis);
//                Button close=dialog.findViewById(R.id.close);
//                Picasso.with(context)
//                        .load(animes.get(holder.getAdapterPosition()).getImage())
//                        .placeholder(R.drawable.brandimagefour)
//                        .error(R.drawable.sad)
//                        .centerCrop()
//                        .fit()
//                        .into(image);
//                title.setText("Title: "+animes.get(holder.getAdapterPosition()).getTitle());
//                ranking.setText("Ranking: "+animes.get(holder.getAdapterPosition()).getRanking());
//                episodes.setText("Episodes: "+animes.get(holder.getAdapterPosition()).getEpisodes());
//                synopsis.setText(animes.get(holder.getAdapterPosition()).getSynopsis());
//                close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        Toast.makeText(context, "closed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                dialog.show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return animes.size();
    }
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       ImageView imageView;
       TextView title;
       TextView like;
       CardView card;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
            like = itemView.findViewById(R.id.like);
            card = itemView.findViewById(R.id.card_item);
            card.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, Item.class);
            intent.putExtra("data",animes.get(getAdapterPosition()));
            context.startActivity(intent);
        }

    }
}

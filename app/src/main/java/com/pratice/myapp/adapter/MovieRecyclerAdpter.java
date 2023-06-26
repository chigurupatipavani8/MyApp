package com.pratice.myapp.adapter;

import android.app.Dialog;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pratice.myapp.R;
import com.pratice.myapp.model.Anime;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class MovieRecyclerAdpter extends RecyclerView.Adapter<MovieRecyclerAdpter.MovieViewHolder>{

    List<Anime> animes;
    Context context;
    SharedPreferences fav_sharedPreferences;
    SharedPreferences.Editor editor;

    Gson gson;
    HashSet<String> fav_set;
    HashSet<String> fav_id_set;


    public MovieRecyclerAdpter(List<Anime> animes, Context context,HashSet<String> fav_id_set,HashSet<String> fav_set) {
        this.animes = animes;
        this.context = context;
        this.gson=new Gson();
        fav_sharedPreferences= context.getSharedPreferences("fav_list",Context.MODE_PRIVATE);
        editor=fav_sharedPreferences.edit();
        this.fav_set= (HashSet<String>) fav_sharedPreferences.getStringSet("fav",fav_set);
        this.fav_id_set=(HashSet<String>)fav_sharedPreferences.getStringSet("fav_id",fav_id_set);
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
                .error(R.drawable.sad)
                .centerCrop()
                .fit()
                .into(holder.imageView);
        String title=(animes.get(position).getTitle().length()<10)?animes.get(position).getTitle():animes.get(position).getTitle().substring(0,10);
        holder.title.setText(title);
        fav_set= (HashSet<String>) fav_sharedPreferences.getStringSet("fav",fav_set);
        fav_id_set=(HashSet<String>)fav_sharedPreferences.getStringSet("fav_id",fav_id_set);

        if(this.fav_id_set.contains(animes.get(holder.getAdapterPosition()).get_id())){
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
                String fav=gson.toJson(animes.get(holder.getAdapterPosition()));
                fav_id_set=(HashSet<String>)fav_sharedPreferences.getStringSet("fav_id",fav_id_set);
                fav_set= (HashSet<String>) fav_sharedPreferences.getStringSet("fav",fav_set);
                Drawable drawable;
                if(fav_id_set.add(animes.get(holder.getAdapterPosition()).get_id())){
                    fav_set.add(fav);
                    notifyItemChanged(holder.getAdapterPosition());
                    Toast.makeText(context, "you liked "+holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else{

                    fav_set.remove(fav);
                    fav_id_set.remove(animes.get(holder.getAdapterPosition()).get_id());
                    notifyItemChanged(holder.getAdapterPosition());
                    Toast.makeText(context, "you unliked "+holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                System.out.println(fav_set);
                Iterator itr = fav_set.iterator();
                Iterator it_id=fav_id_set.iterator();
                while (itr.hasNext()) {
                    System.out.println(itr.next());
                }
                while (it_id.hasNext()) {
                    System.out.println(it_id.next());
                }
                editor.putStringSet("fav",fav_set);
                editor.putStringSet("fav_id",fav_id_set);
                editor.commit();

            }
        });

        Dialog dialog = new Dialog(context);
        holder.card .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.anime_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                ImageView image=dialog.findViewById(R.id.image);
                TextView title=dialog.findViewById(R.id.title);
                TextView ranking=dialog.findViewById(R.id.ranking);
                TextView episodes=dialog.findViewById(R.id.episodes);
                TextView synopsis=dialog.findViewById(R.id.synopsis);
                Button close=dialog.findViewById(R.id.close);
                Picasso.with(context)
                        .load(animes.get(holder.getAdapterPosition()).getImage())
                        .placeholder(R.drawable.brandimagefour)
                        .error(R.drawable.sad)
                        .centerCrop()
                        .fit()
                        .into(image);
                title.setText("Title: "+animes.get(holder.getAdapterPosition()).getTitle());
                ranking.setText("Ranking: "+animes.get(holder.getAdapterPosition()).getRanking());
                episodes.setText("Episodes: "+animes.get(holder.getAdapterPosition()).getEpisodes());
                synopsis.setText(animes.get(holder.getAdapterPosition()).getSynopsis());
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(context, "closed", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return animes.size();
    }
    class MovieViewHolder extends RecyclerView.ViewHolder{
       ImageView imageView;
       TextView title;
       TextView like;
       CardView card;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            imageView=itemView.findViewById(R.id.image);
            like=itemView.findViewById(R.id.like);
            card=itemView.findViewById(R.id.card_item);

        }
    }
}

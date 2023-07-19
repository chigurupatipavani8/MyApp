package com.pratice.myapp.fragments;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pratice.myapp.R;
import com.pratice.myapp.model.Anime;
import com.squareup.picasso.Picasso;


public class Item extends Fragment {

    ImageView image;
    TextView title;
    TextView ranking;
    TextView episodes;
    TextView synopsis;
    Button close;
    Anime anime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back_button);
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_item, container, false);
        image=view.findViewById(R.id.image);
        title=view.findViewById(R.id.title);
        ranking=view.findViewById(R.id.ranking);
        episodes=view.findViewById(R.id.episodes);
        synopsis=view.findViewById(R.id.synopsis);
        close=view.findViewById(R.id.close);
//        anime= (Anime) getIntent().getSerializableExtra("data");
        Glide.with(this)
                .load(anime.getImage())
                .placeholder(R.drawable.brandimagefour)
                .error(R.drawable.ic_action_error_image1)
                .centerCrop()
                .into(image);
        title.setText("Title: "+anime.getTitle());
        ranking.setText("Ranking: "+anime.getRanking());
        episodes.setText("Episodes: "+anime.getEpisodes());
        synopsis.setText(anime.getSynopsis());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(Item.this).commit();
                Toast.makeText(getActivity(), "closed", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
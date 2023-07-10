package com.pratice.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pratice.myapp.model.Anime;
import com.squareup.picasso.Picasso;

public class Item extends AppCompatActivity {
    ImageView image;
    TextView title;
    TextView ranking;
    TextView episodes;
    TextView synopsis;
    Button close;
    Anime anime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back_button);
        actionBar.setDisplayHomeAsUpEnabled(true);

        image=findViewById(R.id.image);
        title=findViewById(R.id.title);
        ranking=findViewById(R.id.ranking);
        episodes=findViewById(R.id.episodes);
        synopsis=findViewById(R.id.synopsis);
        close=findViewById(R.id.close);
        anime= (Anime) getIntent().getSerializableExtra("data");
        Picasso.with(this)
                        .load(anime.getImage())
                        .placeholder(R.drawable.brandimagefour)
                        .error(R.drawable.sad)
                        .centerCrop()
                        .fit()
                        .into(image);
                title.setText("Title: "+anime.getTitle());
                ranking.setText("Ranking: "+anime.getRanking());
                episodes.setText("Episodes: "+anime.getEpisodes());
                synopsis.setText(anime.getSynopsis());
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        Toast.makeText(Item.this, "closed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
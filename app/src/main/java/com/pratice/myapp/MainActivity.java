package com.pratice.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.pratice.myapp.adapter.MainActivityViewPagerAdapter;
import com.pratice.myapp.fragments.About;
import com.pratice.myapp.fragments.Favorite;
import com.pratice.myapp.fragments.Home;
import com.pratice.myapp.model.Anime;
import com.pratice.myapp.model.Genre;
import com.pratice.myapp.model.User;
import com.pratice.myapp.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    Gson gson;
    TabLayout tabLayout;
    MainActivityViewPagerAdapter mainActivityViewPagerAdapter;
    Fragment home;
    Fragment favorite;
    Fragment profile;
    List<TabClass> tabs;



    public class TabClass{
        Drawable drawableIcon;
        String title;

        public TabClass() {
        }

        public TabClass(Drawable drawableIcon, String title) {
            this.drawableIcon = drawableIcon;
            this.title = title;
        }

        public Drawable getDrawableIcon() {
            return drawableIcon;
        }

        public void setDrawableIcon(Drawable drawableIcon) {
            this.drawableIcon = drawableIcon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "TabClass{" +
                    "drawableIcon=" + drawableIcon +
                    ", title='" + title + '\'' +
                    '}';
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson=new Gson();

        mainActivityViewPagerAdapter=new MainActivityViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        tabs=new ArrayList<>();
        viewPager2=findViewById(R.id.viewPager);
        tabLayout=findViewById(R.id.tablayout);
        home=new Home();
        profile=new About();
        favorite=new Favorite();
        mainActivityViewPagerAdapter.add(home);
        mainActivityViewPagerAdapter.add(profile);
        mainActivityViewPagerAdapter.add(favorite);
        tabs.add(new TabClass(getResources().getDrawable(R.drawable.ic_action_home),"home"));
        tabs.add(new TabClass(getResources().getDrawable(R.drawable.ic_action_person),"about"));
        tabs.add(new TabClass(getResources().getDrawable(R.drawable.heart),"favorite"));
        viewPager2.setAdapter(mainActivityViewPagerAdapter);
        new TabLayoutMediator(tabLayout,viewPager2,true,(tab, position) -> {
            tab.setIcon(tabs.get(position).drawableIcon);
            tab.setText(tabs.get(position).getTitle());
        }).attach();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                signOut();
                return true;
            case R.id.home:
//                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {

        super.onPause();

    }

    private void signOut() {
        SharedPreferences user_sharedPreferences= getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=user_sharedPreferences.edit();
        editor.remove("user");
        editor.commit();
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
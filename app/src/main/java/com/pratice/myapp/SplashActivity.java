package com.pratice.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Intent login=new Intent(SplashActivity.this,LoginActivity.class);
        Intent error=new Intent(SplashActivity.this,ErrorActivity.class);
        Intent main=new Intent(SplashActivity.this, MainActivity.class);
        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(sharedPreferences.contains("user")) {
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(main);
        }
        else{
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
        }
        finish();
    }
}
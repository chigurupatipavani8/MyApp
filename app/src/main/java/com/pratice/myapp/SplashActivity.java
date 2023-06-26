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
        if(sharedPreferences.contains("name")) {
            if(isOnline()) {
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(main);
            }
            else {
                Toast.makeText(SplashActivity.this, "not connected to internet", Toast.LENGTH_SHORT).show();
                error.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                error.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                error.putExtra("error","no Internet Connection");
                startActivity(error);
            }
        }
        else{
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
        }
        finish();
    }
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
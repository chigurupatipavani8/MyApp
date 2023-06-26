package com.pratice.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ErrorActivity extends AppCompatActivity {
    TextView error_text;
    Button retry;
    Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        Intent error=new Intent(ErrorActivity.this,ErrorActivity.class);
        Intent main=new Intent(ErrorActivity.this, SplashActivity.class);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        error_text=findViewById(R.id.error);
        error_text.setText(getIntent().getStringExtra("error"));
        exit=findViewById(R.id.exit);
        retry=findViewById(R.id.retry);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()){
                    startActivity(main);
                    finish();
                }
                else{
                    error.putExtra("error","no Internet Connection");
                    error.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    error.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(error);
                    Toast.makeText(ErrorActivity.this, getIntent().getStringExtra("error"), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
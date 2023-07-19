package com.pratice.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.pratice.myapp.model.Favorite;
import com.pratice.myapp.model.User;
import com.pratice.myapp.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout emailOut;
    TextInputLayout passwordOut;
    TextInputEditText email;
    TextInputEditText password;
    MaterialButton login;
    MaterialButton signUp;
    TextView errorText;
    String patternEmail;
    String patternPassword;
    boolean submit=false;
    String invalid="Invalid ";
    MyViewModel storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        storage= new ViewModelProvider(this).get(MyViewModel.class);
        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Intent mainActivityIntent=new Intent(LoginActivity.this,MainActivity.class);
        Intent signUpActivityIntent=new Intent(LoginActivity.this,SignUp.class);
        patternEmail="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        patternPassword="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*])(?=\\S+$).{8,20}$";
        emailOut=findViewById(R.id.email_out);
        email=findViewById(R.id.email);
        passwordOut=findViewById(R.id.password_out);
        password=findViewById(R.id.password);
        login=findViewById(R.id.submit);
        signUp=findViewById(R.id.sign_up);
        errorText=findViewById(R.id.error);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signUpActivityIntent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInputField(email.getText(),emailOut,"email",patternEmail);
                validateInputField(password.getText(),passwordOut,"password",patternPassword);
                if(submit){
                    LiveData<User> u=storage.getUser(email.getText().toString());
                    if(u!=null && u.getValue()!=null && u.getValue().getPassword().equals(password.getText().toString())){
                        int userId=u.getValue().getUserId();
                        editor.putInt("user", userId);
                        editor.commit();
                        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainActivityIntent);
                        finish();
                    }
                    else{
                        if(u!=null && u.getValue()==null ){
                            errorText.setText("Email doesn't exist please SignUp");
                        }
                        else
                            errorText.setText("Worng Credientials");
                    }
                }
                else{
                    errorText.setText("please check the details entered");
                }
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputField(email.getText(),emailOut,"email",patternEmail);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputField(password.getText(),passwordOut,"password",patternPassword);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

    }
    public void validateInputField(CharSequence s,TextInputLayout layout,String message,String pattern){
        if(patternMatches(s.toString(),pattern)){
            layout.setHelperText(" ");
            layout.setBoxStrokeColor(getResources().getColor(R.color.green));
            submit=true;
        }
        else{
            if(s.toString().equals("")){
                layout.setHelperText("Empty " + message);
                layout.setBoxStrokeColor(getResources().getColor(R.color.white_gray));
                submit = false;
            }
            else {
                layout.setHelperText(invalid + message);
                layout.setBoxStrokeColor(getResources().getColor(R.color.red));
                submit = false;
            }
        }
    }
    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

}
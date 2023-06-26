package com.pratice.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    TextInputLayout name_out;
    TextInputLayout email_out;
    TextInputLayout phone_out;
    TextInputLayout password_out;
    TextInputLayout confirmPassword_out;
    TextInputLayout gender_out;

    TextInputEditText name;
    TextInputEditText email;
    TextInputEditText phone;
    TextInputEditText password;
    TextInputEditText confirmPassword;

    RadioGroup gender;
    RadioButton radio_feamle;
    RadioButton radio_male;
    RadioButton radio_no;
    MaterialButton signUp;
    MaterialButton login;
    MaterialCheckBox notification;
    TextView error;
    String patternPhone;
    String patternName;
    String patternEmail;
    String patternPassword;
    String invalid="invalid ";
    static boolean submit=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        name_out=findViewById(R.id.name_out);
        name=findViewById(R.id.name);
        email_out=findViewById(R.id.email_out);
        email=findViewById(R.id.email);
        password_out=findViewById(R.id.password_out);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirm_password);
        confirmPassword_out=findViewById(R.id.confirm_password_out);
        phone_out=findViewById(R.id.phone_out);
        phone=findViewById(R.id.phone);
        gender=findViewById(R.id.gender);
        gender_out=findViewById(R.id.gender_out);
        error=findViewById(R.id.error);
        notification=findViewById(R.id.notification);
        login=findViewById(R.id.login);
        patternPhone="^[6-9]{1}[0-9]{9}$";
        patternName="/^[a-zA-Z]+ [a-zA-Z]+$/";
        patternEmail="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        patternPassword="^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=!*])"
                + "(?=\\S+$).{8,20}$";
        signUp=findViewById(R.id.submit);
        Intent mainActivityIntent=new Intent(SignUp.this,MainActivity.class);
        Intent error=new Intent(SignUp.this,ErrorActivity.class);
        Intent loginActivityIntent=new Intent(SignUp.this,LoginActivity.class);
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputField(s,phone_out,"mobile number",patternPhone);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                validateInputField(s,name_out,"name",patternName);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputField(s,email_out,"email",patternEmail);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputField(s,password_out,"password",patternPassword);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputField(s,confirmPassword_out,"confirm password",patternPassword);
            }
            @Override
            public void afterTextChanged(Editable s) {
               check();
            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
      login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              loginActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(loginActivityIntent);
          }
      });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
//                if(submit)
//                    validateInputField(name.getText(),name_out,"name",patternName);
                if(submit)
                    validateInputField(email.getText(),email_out,"email",patternEmail);
                if(submit)
                    validateInputField(phone.getText(),phone_out,"mobile number",patternPhone);
                if(submit)
                    validateInputField(password.getText(),password_out,"password",patternPassword);
                if(submit)
                    validateInputField(confirmPassword.getText(),confirmPassword_out,"confirm password",patternPassword);

                if(submit){
                    if(isOnline()){
                        editor.putString("name",name.getText().toString());
                        editor.commit();
                        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainActivityIntent);
                    }
                    else{
                        editor.putString("name",name.getText().toString());
                        Toast.makeText(SignUp.this, "not connected to internet", Toast.LENGTH_SHORT).show();
                        error.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        error.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        error.putExtra("error","no Internet Connection");
                        startActivity(error);
                    }


                }
                else{
                    validateInputField(confirmPassword.getText(),confirmPassword_out,"confirm password",patternPassword);
                    validateInputField(password.getText(),password_out,"password",patternPassword);
                    validateInputField(phone.getText(),phone_out,"mobile number",patternPhone);
                    validateInputField(email.getText(),email_out,"email",patternEmail);
//                    validateInputField(name.getText(),name_out,"name",patternName);
                    check();
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
    public void validateInputField(CharSequence s,TextInputLayout layout,String message,String pattern){
        if(patternMatches(s.toString(),pattern)){
            layout.setHelperText(" ");
            layout.setBoxStrokeColor(getResources().getColor(R.color.green));
            submit=true;
        }
        else{
            layout.setHelperText(invalid+message);
            layout.setBoxStrokeColor(getResources().getColor(R.color.red));
            submit=false;
        }
    }
    public void check(){
        if(!confirmPassword.getText().toString().equals(password.getText().toString()) || password.getText().equals("")){
            confirmPassword_out.setHelperText("Password and Confirm password doesn't match");
            confirmPassword_out.setBoxStrokeColor(getResources().getColor(R.color.red));
            submit=false;
        }
        else{
            confirmPassword_out.setHelperText(" ");
            confirmPassword_out.setBoxStrokeColor(getResources().getColor(R.color.green));
            submit=true;
        }
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
package com.pratice.myapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pratice.myapp.R;
import com.pratice.myapp.model.User;
import com.pratice.myapp.viewmodel.MyViewModel;


public class About extends Fragment {

    SharedPreferences user_sharedPreferences;

    User u;
    int userId;
    TextView name;
    TextView email;
    TextView mobile;
    TextView gender;
    MyViewModel viewModelStorage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_sharedPreferences= getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        userId=user_sharedPreferences.getInt("user",-1);
        viewModelStorage=new ViewModelProvider(this).get(MyViewModel.class);
        u=viewModelStorage.getUserById(userId).getValue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_about, container, false);
        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        gender=view.findViewById(R.id.gender);
        mobile=view.findViewById(R.id.mobile);
        name.setText(u.getName());
        email.setText(u.getEmail());
        gender.setText(u.getGender());
        mobile.setText("******"+u.getPhone().substring(6));
        return view;
    }
}
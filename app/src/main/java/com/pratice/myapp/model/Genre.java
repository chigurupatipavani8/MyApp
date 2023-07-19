package com.pratice.myapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Genre implements Serializable
{
    @SerializedName("_id")
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Genre() {

    }

    @Override
    public String toString() {
        return "Genre{" +
                "id='" + id + '\'' +
                '}';
    }
}

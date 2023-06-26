package com.pratice.myapp.model;

import java.io.Serializable;

public class Genre implements Serializable
{
    String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Genre() {

    }

    @Override
    public String toString() {
        return "Genre{" +
                "_id='" + _id + '\'' +
                '}';
    }
}

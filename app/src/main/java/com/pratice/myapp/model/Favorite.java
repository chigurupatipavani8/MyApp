package com.pratice.myapp.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favorite",indices = {@Index(value = { "user_id","_id"}, unique = true)})
public class Favorite implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    int user_id;
    String _id;
    String image;
    String title;
    String thumb;

    public Favorite() {
    }


    public Favorite(int user_id, String _id, String image, String title, String thumb) {
        this.user_id = user_id;
        this._id = _id;
        this.image = image;
        this.title = title;
        this.thumb = thumb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", _id='" + _id + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                '}';
    }
}

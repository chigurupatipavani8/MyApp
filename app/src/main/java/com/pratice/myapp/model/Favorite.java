package com.pratice.myapp.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favorite",indices = {@Index(value = { "userId","favId"}, unique = true)})
public class Favorite implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    int userId;
    String favId;
    String image;
    String title;
    String thumb;

    public Favorite() {
    }


    public Favorite(int userId, String favId, String image, String title, String thumb) {
        this.userId = userId;
        this.favId = favId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFavId() {
        return favId;
    }

    public void setFavId(String favId) {
        this.favId = favId;
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
                ", userId=" + userId +
                ", favId='" + favId + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                '}';
    }
}

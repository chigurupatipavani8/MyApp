package com.pratice.myapp.model;

import java.io.Serializable;
import java.util.Arrays;

public class Anime implements Serializable {
    String _id;
    String title;
    String[] alternativeTitles;
    int ranking;
    String[] genres;
    int episodes;
    boolean hasEpisode;
    boolean hasRanking;
    String image;
    String link;
    String status;
    String synopsis;
    String thumb;
    String type;

    public Anime() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAlternativeTitles() {
        return alternativeTitles;
    }

    public void setAlternativeTitles(String[] alternativeTitles) {
        this.alternativeTitles = alternativeTitles;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public boolean isHasEpisode() {
        return hasEpisode;
    }

    public void setHasEpisode(boolean hasEpisode) {
        this.hasEpisode = hasEpisode;
    }

    public boolean isHasRanking() {
        return hasRanking;
    }

    public void setHasRanking(boolean hasRanking) {
        this.hasRanking = hasRanking;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", alternativeTitles=" + Arrays.toString(alternativeTitles) +
                ", ranking=" + ranking +
                ", genres=" + Arrays.toString(genres) +
                ", episodes=" + episodes +
                ", hasEpisode=" + hasEpisode +
                ", hasRanking=" + hasRanking +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                ", status='" + status + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", thumb='" + thumb + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

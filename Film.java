package com.example.benvc.films;

/**
 * Created by benvc on 2016-11-29.
 */

public class Film {
    protected int id;
    protected int rating;
    protected String title;
    protected String desc;
    protected String thumbnail;
    protected String trailer;

    public Film(){//Default Constructor
    }

    public Film(String id, String rating, String title,String desc, String thumbnail, String trailer){
        this.id = Integer.parseInt(id);
        this.rating = Integer.parseInt(rating);
        this.title = title;
        this.desc = desc;
        this.thumbnail = thumbnail;
        this.trailer = trailer;
    }
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}

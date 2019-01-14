package com.example.mobioticstest;

import com.google.gson.annotations.SerializedName;

public class UserData
{

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("thumb")
    private String thumb;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

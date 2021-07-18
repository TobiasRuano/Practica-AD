package com.trotos.adpractica.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Album implements Serializable {

    @SerializedName("userId")
    private int userId;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
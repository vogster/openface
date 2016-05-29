package com.unlogicon.openface.model.vk;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nik on 05.05.2016.
 */
public class VKCity implements Serializable {
    private static final long serialVersionUID = 2440729430703319550L;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;

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

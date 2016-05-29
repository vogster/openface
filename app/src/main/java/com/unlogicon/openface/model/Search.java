package com.unlogicon.openface.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nik on 24.04.2016.
 */
public class Search implements Serializable {
    private static final long serialVersionUID = 6713264240783399230L;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("photo_id")
    private int photo_id;
    @SerializedName("bbox")
    private List<Integer> bbox;
    @SerializedName("similarity")
    private float similarity;
    @SerializedName("search_top")
    private int search_top;
    @SerializedName("hide_from_search")
    private int hide_from_search;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public List<Integer> getBbox() {
        return bbox;
    }

    public void setBbox(List<Integer> bbox) {
        this.bbox = bbox;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(long similarity) {
        this.similarity = similarity;
    }

    public int getSearch_top() {
        return search_top;
    }

    public void setSearch_top(int search_top) {
        this.search_top = search_top;
    }

    public int getHide_from_search() {
        return hide_from_search;
    }

    public void setHide_from_search(int hide_from_search) {
        this.hide_from_search = hide_from_search;
    }
}

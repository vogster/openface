package com.unlogicon.openface.model.vk;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nik on 05.05.2016.
 */
public class VKPhoto implements Serializable {
    private static final long serialVersionUID = 3868695301140900662L;
    @SerializedName("id")
    private int id;
    @SerializedName("album_id")
    private int album_id;
    @SerializedName("owner_id")
    private int ownwer_id;
    @SerializedName("photo_75")
    private String photo_75;
    @SerializedName("photo_130")
    private String photo_130;
    @SerializedName("photo_604")
    private String photo_604;
    @SerializedName("photo_807")
    private String photo_807;
    @SerializedName("photo_1280")
    private String photo_1280;
    @SerializedName("photo_2560")
    private String photo_2560;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;
    @SerializedName("text")
    private String text;
    @SerializedName("date")
    private int date;
    @SerializedName("post_id")
    private int post_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getOwnwer_id() {
        return ownwer_id;
    }

    public void setOwnwer_id(int ownwer_id) {
        this.ownwer_id = ownwer_id;
    }

    public String getPhoto_75() {
        return photo_75;
    }

    public void setPhoto_75(String photo_75) {
        this.photo_75 = photo_75;
    }

    public String getPhoto_130() {
        return photo_130;
    }

    public void setPhoto_130(String photo_130) {
        this.photo_130 = photo_130;
    }

    public String getPhoto_604() {
        return photo_604;
    }

    public void setPhoto_604(String photo_604) {
        this.photo_604 = photo_604;
    }

    public String getPhoto_807() {
        return photo_807;
    }

    public void setPhoto_807(String photo_807) {
        this.photo_807 = photo_807;
    }

    public String getPhoto_1280() {
        return photo_1280;
    }

    public void setPhoto_1280(String photo_1280) {
        this.photo_1280 = photo_1280;
    }

    public String getPhoto_2560() {
        return photo_2560;
    }

    public void setPhoto_2560(String photo_2560) {
        this.photo_2560 = photo_2560;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

}

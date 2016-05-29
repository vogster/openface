package com.unlogicon.openface.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nik on 24.04.2016.
 */
public class History implements Serializable {
    private static final long serialVersionUID = 324104045800679350L;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("uid")
    private String uid;
    @SerializedName("detect_photo")
    private String detect_photo;
    @SerializedName("image")
    private String image;
    @SerializedName("x1")
    private String x1;
    @SerializedName("y1")
    private String y1;
    @SerializedName("x2")
    private String x2;
    @SerializedName("y2")
    private String y2;
    @SerializedName("hash")
    private String hash;
    @SerializedName("create_time")
    private String create_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDetect_photo() {
        return detect_photo;
    }

    public void setDetect_photo(String detect_photo) {
        this.detect_photo = detect_photo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getX1() {
        return x1;
    }

    public void setX1(String x1) {
        this.x1 = x1;
    }

    public String getY1() {
        return y1;
    }

    public void setY1(String y1) {
        this.y1 = y1;
    }

    public String getX2() {
        return x2;
    }

    public void setX2(String x2) {
        this.x2 = x2;
    }

    public String getY2() {
        return y2;
    }

    public void setY2(String y2) {
        this.y2 = y2;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}

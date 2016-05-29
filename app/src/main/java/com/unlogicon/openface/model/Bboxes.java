package com.unlogicon.openface.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nik on 24.04.2016.
 */
public class Bboxes implements Serializable {
    private static final long serialVersionUID = -2865007390322520860L;
    @SerializedName("x1")
    public int x1;
    @SerializedName("y1")
    public int y1;
    @SerializedName("x2")
    public int x2;
    @SerializedName("y2")
    public int y2;
    @SerializedName("hash")
    public String hash;

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getY2() {
        return y2;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}

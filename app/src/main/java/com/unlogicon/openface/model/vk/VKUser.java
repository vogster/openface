package com.unlogicon.openface.model.vk;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nik on 05.05.2016.
 */
public class VKUser implements Serializable {
    private static final long serialVersionUID = -7682456427916702355L;
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("bdate")
    private String bdate;
    @SerializedName("city")
    private VKCity city;
    @SerializedName("photo_100")
    private String photo_100;
    @SerializedName("photo_400_orig")
    private String photo_400_orig;
    @SerializedName("photo_max_orig")
    private String photo_max_orig;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public VKCity getCity() {
        return city;
    }

    public void setCity(VKCity city) {
        this.city = city;
    }

    public String getPhoto_100() {
        return photo_100;
    }

    public void setPhoto_100(String photo_100) {
        this.photo_100 = photo_100;
    }

    public String getPhoto_400_orig() {
        return photo_400_orig;
    }

    public void setPhoto_400_orig(String photo_400_orig) {
        this.photo_400_orig = photo_400_orig;
    }

    public String getPhoto_max_orig() {
        return photo_max_orig;
    }

    public void setPhoto_max_orig(String photo_max_orig) {
        this.photo_max_orig = photo_max_orig;
    }
}

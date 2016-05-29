package com.unlogicon.openface.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nik on 24.04.2016.
 */
public class Detect implements Serializable {
    private static final long serialVersionUID = -3139475342709918736L;
    @SerializedName("bboxes")
    List<Bboxes> bboxes;
    @SerializedName("image_hash")
    private String image_hash;
    @SerializedName("md5_image_hash_file")
    private String md5_image_hash_file;
    @SerializedName("detect_photo_url")
    private String detect_photo_url;
    @SerializedName("uid")
    private String uid;
    @SerializedName("user_id")
    private String user_id;

    public List<Bboxes> getBboxes() {
        return bboxes;
    }

    public void setBboxes(List<Bboxes> bboxes) {
        this.bboxes = bboxes;
    }

    public String getImage_hash() {
        return image_hash;
    }

    public void setImage_hash(String image_hash) {
        this.image_hash = image_hash;
    }

    public String getMd5_image_hash_file() {
        return md5_image_hash_file;
    }

    public void setMd5_image_hash_file(String md5_image_hash_file) {
        this.md5_image_hash_file = md5_image_hash_file;
    }

    public String getDetect_photo_url() {
        return detect_photo_url;
    }

    public void setDetect_photo_url(String detect_photo_url) {
        this.detect_photo_url = detect_photo_url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

package com.unlogicon.openface.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nik on 24.04.2016.
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -8503935637846772043L;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("account_type")
    private int account_type;
    @SerializedName("subscribe_end_date")
    private String subscribe_end_date;
    @SerializedName("search_count")
    private int search_count;
    @SerializedName("search_top")
    private int search_top;
    @SerializedName("hide_from_search")
    private int hide_from_search;
    @SerializedName("vk_open_count")
    private int vk_open_count;
    @SerializedName("email")
    private String email;
    @SerializedName("invite_hash")
    private String invite_hash;
    @SerializedName("invite_count")
    private int invite_count;
    @SerializedName("check_delete_key")
    private String check_delete_key;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAccount_type() {
        return account_type;
    }

    public void setAccount_type(int account_type) {
        this.account_type = account_type;
    }


    public String getSubscribe_end_date() {
        return subscribe_end_date;
    }

    public void setSubscribe_end_date(String subscribe_end_date) {
        this.subscribe_end_date = subscribe_end_date;
    }

    public int getSearch_count() {
        return search_count;
    }

    public void setSearch_count(int search_count) {
        this.search_count = search_count;
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

    public int getVk_open_count() {
        return vk_open_count;
    }

    public void setVk_open_count(int vk_open_count) {
        this.vk_open_count = vk_open_count;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvite_hash() {
        return invite_hash;
    }

    public void setInvite_hash(String invite_hash) {
        this.invite_hash = invite_hash;
    }

    public int getInvite_count() {
        return invite_count;
    }

    public void setInvite_count(int invite_count) {
        this.invite_count = invite_count;
    }

    public String getCheck_delete_key() {
        return check_delete_key;
    }

    public void setCheck_delete_key(String check_delete_key) {
        this.check_delete_key = check_delete_key;
    }
}

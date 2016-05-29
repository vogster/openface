package com.unlogicon.openface.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Nik on 24.04.2016.
 */
public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;

    private static final String KEY_TOKEN = "token";
    private static final String KEY_VK_USERID = "vk_user_id";
    private static final String KEY_USERID = "user_id";
    private static final String KEY_REFRESH_TOKEN_TIMESTAMP = "refresh_token_timestamp";
    private static final String KEY_SERVER_IMG_HEIGHT = "server_img_height";
    private static final String KEY_SERVER_IMG_WIDTH = "server_img_width";
    private static final String KEY_SEARCH_COUNT = "search_count";

    public SharedPreferencesHelper(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setToken(String token){
        sharedPreferences.edit().putString(KEY_TOKEN, token).commit();
    }

    public String getToken(){
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void setVkUserId(String vk_user_id){
        sharedPreferences.edit().putString(KEY_VK_USERID, vk_user_id).commit();
    }

    public String getVkUserId(){
        return sharedPreferences.getString(KEY_VK_USERID, null);
    }

    public void setUserId(int user_id) {
        sharedPreferences.edit().putInt(KEY_USERID, user_id).commit();
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USERID, 0);
    }

    public void setTimestamp(String timestamp){
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN_TIMESTAMP, timestamp).commit();
    }

    public String getTimestamp(){
        return sharedPreferences.getString(KEY_REFRESH_TOKEN_TIMESTAMP, null);
    }

     public void setServerWidth(float width){
         sharedPreferences.edit().putFloat(KEY_SERVER_IMG_WIDTH, width).commit();
     }

    public float getServerWidth(){
        return sharedPreferences.getFloat(KEY_SERVER_IMG_WIDTH, -1.0F);
    }

    public void setServerHeight(float height){
        sharedPreferences.edit().putFloat(KEY_SERVER_IMG_HEIGHT, height).commit();
    }

    public float getServerHeight(){
        return sharedPreferences.getFloat(KEY_SERVER_IMG_HEIGHT, -1.0F);
    }

    public void setSearchCount(int search_count){
        sharedPreferences.edit().putInt(KEY_SEARCH_COUNT, search_count).commit();
    }

    public int getSearchCount(){
        return sharedPreferences.getInt(KEY_SEARCH_COUNT, 0);
    }
}

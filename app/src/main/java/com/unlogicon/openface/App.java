package com.unlogicon.openface;

import android.app.Application;
import android.content.SharedPreferences;

import com.unlogicon.openface.helper.SharedPreferencesHelper;
import com.vk.sdk.VKSdk;


/**
 * Created by Nik on 24.04.2016.
 */
public class App extends Application {

    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
    }

    public SharedPreferencesHelper getSharedPreferencesHelper(){
        return sharedPreferencesHelper;
    }
}

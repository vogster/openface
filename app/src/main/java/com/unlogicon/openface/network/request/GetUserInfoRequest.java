package com.unlogicon.openface.network.request;

import com.unlogicon.openface.model.UserInfo;
import com.unlogicon.openface.network.INetworkManager;

import retrofit2.Callback;

/**
 * Created by Nik on 24.04.2016.
 */
public class GetUserInfoRequest {

    private static volatile GetUserInfoRequest instance;

    public static GetUserInfoRequest getInstance(){
        GetUserInfoRequest localInstance = instance;
        if (localInstance == null) {
            synchronized (GetUserInfoRequest.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new GetUserInfoRequest();
                }
            }
        }
        return localInstance;
    }

    public void execute(INetworkManager iNetworkManager, Callback<UserInfo> getUserInfoCallback){
        iNetworkManager.getUserInfo().enqueue(getUserInfoCallback);
    }
}

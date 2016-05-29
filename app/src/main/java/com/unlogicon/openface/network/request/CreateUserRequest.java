package com.unlogicon.openface.network.request;

import com.unlogicon.openface.model.CreateUser;
import com.unlogicon.openface.network.INetworkManager;

import retrofit2.Callback;

/**
 * Created by Nik on 24.04.2016.
 */
public class CreateUserRequest {

    private static volatile CreateUserRequest instance;

    public static CreateUserRequest getInstance(){
        CreateUserRequest localInstance = instance;
        if (localInstance == null) {
            synchronized (CreateUserRequest.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CreateUserRequest();
                }
            }
        }
        return localInstance;
    }

    public void execute(String uid, String token, INetworkManager iNetworkManager, Callback<CreateUser> createUserCallback){
        iNetworkManager.createUser(uid, token).enqueue(createUserCallback);
    }
}

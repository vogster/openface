package com.unlogicon.openface.network.request;

import com.unlogicon.openface.model.History;
import com.unlogicon.openface.network.INetworkManager;

import retrofit2.Callback;

/**
 * Created by Nik on 24.04.2016.
 */
public class GetHistoryRequest {
    private static volatile GetHistoryRequest instance;

    public static GetHistoryRequest getInstance(){
        GetHistoryRequest localInstance = instance;
        if (localInstance == null) {
            synchronized (GetHistoryRequest.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new GetHistoryRequest();
                }
            }
        }
        return localInstance;
    }

    public void execute(INetworkManager iNetworkManager, Callback<History[]> getHistoryCallback){
        iNetworkManager.getHistory().enqueue(getHistoryCallback);
    }
}

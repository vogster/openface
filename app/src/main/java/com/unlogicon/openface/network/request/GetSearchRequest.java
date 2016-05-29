package com.unlogicon.openface.network.request;

import com.unlogicon.openface.model.Search;
import com.unlogicon.openface.network.INetworkManager;

import retrofit2.Callback;

/**
 * Created by Nik on 24.04.2016.
 */
public class GetSearchRequest {
    private static volatile GetSearchRequest instance;

    public static GetSearchRequest getInstance(){
        GetSearchRequest localInstance = instance;
        if (localInstance == null) {
            synchronized (GetSearchRequest.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new GetSearchRequest();
                }
            }
        }
        return localInstance;
    }

    public void execute(String hash, String image_hash,
                        int user_id, int x1, int y1, int x2, int y2,
                        INetworkManager iNetworkManager, Callback<Search[]> getSearchCallback){
        iNetworkManager.getSearch(hash, image_hash, user_id, x1, y1, x2, y2).enqueue(getSearchCallback);
    }
}

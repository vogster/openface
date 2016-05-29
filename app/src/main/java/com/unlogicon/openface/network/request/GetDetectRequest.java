package com.unlogicon.openface.network.request;

import com.unlogicon.openface.model.Detect;
import com.unlogicon.openface.network.INetworkManager;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

/**
 * Created by Nik on 24.04.2016.
 */
public class GetDetectRequest {
    private static volatile GetDetectRequest instance;

    public static GetDetectRequest getInstance(){
        GetDetectRequest localInstance = instance;
        if (localInstance == null) {
            synchronized (GetDetectRequest.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new GetDetectRequest();
                }
            }
        }
        return localInstance;
    }

    public void execute(RequestBody description, MultipartBody.Part image, INetworkManager iNetworkManager, Callback<Detect> getDetectCallback){
        iNetworkManager.getDetect(description, image).enqueue(getDetectCallback);
    }

    public void execute(String image_url, INetworkManager iNetworkManager, Callback<Detect> getDetectCallback){
        iNetworkManager.getDetect(image_url).enqueue(getDetectCallback);
    }
}

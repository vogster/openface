package com.unlogicon.openface.network;

import android.content.Context;

import com.unlogicon.openface.App;
import com.unlogicon.openface.model.CreateUser;
import com.unlogicon.openface.model.Detect;
import com.unlogicon.openface.model.History;
import com.unlogicon.openface.model.Search;
import com.unlogicon.openface.model.UserInfo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;

/**
 * Created by Nik on 24.04.2016.
 */
public class NetworkManagerImpl implements INetworkManager {

    private RestClient restClient;
    private Context context;

    public NetworkManagerImpl(Context context){
        this.restClient = new RestClient(context);
        this.context = context;
    }

    @Override
    public Call<CreateUser> createUser(String uid, String token) {
        return restClient.getApi().createUser(uid, token);
    }

    @Override
    public Call<UserInfo> getUserInfo() {
        return restClient.getApi().getUserInfo();
    }

    @Override
    public Call<History[]> getHistory() {
        return restClient.getApi().getHistory(String.valueOf(((App) context.getApplicationContext()).getSharedPreferencesHelper().getUserId()));
    }

    @Override
    public Call<Detect> getDetect(RequestBody description, MultipartBody.Part image) {
        return restClient.getApi().getDetect(String.valueOf(((App) context.getApplicationContext()).getSharedPreferencesHelper().getUserId()), description, image);
    }

    @Override
    public Call<Detect> getDetect(String image_url) {
        return restClient.getApi().getDetect(String.valueOf(((App) context.getApplicationContext()).getSharedPreferencesHelper().getUserId()), image_url);
    }

    @Override
    public Call<Search[]> getSearch(String hash, String image_hash, int user_id, int x1, int y1, int x2, int y2) {
        return restClient.getApi().getSearch(hash, image_hash, user_id, x1, y1, x2, y2);
    }
}

package com.unlogicon.openface.network;

import com.unlogicon.openface.model.CreateUser;
import com.unlogicon.openface.model.Detect;
import com.unlogicon.openface.model.History;
import com.unlogicon.openface.model.Search;
import com.unlogicon.openface.model.UserInfo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.Query;

/**
 * Created by Nik on 24.04.2016.
 */
public interface INetworkManager {
    Call<CreateUser> createUser(String uid, String token);
    Call<UserInfo> getUserInfo();
    Call<History[]> getHistory();
    Call<Detect> getDetect(RequestBody description, MultipartBody.Part image);
    Call<Detect> getDetect(String image_url);
    Call<Search[]> getSearch(String hash, String image_hash, int user_id, int x1, int y1, int x2, int y2);
}

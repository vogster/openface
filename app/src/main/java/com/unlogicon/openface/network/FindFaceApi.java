package com.unlogicon.openface.network;

import com.unlogicon.openface.model.CreateUser;
import com.unlogicon.openface.model.Detect;
import com.unlogicon.openface.model.History;
import com.unlogicon.openface.model.Search;
import com.unlogicon.openface.model.UserInfo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Nik on 24.04.2016.
 */
public interface FindFaceApi {
    @POST("/v2/user/new")
    Call<CreateUser> createUser(@Query("uid") String uid,
                               @Query("token") String token);

    @GET("/v2/user-info/list")
    Call<UserInfo> getUserInfo();

    @GET("/v2/search/history")
    Call<History[]> getHistory(@Query("uid") String uid);

    @Multipart
    @POST("/v2/search/detect")
    Call<Detect> getDetect(@Query("user_id") String user_id,
                           @Part("image") RequestBody description,
                           @Part MultipartBody.Part file);

    @POST("/v2/search/detect")
    Call<Detect> getDetect(@Query("user_id") String user_id,
                           @Query("image_url") String image_url);

    @GET("/v2/search/list3")
    Call<Search[]> getSearch(@Query("hash") String hash,
                             @Query("image_hash") String image_hash,
                             @Query("user_id") int user_id,
                             @Query("x1") int x1,
                             @Query("y1") int y1,
                             @Query("x2") int x2,
                             @Query("y2") int y2);
}

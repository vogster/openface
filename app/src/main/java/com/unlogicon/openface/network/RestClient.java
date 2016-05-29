package com.unlogicon.openface.network;

import android.content.Context;

import com.unlogicon.openface.App;
import com.unlogicon.openface.Const;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nik on 24.04.2016.
 */
public class RestClient {
    private FindFaceApi findFaceApi;
    private Context context;

    public RestClient(Context context) {
        this.context = context;

        String url = Const.API_URL;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = getHttpClientBuilder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .client(client)
                .build();

        findFaceApi = retrofit.create(FindFaceApi.class);
    }

    public FindFaceApi getApi() {
        return findFaceApi;
    }

    private OkHttpClient.Builder getHttpClientBuilder() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + ((App) context.getApplicationContext()).getSharedPreferencesHelper().getToken() )
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        return httpClient;
    }

}

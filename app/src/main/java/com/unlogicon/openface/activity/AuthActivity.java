package com.unlogicon.openface.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.unlogicon.openface.App;
import com.unlogicon.openface.R;
import com.unlogicon.openface.model.CreateUser;
import com.unlogicon.openface.network.NetworkManagerImpl;
import com.unlogicon.openface.network.request.CreateUserRequest;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends BaseActivity {

    private Button mButtonLoginVk;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_vk);

        mActivity = this;

        mButtonLoginVk = (Button) findViewById(R.id.btnLogin_vk);
        mButtonLoginVk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.login(AuthActivity.this, getResources().getStringArray(R.array.vk_scope));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                ((App) getApplication()).getSharedPreferencesHelper().setToken(res.accessToken);
                ((App) getApplication()).getSharedPreferencesHelper().setVkUserId(res.userId);
                CreateUserRequest.getInstance().execute(res.userId, res.accessToken, new NetworkManagerImpl(AuthActivity.this), new Callback<CreateUser>() {
                    @Override
                    public void onResponse(Call<CreateUser> call, Response<CreateUser> response) {
                        if (response.body().getSuccess().equals("yes")) {
                            ((App) getApplication()).getSharedPreferencesHelper().setUserId(response.body().getUserId());
                            ((App) getApplication()).getSharedPreferencesHelper().setTimestamp(String.valueOf(System.currentTimeMillis()));
                            mActivity.setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateUser> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onError(VKError error) {

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

package com.unlogicon.openface.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.unlogicon.openface.App;
import com.unlogicon.openface.R;
import com.unlogicon.openface.activity.AuthActivity;
import com.unlogicon.openface.activity.BaseActivity;
import com.unlogicon.openface.fragment.HistoryFragment;
import com.unlogicon.openface.fragment.UploadPhotoFragment;
import com.unlogicon.openface.helper.SharedPreferencesHelper;
import com.unlogicon.openface.model.CreateUser;
import com.unlogicon.openface.model.History;
import com.unlogicon.openface.model.UserInfo;
import com.unlogicon.openface.network.request.CreateUserRequest;
import com.unlogicon.openface.network.request.GetHistoryRequest;
import com.unlogicon.openface.network.request.GetUserInfoRequest;
import com.unlogicon.openface.network.NetworkManagerImpl;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferencesHelper preferencesHelper;
    private Activity mActivity;

    private CircleImageView mProfileImage;
    private TextView mNameProfile;
    private NavigationView navigationView;

    private FragmentManager fragmentManager;

    private static final int LOGIN_REQUEST_CODE = 548;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferencesHelper = new SharedPreferencesHelper(this);
        fragmentManager = getSupportFragmentManager();

        if (preferencesHelper.getToken() == null && preferencesHelper.getUserId() == 0) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mNameProfile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.profile_name);
        mProfileImage = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        fragmentManager.beginTransaction().replace(R.id.content_frame, new UploadPhotoFragment()).commit();

        if (((App) getApplication()).getSharedPreferencesHelper().getTimestamp() != null) {
            if (Math.abs(Long.parseLong(((App) getApplication()).getSharedPreferencesHelper().getTimestamp()) - System.currentTimeMillis()) > 32400000) {
                updateToken();
            } else {
                refresh();
            }
        }

    }

    private void updateToken() {
        CreateUserRequest.getInstance().execute(((App) getApplication()).getSharedPreferencesHelper().getVkUserId(), ((App) getApplication()).getSharedPreferencesHelper().getToken(), new NetworkManagerImpl(mActivity), new Callback<CreateUser>() {
            @Override
            public void onResponse(Call<CreateUser> call, Response<CreateUser> response) {
                if (response.body().getSuccess().equals("yes")) {
                    ((App) getApplication()).getSharedPreferencesHelper().setTimestamp(String.valueOf(System.currentTimeMillis()));
                    refresh();
                }
            }

            @Override
            public void onFailure(Call<CreateUser> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200"));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    Picasso.with(mActivity).load(response.json.getJSONArray("response").getJSONObject(0).getString("photo_200")).into(mProfileImage);
                    mNameProfile.setText(response.json.getJSONArray("response").getJSONObject(0).getString("first_name").toString() + " "
                            + response.json.getJSONArray("response").getJSONObject(0).getString("last_name").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VKError error) {

            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {

            }
        });

        GetUserInfoRequest.getInstance().execute(new NetworkManagerImpl(this), new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                ((App) getApplicationContext()).getSharedPreferencesHelper().setSearchCount(response.body().getSearch_count());
                FragmentManager fm = getSupportFragmentManager();

                UploadPhotoFragment fragment = (UploadPhotoFragment) fm.findFragmentById(R.id.content_frame);
                fragment.updateSearchCount();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_search) {
            fragment = new UploadPhotoFragment();
        } else if (id == R.id.nav_history) {
            fragment = new HistoryFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOGIN_REQUEST_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        refresh();
                        break;
                    case RESULT_CANCELED:
                        Intent intent = new Intent(this, AuthActivity.class);
                        startActivityForResult(intent, LOGIN_REQUEST_CODE);
                        break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

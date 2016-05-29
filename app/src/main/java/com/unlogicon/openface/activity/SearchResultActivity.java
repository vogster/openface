package com.unlogicon.openface.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.cesards.cropimageview.CropImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.unlogicon.openface.App;
import com.unlogicon.openface.R;
import com.unlogicon.openface.adapter.SearchResultAdapter;
import com.unlogicon.openface.model.Detect;
import com.unlogicon.openface.model.Search;
import com.unlogicon.openface.model.UserInfo;
import com.unlogicon.openface.model.vk.VKPhoto;
import com.unlogicon.openface.model.vk.VKUser;
import com.unlogicon.openface.network.NetworkManagerImpl;
import com.unlogicon.openface.network.request.GetSearchRequest;
import com.unlogicon.openface.network.request.GetUserInfoRequest;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends BaseActivity implements SearchResultAdapter.OnItemClickListener {

    public static final String EXTRA_DETECTED = "detected";
    public static final String EXTRA_BBOX_POSITION = "bbox_position";

    private int bbox_position = 0;

    private Detect detect;
    private RecyclerView mDataList;
    private SearchResultAdapter adapter;
    private ArrayList<VKUser> users = new ArrayList<VKUser>();
    private ArrayList<VKPhoto> photos = new ArrayList<VKPhoto>();
    private Activity mActivity;
    private Toolbar toolbar;
    private ImageView mHeaderImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mActivity = this;

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_DETECTED)) {
            detect = (Detect) intent.getSerializableExtra(EXTRA_DETECTED);
        } else {
            finish();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.search));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mHeaderImage = (ImageView) findViewById(R.id.headerImage);

        bbox_position = intent.getIntExtra(EXTRA_BBOX_POSITION, 0);

        mDataList = (RecyclerView) findViewById(R.id.dataList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mDataList.setLayoutManager(llm);
        mDataList.setHasFixedSize(true);
        adapter = new SearchResultAdapter(users, photos);
        mDataList.setAdapter(adapter);

        adapter.setItemClickListener(this);

        if (savedInstanceState == null) {
            loadData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("photos", photos);
        savedInstanceState.putSerializable("users", users);
        savedInstanceState.putSerializable("detect", detect);
        savedInstanceState.putInt("bbox_position", bbox_position);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        photos = (ArrayList<VKPhoto>) savedInstanceState.getSerializable("photos");
        users = (ArrayList<VKUser>) savedInstanceState.getSerializable("users");
        detect = (Detect) savedInstanceState.getSerializable("detect");
        Picasso.with(mActivity).load(detect.getDetect_photo_url()).into(mHeaderImage);
        bbox_position = savedInstanceState.getInt("bbox_position");
        adapter = new SearchResultAdapter(users, photos);
        mDataList.setAdapter(adapter);
        adapter.setItemClickListener(this);
    }

    public void loadData() {

        Picasso.with(mActivity).load(detect.getDetect_photo_url()).into(mHeaderImage);

        GetSearchRequest.getInstance().execute(detect.getBboxes().get(bbox_position).getHash(), detect.getImage_hash(), Integer.parseInt(detect.getUser_id()),
                detect.getBboxes().get(bbox_position).getX1(), detect.getBboxes().get(bbox_position).getY1(), detect.getBboxes().get(bbox_position).getX2(), detect.getBboxes().get(bbox_position).getY2(),
                new NetworkManagerImpl(this), new Callback<Search[]>() {
                    @Override
                    public void onResponse(Call<Search[]> call, Response<Search[]> response) {

                        GetUserInfoRequest.getInstance().execute(new NetworkManagerImpl(mActivity), new Callback<UserInfo>() {
                            @Override
                            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                                ((App) getApplication()).getSharedPreferencesHelper().setSearchCount(response.body().getSearch_count());
                            }

                            @Override
                            public void onFailure(Call<UserInfo> call, Throwable t) {

                            }
                        });

                        StringBuilder builderUsersId = new StringBuilder();
                        StringBuilder builderUsersPhotos = new StringBuilder();
                        for (int i = 0; i < response.body().length; i++) {
                            if (i > 0) {
                                builderUsersId.append("," + response.body()[i].getUser_id());
                                builderUsersPhotos.append("," + response.body()[i].getUser_id() + "_" + response.body()[i].getPhoto_id());
                            } else {
                                builderUsersId.append(response.body()[i].getUser_id());
                                builderUsersPhotos.append(response.body()[i].getUser_id() + "_" + response.body()[i].getPhoto_id());
                            }
                        }

                        VKRequest requestUser = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS, builderUsersId.toString(), VKApiConst.FIELDS,
                                "photo_100,photo_400_orig,photo_max_orig,city,bdate"));
                        VKRequest requestPhoto = new VKRequest("photos.getById", VKParameters.from(VKApiConst.PHOTOS, builderUsersPhotos.toString()));
                        VKBatchRequest batch = new VKBatchRequest(requestUser, requestPhoto);

                        batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
                            @Override
                            public void onComplete(VKResponse[] responses) {
                                JSONArray userJson = null;
                                JSONArray photoJson = null;
                                try {
                                    userJson = responses[0].json.getJSONArray("response");
                                    photoJson = responses[1].json.getJSONArray("response");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                for (int i = 0; i < userJson.length(); i++) {
                                    VKUser VKUser = new VKUser();
                                    VKUser = new Gson().fromJson(userJson.optJSONObject(i).toString(), VKUser.class);
                                    users.add(VKUser);
                                }
                                for (int i = 0; i < photoJson.length(); i++) {
                                    VKPhoto photo = new VKPhoto();
                                    photo = new Gson().fromJson(photoJson.optJSONObject(i).toString(), VKPhoto.class);
                                    photos.add(photo);
                                }
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onError(VKError error) {

                            }

                        });


                    }

                    @Override
                    public void onFailure(Call<Search[]> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onClick(VKUser VKUser) {
        Intent i = new Intent(mActivity, UserFoundActivity.class);
        i.putExtra(UserFoundActivity.EXTRA_VKUSER, VKUser);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

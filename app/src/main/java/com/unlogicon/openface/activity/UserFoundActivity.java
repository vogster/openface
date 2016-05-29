package com.unlogicon.openface.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.unlogicon.openface.Const;
import com.unlogicon.openface.R;
import com.unlogicon.openface.adapter.UserFoundAdapter;
import com.unlogicon.openface.model.vk.VKPhoto;
import com.unlogicon.openface.model.vk.VKUser;
import com.unlogicon.openface.view.AutofitRecyclerView;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class UserFoundActivity extends BaseActivity {

    public static final String EXTRA_VKUSER = "vk_user";
    private VKUser VKUser;
    private Activity mActivity;
    private ArrayList<VKPhoto> mPhotos = new ArrayList<VKPhoto>();

    private UserFoundAdapter adapter;

    private AutofitRecyclerView mRecycleView;
    private Toolbar mToolBar;
    private ImageView mHeaderImageView;
    private FloatingActionButton mOpenVk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_found);
        mActivity = this;
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_VKUSER)) {
            VKUser = (VKUser) intent.getSerializableExtra(EXTRA_VKUSER);
        } else {
            finish();
        }
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mHeaderImageView = (ImageView) findViewById(R.id.headerImage);
        mOpenVk = (FloatingActionButton) findViewById(R.id.open_vk);
        mOpenVk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Const.VK_ID_URL + String.valueOf(VKUser.getId())));
                startActivity(browserIntent);
            }
        });
        Picasso.with(mActivity).load(VKUser.getPhoto_max_orig()).into(mHeaderImageView);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(VKUser.getFirst_name() + " " + VKUser.getLast_name());

        adapter = new UserFoundAdapter(mPhotos);
        adapter.setOnItemClickListener(new UserFoundAdapter.OnItemClickListener() {
            @Override
            public void onClick(ArrayList<VKPhoto> photos, int position) {
                Intent i = new Intent(mActivity, GalleryActivity.class);
                i.putExtra(GalleryActivity.EXTRA_PHOTOS, photos);
                i.putExtra(GalleryActivity.EXTRA_POITION, position);
                startActivity(i);
            }
        });
        mRecycleView = (AutofitRecyclerView) findViewById(R.id.recycleView);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(adapter);

        VKRequest requestPhoto = new VKRequest("photos.getAll", VKParameters.from(VKApiConst.OWNER_ID, VKUser.getId(), VKApiConst.COUNT, 200));
        requestPhoto.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                JSONArray jsonPhotos = null;
                try {
                    jsonPhotos = response.json.getJSONObject("response").getJSONArray("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < jsonPhotos.length(); i++) {
                    VKPhoto photo;
                    photo = new Gson().fromJson(jsonPhotos.optJSONObject(i).toString(), VKPhoto.class);
                    mPhotos.add(photo);
                }
                adapter.notifyDataSetChanged();
            }
        });
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

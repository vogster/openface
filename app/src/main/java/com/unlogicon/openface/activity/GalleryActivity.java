package com.unlogicon.openface.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.unlogicon.openface.R;
import com.unlogicon.openface.adapter.GalleryPagerAdapter;
import com.unlogicon.openface.model.vk.VKPhoto;
import com.unlogicon.openface.view.HackyViewPager;

import java.util.ArrayList;

public class GalleryActivity extends BaseActivity {

    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_POITION = "extra_position";
    private static final String ISLOCKED_ARG = "isLocked";


    private ArrayList<VKPhoto> photos = new ArrayList<VKPhoto>();

    private HackyViewPager mViewPager;
    private Toolbar mToolBar;
    private int position;

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mActivity = this;

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_PHOTOS) && intent.hasExtra(EXTRA_PHOTOS)) {
            photos = (ArrayList<VKPhoto>) intent.getSerializableExtra(EXTRA_PHOTOS);
            position = intent.getIntExtra(EXTRA_POITION, 0);
        } else {
            finish();
        }

        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.photos));

        mViewPager.setAdapter(new GalleryPagerAdapter(this, photos));
        mViewPager.setCurrentItem(position);

        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
            ((HackyViewPager) mViewPager).setLocked(isLocked);
        }

    }
}

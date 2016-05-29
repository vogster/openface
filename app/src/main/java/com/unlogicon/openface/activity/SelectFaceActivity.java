package com.unlogicon.openface.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.unlogicon.openface.R;
import com.unlogicon.openface.model.Detect;

import com.unlogicon.openface.view.DrawView;

public class SelectFaceActivity extends AppCompatActivity {

    public static final String EXTRA_DETECTED = "detected";

    private Activity mActivity;
    private Detect detect;

    private DrawView mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_face);

        mActivity = this;

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_DETECTED)){
            detect = (Detect) intent.getSerializableExtra(EXTRA_DETECTED);
        } else {
            finish();
        }

        mPhoto = (DrawView) findViewById(R.id.photo);
        Picasso.with(mActivity).load(detect.getDetect_photo_url()).into(mPhoto, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
        mPhoto.setDetect(detect, mPhoto, mActivity);
        mPhoto.setRectClickListener(new DrawView.OnRectClickListener() {
            @Override
            public void onClickRect(int bbox_position) {
                Intent intent = new Intent(mActivity, SearchResultActivity.class);
                intent.putExtra(SearchResultActivity.EXTRA_DETECTED, detect);
                intent.putExtra(SearchResultActivity.EXTRA_BBOX_POSITION, bbox_position);
                startActivity(intent);
            }
        });
    }
}

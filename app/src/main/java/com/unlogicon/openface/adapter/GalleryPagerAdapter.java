package com.unlogicon.openface.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.unlogicon.openface.R;
import com.unlogicon.openface.model.vk.VKPhoto;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Nik on 06.05.2016.
 */
public class GalleryPagerAdapter extends PagerAdapter {

    private ArrayList<VKPhoto> photos;
    private Activity activity;
    private LayoutInflater inflater;

    public GalleryPagerAdapter(Activity activity, ArrayList<VKPhoto> photos) {
        this.activity = activity;
        this.photos = photos;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {

        View imageLayout = inflater.inflate(R.layout.gallery_layout, container, false);
        final PhotoView photoView = (PhotoView) imageLayout.findViewById(R.id.image);
        final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

        Callback callback = new Callback() {
            @Override
            public void onSuccess() {
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        };
        if (photos.get(position).getPhoto_2560() != null)
            Picasso.with(activity).load(photos.get(position).getPhoto_2560()).into(photoView, callback);
        else
            Picasso.with(activity).load(photos.get(position).getPhoto_604()).into(photoView, callback);
        container.addView(imageLayout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);


        return imageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
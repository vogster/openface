package com.unlogicon.openface.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.unlogicon.openface.App;
import com.unlogicon.openface.R;
import com.unlogicon.openface.activity.MainActivity;
import com.unlogicon.openface.activity.SearchResultActivity;
import com.unlogicon.openface.adapter.HistoryAdapter;
import com.unlogicon.openface.model.Detect;
import com.unlogicon.openface.model.History;
import com.unlogicon.openface.network.NetworkManagerImpl;
import com.unlogicon.openface.network.request.GetDetectRequest;
import com.unlogicon.openface.network.request.GetHistoryRequest;
import com.unlogicon.openface.utils.BboxComparison;
import com.unlogicon.openface.utils.ImagePicker;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nik on 06.05.2016.
 */
public class HistoryFragment extends Fragment {

    private Activity mActivity;
    private RecyclerView mRecycleView;

    private ArrayList<History> histories = new ArrayList<History>();
    private HistoryAdapter adapter;

    public HistoryFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        ((MainActivity) mActivity).getSupportActionBar().setTitle(mActivity.getString(R.string.history));
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        adapter = new HistoryAdapter(histories);
        adapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onClick(final History history) {
                GetDetectRequest.getInstance().execute(history.getDetect_photo(), new NetworkManagerImpl(mActivity), new Callback<Detect>() {
                    @Override
                    public void onResponse(Call<Detect> call, Response<Detect> response) {
                        Intent intent = new Intent(mActivity, SearchResultActivity.class);
                        intent.putExtra(SearchResultActivity.EXTRA_DETECTED, response.body());
                        intent.putExtra(SearchResultActivity.EXTRA_BBOX_POSITION, BboxComparison.compare(history, response.body()));
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Detect> call, Throwable t) {

                    }
                });
            }
        });
        mRecycleView.setAdapter(adapter);

        GetHistoryRequest.getInstance().execute(new NetworkManagerImpl(mActivity), new Callback<History[]>() {
            @Override
            public void onResponse(Call<History[]> call, final Response<History[]> response) {
                histories.addAll(Arrays.asList(response.body()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<History[]> call, Throwable t) {

            }
        });
    }
}

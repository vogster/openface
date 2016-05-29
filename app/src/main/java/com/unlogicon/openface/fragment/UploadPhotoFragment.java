package com.unlogicon.openface.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unlogicon.openface.App;
import com.unlogicon.openface.R;
import com.unlogicon.openface.activity.MainActivity;
import com.unlogicon.openface.activity.SearchResultActivity;
import com.unlogicon.openface.activity.SelectFaceActivity;
import com.unlogicon.openface.model.Detect;
import com.unlogicon.openface.model.UserInfo;
import com.unlogicon.openface.network.NetworkManagerImpl;
import com.unlogicon.openface.network.request.GetDetectRequest;
import com.unlogicon.openface.network.request.GetUserInfoRequest;
import com.unlogicon.openface.utils.ImagePicker;
import com.unlogicon.openface.view.ColoredSnackbar;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nik on 27.04.2016.
 */
public class UploadPhotoFragment extends Fragment {

    private Activity mActivity;
    private ImageView mSelectPhoto;
    private TextView mSearchCount;
    private static final int PICK_IMAGE_ID = 234;
    private static final int SEARCH_RESULT_ID = 185;
    private static final int SELECT_FACE_ID = 25;

    private  View rootView;

    public UploadPhotoFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_upload, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        ((MainActivity) mActivity).getSupportActionBar().setTitle(mActivity.getString(R.string.search));
        mSearchCount = (TextView) view.findViewById(R.id.search_count);
        mSelectPhoto = (ImageView) view.findViewById(R.id.selectPhoto);
        mSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(mActivity);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
            }
        });
        updateSearchCount();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateSearchCount();
        switch(requestCode) {
            case PICK_IMAGE_ID:
                switch (resultCode){
                    case Activity.RESULT_OK:
                        final File image = ImagePicker.getImageFromResult(mActivity, resultCode, data);
                        RequestBody requestFile =
                                RequestBody.create(MediaType.parse("multipart/form-data"), image);
                        MultipartBody.Part body =
                                MultipartBody.Part.createFormData("image", image.getName(), requestFile);
                        RequestBody description =
                                RequestBody.create(
                                        MediaType.parse("multipart/form-data"), "");


                        GetDetectRequest.getInstance().execute(description, body, new NetworkManagerImpl(mActivity), new Callback<Detect>() {
                            @Override
                            public void onResponse(Call<Detect> call, Response<Detect> response) {
                                if (response.body().getBboxes() != null && response.body().getBboxes().size() == 1){
                                    Intent intent = new Intent(mActivity, SearchResultActivity.class);
                                    intent.putExtra(SearchResultActivity.EXTRA_DETECTED, response.body());
                                    startActivityForResult(intent, SEARCH_RESULT_ID);
                                } else if (response.body().getBboxes() != null && response.body().getBboxes().size() > 1){
                                    Intent intent = new Intent(mActivity, SelectFaceActivity.class);
                                    intent.putExtra(SelectFaceActivity.EXTRA_DETECTED, response.body());
                                    startActivityForResult(intent, SELECT_FACE_ID);
                                } else if (response.body().getBboxes() == null){
                                    ColoredSnackbar.alert(Snackbar.make(rootView, getString(R.string.face_no_detected), Snackbar.LENGTH_LONG)).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<Detect> call, Throwable t) {

                            }
                        });
                        break;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public void updateSearchCount(){
        mSearchCount.setText(mActivity.getString(R.string.free_searches) + ": " +
                ((App) getActivity().getApplicationContext()).getSharedPreferencesHelper().getSearchCount());
    }

}

package com.unlogicon.openface.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.unlogicon.openface.R;
import com.unlogicon.openface.model.vk.VKPhoto;

import java.util.ArrayList;

/**
 * Created by Nik on 05.05.2016.
 */
public class UserFoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<VKPhoto> photos = new ArrayList<VKPhoto>();
    private OnItemClickListener onItemClickListener;


    public UserFoundAdapter(ArrayList<VKPhoto> photos) {
        this.photos = photos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.found_user_row, parent, false);
        return new ViewHolderResult(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final VKPhoto photo = photos.get(position);
        final ViewHolderResult viewHolderResult = (ViewHolderResult) holder;
        Picasso.with(mContext).load(photo.getPhoto_604()).into(viewHolderResult.mPhoto);
        viewHolderResult.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(photos, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ViewHolderResult extends RecyclerView.ViewHolder {

        private ImageView mPhoto;
        private CardView mCardView;

        public ViewHolderResult(View itemView) {
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.photo);
            mCardView = (CardView) itemView.findViewById(R.id.cv);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(ArrayList<VKPhoto> photos, int position);
    }

}

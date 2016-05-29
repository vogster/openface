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
import com.unlogicon.openface.model.Bboxes;
import com.unlogicon.openface.model.Detect;
import com.unlogicon.openface.model.History;
import com.unlogicon.openface.model.Search;
import com.unlogicon.openface.model.vk.VKPhoto;

import java.util.ArrayList;

/**
 * Created by Nik on 06.05.2016.
 */
public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<History> histories;
    private OnItemClickListener onItemClickListener;

    public HistoryAdapter(ArrayList<History> histories){
        this.histories = histories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.found_user_row, parent, false);
        return new ViewHolderResult(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final History history = histories.get(position);
        final ViewHolderResult viewHolderResult = (ViewHolderResult) holder;
        Picasso.with(mContext).load(history.getImage()).into(viewHolderResult.mPhoto);
        viewHolderResult.mCardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(history);
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
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

    public interface OnItemClickListener{
        void onClick(History history);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}

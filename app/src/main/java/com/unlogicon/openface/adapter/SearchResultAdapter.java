package com.unlogicon.openface.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.unlogicon.openface.R;
import com.unlogicon.openface.model.Detect;
import com.unlogicon.openface.model.vk.VKPhoto;
import com.unlogicon.openface.model.vk.VKUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nik on 28.04.2016.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<VKUser> users = new ArrayList<VKUser>();
    private ArrayList<VKPhoto> photos = new ArrayList<VKPhoto>();

    private static final int VIEW_TYPE_RESULT = 1;

    private OnItemClickListener mItemClickListener;

    public SearchResultAdapter(ArrayList<VKUser> users, ArrayList<VKPhoto> photos){
        this.users = users;
        this.photos = photos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        final View v;
        switch (viewType) {
            default:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_result_row, parent, false);
                return new ViewHolderResult(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderResult){
            final ViewHolderResult viewHolderResult = (ViewHolderResult) holder;
            final VKUser user = users.get(position);
            VKPhoto userPhoto = null;

            //TODO Работает, но чет не очень
            for (VKPhoto photo : photos){
                if (user.getId() == photo.getOwnwer_id()){
                    userPhoto = photo;
                    break;
                }
            }
            if (userPhoto != null)
                Picasso.with(mContext).load(userPhoto.getPhoto_130()).into(viewHolderResult.mProfileImage);
            else
                Picasso.with(mContext).load(user.getPhoto_100()).into(viewHolderResult.mProfileImage);
            viewHolderResult.mPersonName.setText(user.getFirst_name());
            viewHolderResult.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClick(user);
                }
            });
            if (user.getCity() != null) {
                viewHolderResult.mPersonInfo.setText(user.getCity().getTitle());
                viewHolderResult.mPersonInfo.setVisibility(View.VISIBLE);
            } else {
                viewHolderResult.mPersonInfo.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    @Override
    public int getItemViewType(int position) {
        switch (position){
            default:
                return VIEW_TYPE_RESULT;
        }
    }


    class ViewHolderResult extends RecyclerView.ViewHolder {

        private CircleImageView mProfileImage;
        private TextView mPersonName;
        private TextView mPersonInfo;
        private CardView mCardView;

        public ViewHolderResult(View itemView) {
            super(itemView);
            mProfileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
            mPersonName = (TextView)  itemView.findViewById(R.id.person_name);
            mPersonInfo = (TextView) itemView.findViewById(R.id.person_info);
            mCardView = (CardView) itemView.findViewById(R.id.cv);
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(VKUser VKUser);
    }

}

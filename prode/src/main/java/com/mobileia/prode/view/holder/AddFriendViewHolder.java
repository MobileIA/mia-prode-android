package com.mobileia.prode.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by matiascamiletti on 6/3/18.
 */

public class AddFriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected OnAddFriendClickListener mListener;

    public AddFriendViewHolder(View itemView, OnAddFriendClickListener listener) {
        super(itemView);
        mListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(mListener != null){
            mListener.onAddFriendClick();
        }
    }

    public interface OnAddFriendClickListener{
        void onAddFriendClick();
    }
}

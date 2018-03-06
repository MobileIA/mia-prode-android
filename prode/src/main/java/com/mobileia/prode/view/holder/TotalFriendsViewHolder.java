package com.mobileia.prode.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mobileia.prode.R;

/**
 * Created by matiascamiletti on 6/3/18.
 */

public class TotalFriendsViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public TotalFriendsViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text_users);
    }

    public void bind(int total){
        textView.setText(total + " Participantes.");
    }
}

package com.mobileia.prode.view.controller;

import android.view.View;

import com.mobileia.prode.adapter.MatchAdapter;
import com.mobileia.prode.entity.Match;
import com.mobileia.prode.view.holder.MatchViewHolder;
import com.mobileia.recyclerview.controller.BaseViewController;

/**
 * Created by matiascamiletti on 20/2/18.
 */

public class MatchViewController extends BaseViewController implements MatchAdapter.OnClickMatchAdapter {

    protected MatchAdapter.OnClickMatchAdapter mListener;
    /**
     * Constructor
     *
     * @param view
     */
    public MatchViewController(View view) {
        super(view);
        // Crear viewHolder
        mViewHolder = new MatchViewHolder(view, this);
    }

    @Override
    public void onClick(Match match) {
        if(mListener != null){
            mListener.onClick(match);
        }
    }

    public void setOnClickMatchListener(MatchAdapter.OnClickMatchAdapter listener){
        mListener = listener;
    }

    /**
     * Setea el partido a mostrar
     * @param match
     */
    public void bind(Match match){
        mViewHolder.bind(match);
    }


}

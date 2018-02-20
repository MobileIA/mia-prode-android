package com.mobileia.prode.view.controller;

import android.view.View;

import com.mobileia.prode.entity.Match;
import com.mobileia.prode.view.holder.MatchViewHolder;
import com.mobileia.recyclerview.controller.BaseViewController;

/**
 * Created by matiascamiletti on 20/2/18.
 */

public class MatchViewController extends BaseViewController {
    /**
     * Constructor
     *
     * @param view
     */
    public MatchViewController(View view) {
        super(view);
        // Crear viewHolder
        mViewHolder = new MatchViewHolder(view);
    }

    /**
     * Setea el partido a mostrar
     * @param match
     */
    public void bind(Match match){
        mViewHolder.bind(match);
    }
}

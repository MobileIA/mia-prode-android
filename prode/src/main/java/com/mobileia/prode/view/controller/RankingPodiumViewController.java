package com.mobileia.prode.view.controller;

import android.app.Activity;
import android.view.View;

import com.mobileia.prode.entity.Ranking;
import com.mobileia.prode.rest.RankingRest;
import com.mobileia.prode.view.holder.RankingPodiumViewHolder;
import com.mobileia.recyclerview.controller.BaseViewController;

import java.util.ArrayList;

/**
 * Created by matiascamiletti on 9/2/18.
 */

public class RankingPodiumViewController extends BaseViewController {

    public RankingPodiumViewController(View view) {
        super(view);
        // Crear viewHolder
        mViewHolder = new RankingPodiumViewHolder(view);
    }

    /**
     * Funcion para cargar el ranking de un grupo especifico
     * @param groupId
     */
    public void loadByGroup(int groupId){
        new RankingRest((Activity)mViewHolder.getView().getContext()).fetchList(groupId, new RankingRest.OnFetchComplete() {
            @Override
            public void onSuccess(ArrayList<Ranking> list) {
                // Mostrar en la vista
                mViewHolder.bind(list);
            }

            @Override
            public void onError() {

            }
        });
    }
}

package com.mobileia.prode.adapter;

import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mobileia.prode.R;
import com.mobileia.prode.entity.Ranking;
import com.mobileia.prode.view.holder.RankingPodiumViewHolder;
import com.mobileia.prode.view.holder.RankingViewHolder;

import java.util.ArrayList;

/**
 * Created by matiascamiletti on 13/2/18.
 */

public class RankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_RANKING_TITLE = 0;
    public static final int TYPE_RANKING = 1;

    protected ArrayList<Ranking> mValues = new ArrayList<>();
    protected int mColorPrimary = 0;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_RANKING_TITLE:
                return new RankingPodiumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking_podium, parent, false));
            case TYPE_RANKING:
                return new RankingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_RANKING_TITLE:
                ((RankingPodiumViewHolder) holder).bind(mValues);
                if(mColorPrimary != 0){
                    ((RankingPodiumViewHolder) holder).imageBackgroundTop.setBackgroundResource(mColorPrimary);
                }
                break;
            case TYPE_RANKING:
                ((RankingViewHolder) holder).bind(mValues.get(position + 2), position);
                if(mColorPrimary != 0){
                    ((RankingViewHolder) holder).textPoints.setTextColor(mColorPrimary);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(mValues.size() <= 3){
            return 1;
        }
        return mValues.size() - 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_RANKING_TITLE;
        }
        return TYPE_RANKING;
    }

    public void setValues(ArrayList<Ranking> list){
        mValues = list;
        notifyDataSetChanged();
    }

    public void setColorPrimary(@ColorRes int colorPrimary){ mColorPrimary = colorPrimary;}
}
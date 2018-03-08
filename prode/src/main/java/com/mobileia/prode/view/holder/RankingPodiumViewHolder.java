package com.mobileia.prode.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.mobileia.prode.R;
import com.mobileia.prode.entity.Ranking;
import com.mobileia.recyclerview.holder.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by matiascamiletti on 30/8/17.
 */

public class RankingPodiumViewHolder extends BaseViewHolder<ArrayList<Ranking>> {

    public final ImageView imageBackgroundTop;
    public final ImageView imageOne;
    public final ImageView imageTwo;
    public final ImageView imageThree;
    public final TextView nameOne;
    public final TextView nameTwo;
    public final TextView nameThree;
    public final TextView pointsOne;
    public final TextView pointsTwo;
    public final TextView pointsThree;

    public RankingPodiumViewHolder(View itemView) {
        super(itemView);
        imageBackgroundTop = itemView.findViewById(R.id.image_background_top);
        imageOne = itemView.findViewById(R.id.image_user_one);
        imageTwo = itemView.findViewById(R.id.image_user_two);
        imageThree = itemView.findViewById(R.id.image_user_three);
        nameOne = itemView.findViewById(R.id.text_user_one);
        nameTwo = itemView.findViewById(R.id.text_user_two);
        nameThree = itemView.findViewById(R.id.text_user_three);
        pointsOne = itemView.findViewById(R.id.points_user_one);
        pointsTwo = itemView.findViewById(R.id.points_user_two);
        pointsThree = itemView.findViewById(R.id.points_user_three);
    }

    @Override
    public void bind(ArrayList<Ranking> list) {
        if(list.size() >= 1){
            bindOne(list.get(0));
        }else{
            cleanOne();
        }
        if(list.size() >= 2){
            bindTwo(list.get(1));
        }else{
            cleanTwo();
        }
        if(list.size() >= 3){
            bindThree(list.get(2));
        }else{
            cleanThree();
        }
    }

    public void bindOne(Ranking ranking){
        if(ranking.photo != null && ranking.photo.length() > 0){
            Glide.with(imageOne).load(ranking.photo).apply(RequestOptions.circleCropTransform()).into(imageOne);
        }else{
            imageOne.setImageResource(R.drawable.avatar_three_gray);
        }
        nameOne.setText(ranking.firstname);
        pointsOne.setText(ranking.points + " puntos");
    }

    public void cleanOne(){
        imageOne.setImageResource(R.drawable.avatar_three_gray);
        nameOne.setText("1.");
        pointsOne.setText("");
    }

    public void bindTwo(Ranking ranking){
        if(ranking.photo != null && ranking.photo.length() > 0){
            Glide.with(imageTwo).load(ranking.photo).apply(RequestOptions.circleCropTransform()).into(imageTwo);
        }else{
            imageTwo.setImageResource(R.drawable.avatar_three_gray);
        }
        nameTwo.setText(ranking.firstname);
        pointsTwo.setText(ranking.points + " puntos");
    }

    public void cleanTwo(){
        imageTwo.setImageResource(R.drawable.avatar_three_gray);
        nameTwo.setText("2.");
        pointsTwo.setText("");
    }

    public void bindThree(Ranking ranking){
        if(ranking.photo != null && ranking.photo.length() > 0){
            Glide.with(imageThree).load(ranking.photo).apply(RequestOptions.circleCropTransform()).into(imageThree);
        }else{
            imageThree.setImageResource(R.drawable.avatar_three_gray);
        }
        nameThree.setText(ranking.firstname);
        pointsThree.setText(ranking.points + " puntos");
    }

    public void cleanThree(){
        imageThree.setImageResource(R.drawable.avatar_three_gray);
        nameThree.setText("3.");
        pointsThree.setText("");
    }
}
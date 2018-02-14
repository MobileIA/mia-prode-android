package com.mobileia.prode.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mobileia.prode.R;
import com.mobileia.prode.entity.Ranking;

/**
 * Created by matiascamiletti on 1/9/17.
 */

public class RankingViewHolder extends RecyclerView.ViewHolder {

    public final View view;
    public final TextView textPosition;
    public final ImageView image;
    public final TextView textUsername;
    public final TextView textPoints;
    public Ranking ranking;

    public RankingViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        textPosition = (TextView)itemView.findViewById(R.id.text_position);
        image = itemView.findViewById(R.id.image_avatar);
        textUsername = (TextView)itemView.findViewById(R.id.text_username);
        textPoints = (TextView)itemView.findViewById(R.id.text_points);
    }

    public void bind(Ranking r, int position){
        // Guardamos el item
        ranking = r;
        // Setear datos
        textPosition.setText((position+3) + ".");
        textUsername.setText(ranking.firstname);
        textPoints.setText(ranking.points + "pts");
        // cargar imagen
        if(ranking.photo != null && ranking.photo.length() > 0){
            Glide.with(image).load(ranking.photo).apply(RequestOptions.circleCropTransform()).into(image);
        }else{
            image.setImageResource(R.drawable.avatar_three_gray);
        }
    }
}
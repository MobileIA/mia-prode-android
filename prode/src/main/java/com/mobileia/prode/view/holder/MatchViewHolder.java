package com.mobileia.prode.view.holder;

import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobileia.core.helper.DateHelper;
import com.mobileia.prode.R;
import com.mobileia.prode.adapter.MatchAdapter;
import com.mobileia.prode.entity.Match;
import com.mobileia.recyclerview.holder.BaseViewHolder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by matiascamiletti on 30/8/17.
 */

public class MatchViewHolder extends BaseViewHolder<Match> implements View.OnClickListener {

    public View backgroundRed;
    public CardView cardView;
    public ImageView imageOne;
    public TextView textDate;
    public TextView titleOne;
    public TextView preditecOne;
    public TextView resultOne;
    public TextView statusMedium;
    public TextView statusTime;
    public ImageView imageTwo;
    public TextView titleTwo;
    public TextView resultTwo;
    public TextView preditecTwo;
    public TextView points;
    public CountDownTimer timer;
    public long matchDayLong = -1;
    public RelativeLayout blockView;
    public TextView pointsMax;

    public Match match;
    public boolean isBlock = false;

    protected MatchAdapter.OnClickMatchAdapter onClickListener;

    public MatchViewHolder(View itemView) {
        super(itemView);
        init(itemView);
    }

    @Override
    public void bind(Match object) {
        match = object;
        refresh();
    }

    public MatchViewHolder(View itemView, MatchAdapter.OnClickMatchAdapter listener){
        super(itemView);
        init(itemView);
        onClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if(isBlock){
            return;
        }
        if(onClickListener != null){
            onClickListener.onClick(match);
        }
    }

    protected void refresh(){
        textDate.setText(DateHelper.dateToString(match.day, "dd 'de' MMMM '-' HH:mm"));

        titleOne.setText(match.title_short_one);
        titleTwo.setText(match.title_short_two);

        if(match.photo_one != null && match.photo_one.length() > 0){
            Glide.with(imageOne).load(match.photo_one).into(imageOne);
        }else{
            imageOne.setImageResource(R.drawable.avatar_three_gray);
        }
        if(match.photo_two != null && match.photo_two.length() > 0){
            Glide.with(imageTwo).load(match.photo_two).into(imageTwo);
        }else{
            imageTwo.setImageResource(R.drawable.avatar_three_gray);
        }

        if(match.status == Match.STATUS_ENDED){
            cardView.setBackgroundResource(R.color.colorGray);
            statusMedium.setText("Fin");
        }else{
            cardView.setBackgroundResource(android.R.color.white);
            statusMedium.setText("-");
        }

        if(match.status == Match.STATUS_IN_PROGRESS){
            cardView.setBackgroundResource(R.color.prodeBlueAlpha);
            statusTime.setVisibility(View.VISIBLE);
            statusMedium.setVisibility(View.GONE);
        }else{
            statusTime.setVisibility(View.GONE);
            statusMedium.setVisibility(View.VISIBLE);
        }

        if(match.points > -1){
            points.setText("+" + match.points + "p");
            points.setVisibility(View.VISIBLE);
        }else{
            points.setVisibility(View.GONE);
        }

        if(match.predicted_one > -1){
            preditecOne.setText(match.predicted_one + "");
            preditecTwo.setText(match.predicted_two + "");
        }else{
            preditecOne.setText("-");
            preditecTwo.setText("-");
        }

        resultOne.setText(match.result_one + "");
        resultTwo.setText(match.result_two + "");

        matchDayLong = match.day.getTime();

        if(isBlock){
            blockView.setVisibility(View.VISIBLE);
        }else{
            blockView.setVisibility(View.GONE);
        }

        // Configurar el timer si fuera necesario
        processTimer();
    }

    protected void processTimer(){
        // Cancelamos si ya existe un timer
        if(timer != null){
            timer.cancel();
            timer = null;
        }
        // Verificamos si el partido esta en progreso
        if(match.status != Match.STATUS_IN_PROGRESS){
            return;
        }
        // Creamos temporizador 90 * 60 * 1000 = 5.400.000
        timer = new CountDownTimer(5400000, 1000) {
            @Override
            public void onTick(long l) {
                // Calcular los segundos que pasaron
                long seconds = (new Date().getTime() - matchDayLong) / 1000;
                // Calcular los minutos del partido
                long minutes = seconds / 60;
                //long minutes = TimeUnit.MILLISECONDS.toMinutes(uptime);
                if(minutes > 45 && minutes < 60){
                    minutes = 45;
                    seconds = 60;
                }else if(minutes >= 60){
                    minutes -= 15;
                }else if(minutes > 90){
                    minutes = 90;
                    seconds = 60;
                }

                statusTime.setText(String.format("%02d:%02d", minutes, (seconds%60)));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    protected void init(View itemView){
        itemView.setOnClickListener(this);
        backgroundRed = (View)itemView.findViewById(R.id.view_background_red);
        cardView = (CardView)itemView.findViewById(R.id.card_view);
        textDate = itemView.findViewById(R.id.text_date);
        imageOne = itemView.findViewById(R.id.image_team);
        titleOne = (TextView)itemView.findViewById(R.id.title_one);
        preditecOne = (TextView)itemView.findViewById(R.id.predicted_one);
        resultOne = (TextView)itemView.findViewById(R.id.result_one);
        statusMedium = (TextView)itemView.findViewById(R.id.status_medium);
        statusTime = (TextView)itemView.findViewById(R.id.status_time);
        imageTwo = itemView.findViewById(R.id.image_team_two);
        titleTwo = (TextView)itemView.findViewById(R.id.title_two);
        resultTwo = (TextView)itemView.findViewById(R.id.result_two);
        preditecTwo = (TextView)itemView.findViewById(R.id.predicted_two);
        points = (TextView)itemView.findViewById(R.id.text_points_two);
        blockView = itemView.findViewById(R.id.block_match);
        pointsMax = itemView.findViewById(R.id.text_points_max);
    }

}

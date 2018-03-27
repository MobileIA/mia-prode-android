package com.mobileia.prode.entity;

import io.realm.annotations.PrimaryKey;

/**
 * Created by matiascamiletti on 26/3/18.
 */

public class Standing {

    @PrimaryKey
    public int id;

    public int stage_id;

    public int team_id;

    public int played;

    public int win;

    public int draw;

    public int lost;

    public int goal_f;

    public int goal_c;

    public int goal_diff;

    public int points;

    public int position;

    public String title;

    public String title_short;

    public String photo;
}

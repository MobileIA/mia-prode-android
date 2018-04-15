package com.mobileia.prode.rest.response;

import com.mobileia.prode.entity.Match;
import com.mobileia.prode.entity.Standing;

import java.util.ArrayList;

/**
 * Created by matiascamiletti on 26/3/18.
 */

public class StandingResponse {

    public String title;

    public int has_penalty;

    public ArrayList<Standing> list = new ArrayList<Standing>();

    public ArrayList<Match> matches = new ArrayList<Match>();
}

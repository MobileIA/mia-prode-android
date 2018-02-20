package com.mobileia.prode.rest.service;

import com.mobileia.core.rest.RestBodyCall;
import com.mobileia.prode.entity.Match;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by matiascamiletti on 19/10/17.
 */

public interface MatchService {
    @GET("api/prode/match/next")
    RestBodyCall<ArrayList<Match>> next(@Query("access_token") String accessToken, @Query("group_id") int groupId);

    @GET("api/prode/match/list")
    RestBodyCall<ArrayList<Match>> list(@Query("access_token") String accessToken, @Query("group_id") int groupId);

    @GET("api/prode/match/list-previous")
    RestBodyCall<ArrayList<Match>> listPrevious(@Query("access_token") String accessToken, @Query("group_id") int groupId, @Query("match_id") int matchId, @Query("exclude") String exclude);

    @GET("api/prode/match/list-next")
    RestBodyCall<ArrayList<Match>> listNext(@Query("access_token") String accessToken, @Query("group_id") int groupId, @Query("match_id") int matchId, @Query("exclude") String exclude);
}

package com.mobileia.prode.rest.service;

import com.mobileia.core.rest.RestBodyCall;
import com.mobileia.prode.entity.Ranking;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by matiascamiletti on 15/10/17.
 */

public interface RankingService {
    @GET("ranking")
    RestBodyCall<ArrayList<Ranking>> list(@Query("access_token") String accessToken, @Query("group_id") int groupId);
}

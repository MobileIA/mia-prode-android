package com.mobileia.prode.rest.service;

import com.mobileia.core.rest.RestBodyCall;
import com.mobileia.prode.rest.response.StandingResponse;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by matiascamiletti on 26/3/18.
 */

public interface StandingService {
    @GET("api/prode/standing")
    RestBodyCall<ArrayList<StandingResponse>> list(@Query("access_token") String accessToken, @Query("tournament_id") int tournamentId);
}

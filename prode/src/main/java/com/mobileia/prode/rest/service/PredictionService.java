package com.mobileia.prode.rest.service;

import com.mobileia.core.rest.RestBodyCall;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by matiascamiletti on 22/10/17.
 */

public interface PredictionService {

    @FormUrlEncoded
    @POST("api/prode/prediction/send")
    RestBodyCall<Boolean> send(@Field("access_token") String accessToken, @Field("group_id") int groupId, @Field("match_id") int matchId, @Field("result_one") int resultOne, @Field("result_two") int resultTwo);
}

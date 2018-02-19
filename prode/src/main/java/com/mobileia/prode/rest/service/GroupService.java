package com.mobileia.prode.rest.service;

import com.google.gson.JsonObject;
import com.mobileia.core.rest.RestBodyCall;
import com.mobileia.prode.entity.Group;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by matiascamiletti on 28/1/18.
 */

public interface GroupService {

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("api/prode/group/add")
    RestBodyCall<Group> add(@Body JsonObject params);

    @GET("api/prode/group/index")
    RestBodyCall<ArrayList<Group>> list(@Query("access_token") String accessToken);
}

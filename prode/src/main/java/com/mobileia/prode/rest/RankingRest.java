package com.mobileia.prode.rest;

import android.app.Activity;

import com.mobileia.core.rest.RestBody;
import com.mobileia.core.rest.RestBodyCall;
import com.mobileia.prode.entity.Ranking;
import com.mobileia.prode.rest.service.RankingService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matiascamiletti on 17/10/17.
 */

public class RankingRest extends BaseRest {

    public RankingRest(Activity context) {
        super(context);
    }

    /**
     * Se encarga de obtener el ranking
     * @param callback
     */
    public void fetchList(int groupId, final OnFetchComplete callback){
        // Creamos el servicio
        RankingService service = createService(RankingService.class);
        // Creamos call
        RestBodyCall<ArrayList<Ranking>> call = service.list(getAccessToken(), groupId);
        // Ejecutamos call
        call.enqueue(new Callback<RestBody<ArrayList<Ranking>>>() {
            @Override
            public void onResponse(Call<RestBody<ArrayList<Ranking>>> call, Response<RestBody<ArrayList<Ranking>>> response) {
                if(!response.isSuccessful() || !response.body().success){
                    callback.onError();
                    return;
                }
                callback.onSuccess(response.body().response);
            }

            @Override
            public void onFailure(Call<RestBody<ArrayList<Ranking>>> call, Throwable t) {
                callback.onError();
            }
        });
    }

    /**
     * Interface para cuando se obtiene el ranking
     */
    public interface OnFetchComplete{
        void onSuccess(ArrayList<Ranking> list);
        void onError();
    }
}

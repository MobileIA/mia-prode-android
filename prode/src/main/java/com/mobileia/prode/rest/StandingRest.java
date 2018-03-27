package com.mobileia.prode.rest;

import android.content.Context;

import com.mobileia.core.rest.RestBody;
import com.mobileia.core.rest.RestBodyCall;
import com.mobileia.prode.rest.response.StandingResponse;
import com.mobileia.prode.rest.service.StandingService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matiascamiletti on 26/3/18.
 */

public class StandingRest extends BaseRest {

    /**
     * Constructor
     *
     * @param context
     */
    public StandingRest(Context context) {
        super(context);
    }

    /**
     * Obtener tabla de posiciones del torneo
     * @param tournamentId
     * @param callback
     */
    public void fetchTable(int tournamentId, final OnFetchComplete callback){
        // Creamos el servicio
        StandingService service = createService(StandingService.class);
        // Creamos call
        RestBodyCall<ArrayList<StandingResponse>> call = service.list(getAccessToken(), tournamentId);
        // Ejecutamos
        call.enqueue(new Callback<RestBody<ArrayList<StandingResponse>>>() {
            @Override
            public void onResponse(Call<RestBody<ArrayList<StandingResponse>>> call, Response<RestBody<ArrayList<StandingResponse>>> response) {
                if(!response.isSuccessful() || !response.body().success){
                    callback.onError();
                    return;
                }
                callback.onSuccess(response.body().response);
            }

            @Override
            public void onFailure(Call<RestBody<ArrayList<StandingResponse>>> call, Throwable t) {
                callback.onError();
            }
        });
    }

    public interface OnFetchComplete{
        void onSuccess(ArrayList<StandingResponse> list);
        void onError();
    }
}

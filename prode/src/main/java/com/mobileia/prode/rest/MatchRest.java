package com.mobileia.prode.rest;

import android.app.Activity;

import com.mobileia.core.rest.RestBody;
import com.mobileia.core.rest.RestBodyCall;
import com.mobileia.prode.entity.Match;
import com.mobileia.prode.rest.service.MatchService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matiascamiletti on 19/10/17.
 */

public class MatchRest extends BaseRest {

    public MatchRest(Activity context) {
        super(context);
    }

    /**
     * Obtiene el listado de partidos del momento
     * @param callback
     */
    public void fetchList(int groupId, OnFetchComplete callback){
        // Creamos el servicio
        MatchService service = createService(MatchService.class);
        // Creamos call
        RestBodyCall<ArrayList<Match>> call = service.list(getAccessToken(), groupId);
        // Ejecutamos call
        execute(call, callback);
    }
    /**
     * Obtiene el listado de partidos anteriores
     * @param callback
     */
    public void fetchListPrevious(int groupId, int matchId, String exclude, OnFetchComplete callback){
        // Creamos el servicio
        MatchService service = createService(MatchService.class);
        // Creamos call
        RestBodyCall<ArrayList<Match>> call = service.listPrevious(getAccessToken(), groupId, matchId, exclude);
        // Ejecutamos call
        execute(call, callback);
    }
    /**
     * Obtiene el listado de partidos siguientes
     * @param callback
     */
    public void fetchListNext(int groupId, int matchId, String exclude, OnFetchComplete callback){
        // Creamos el servicio
        MatchService service = createService(MatchService.class);
        // Creamos call
        RestBodyCall<ArrayList<Match>> call = service.listNext(getAccessToken(), groupId, matchId, exclude);
        // Ejecutamos call
        execute(call, callback);
    }
    /**
     * Se encarga de obtener los proximos partidos
     * @param callback
     */
    public void fetchNext(int groupId, OnFetchComplete callback){
        // Creamos el servicio
        MatchService service = createService(MatchService.class);
        // Creamos call
        RestBodyCall<ArrayList<Match>> call = service.next(getAccessToken(), groupId);
        // Ejecutamos call
        execute(call, callback);
    }

    /**
     * Funcion privada que ejecuta la call, como siempre se devuelve un listado de partidos
     * @param call
     * @param callback
     */
    protected void execute(RestBodyCall<ArrayList<Match>> call, final OnFetchComplete callback){
        call.enqueue(new Callback<RestBody<ArrayList<Match>>>() {
            @Override
            public void onResponse(Call<RestBody<ArrayList<Match>>> call, Response<RestBody<ArrayList<Match>>> response) {
                if(!response.isSuccessful() || !response.body().success){
                    callback.onError();
                    return;
                }
                callback.onSuccess(response.body().response);
            }

            @Override
            public void onFailure(Call<RestBody<ArrayList<Match>>> call, Throwable t) {
                callback.onError();
            }
        });
    }

    public interface OnFetchComplete{
        void onSuccess(ArrayList<Match> list);
        void onError();
    }
}

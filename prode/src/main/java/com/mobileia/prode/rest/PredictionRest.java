package com.mobileia.prode.rest;

import android.app.Activity;

import com.mobileia.core.rest.RestBody;
import com.mobileia.core.rest.RestBodyCall;
import com.mobileia.prode.rest.service.PredictionService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matiascamiletti on 22/10/17.
 */

public class PredictionRest extends BaseRest {

    public PredictionRest(Activity context) {
        super(context);
    }

    /**
     * Se encarga de enviar la prediccion al servidor
     * @param callback
     */
    public void send(int groupId, int matchId, int resultOne, int resultTwo, final OnSendComplete callback){
        // Creamos el servicio
        PredictionService service = createService(PredictionService.class);
        // Creamos call
        RestBodyCall<Boolean> call = service.send(getAccessToken(), groupId, matchId, resultOne, resultTwo);
        // Ejecutamos call
        call.enqueue(new Callback<RestBody<Boolean>>() {
            @Override
            public void onResponse(Call<RestBody<Boolean>> call, Response<RestBody<Boolean>> response) {
                if(!response.isSuccessful() || !response.body().success){
                    callback.onError();
                    return;
                }
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<RestBody<Boolean>> call, Throwable t) {
                callback.onError();
            }
        });
    }

    public interface OnSendComplete{
        void onSuccess();
        void onError();
    }
}

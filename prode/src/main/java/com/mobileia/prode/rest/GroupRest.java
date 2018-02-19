package com.mobileia.prode.rest;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mobileia.contacts.entity.Person;
import com.mobileia.core.rest.RestBody;
import com.mobileia.core.rest.RestBodyCall;
import com.mobileia.facebook.entity.Profile;
import com.mobileia.prode.entity.Group;
import com.mobileia.prode.realm.GroupRealm;
import com.mobileia.prode.rest.service.GroupService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matiascamiletti on 28/1/18.
 */

public class GroupRest extends BaseRest {

    public GroupRest(Context context) {
        super(context);
    }

    public void add(String title, int tournamentId, ArrayList<Profile> persons, final OnNewComplete callback){
        // Obtenemos servicio
        GroupService service = createService(GroupService.class);
        // Generamos body
        JsonObject params = new JsonObject();
        params.addProperty("access_token", getAccessToken());
        params.addProperty("title", title);
        params.addProperty("tournament_id", tournamentId);
        // Recorres usuarios agregados
        JsonArray contacts = new JsonArray();
        for(Profile p : persons){
            JsonObject at = new JsonObject();
            at.addProperty("id", p.id);
            at.addProperty("facebook", p.id);
            at.addProperty("firstname", p.firstname);
            at.addProperty("lastname", p.lastname);
            at.addProperty("photo", p.picture);
            contacts.add(at);
        }
        params.add("contacts", contacts);
        // Generamos request
        Call<RestBody<Group>> call = service.add(params);
        // Ejecutamos request
        call.enqueue(new Callback<RestBody<Group>>() {
            @Override
            public void onResponse(Call<RestBody<Group>> call, Response<RestBody<Group>> response) {
                if(!response.isSuccessful() || !response.body().success){
                    callback.onError();
                    return;
                }
                callback.onSuccess(response.body().response);
            }

            @Override
            public void onFailure(Call<RestBody<Group>> call, Throwable t) {
                callback.onError();
            }
        });
    }

    /**
     * FUncino que se encarga de sincronizar los grupos del usuario
     */
    public void syncGroups(final OnSyncComplete callback){
        // Obtenemos servicio
        GroupService service = createService(GroupService.class);
        // Creamos la call
        RestBodyCall<ArrayList<Group>> call = service.list(getAccessToken());
        // Ejecutamos la call
        call.enqueue(new Callback<RestBody<ArrayList<Group>>>() {
            @Override
            public void onResponse(Call<RestBody<ArrayList<Group>>> call, Response<RestBody<ArrayList<Group>>> response) {
                if(!response.isSuccessful() || !response.body().success){
                    callback.onSuccess(new ArrayList<Group>());
                    return;
                }
                // Guardar todos los grupos
                new GroupRealm().saveAll(response.body().response);

                callback.onSuccess(response.body().response);
            }

            @Override
            public void onFailure(Call<RestBody<ArrayList<Group>>> call, Throwable t) {
                callback.onSuccess(new ArrayList<Group>());
            }
        });
    }

    public interface OnNewComplete{
        void onSuccess(Group group);
        void onError();
    }

    public interface OnSyncComplete{
        void onSuccess(ArrayList<Group> list);
    }
}
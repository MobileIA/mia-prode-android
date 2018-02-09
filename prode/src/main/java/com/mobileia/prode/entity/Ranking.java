package com.mobileia.prode.entity;

import com.google.gson.JsonObject;

import java.util.Map;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by matiascamiletti on 20/12/16.
 */

public class Ranking extends RealmObject {

    @PrimaryKey
    public int id;

    public int tournament_id;

    public int user_id;

    public int points;

    public String firstname;

    public String photo;

    public static Ranking fromMap(Map<String, String> data){
        Ranking entity = new Ranking();
        entity.id = Integer.valueOf(data.get("id"));
        entity.tournament_id = Integer.valueOf(data.get("tournament_id"));
        entity.user_id = Integer.valueOf(data.get("user_id"));
        entity.points = Integer.valueOf(data.get("points"));

        return entity;
    }
    
    public static Ranking fromJson(JsonObject json){
        Ranking entity = new Ranking();
        entity.id = json.get("id").getAsInt();
        entity.tournament_id = json.get("tournament_id").getAsInt();
        entity.user_id = json.get("user_id").getAsInt();
        entity.points = json.get("points").getAsInt();
        entity.firstname = json.get("firstname").getAsString();
        entity.photo = json.get("photo").getAsString();

        return entity;
    }
}
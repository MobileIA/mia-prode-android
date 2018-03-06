package com.mobileia.prode.entity;

import com.google.gson.JsonObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by matiascamiletti on 25/1/18.
 */

public class Group extends RealmObject {

    @PrimaryKey
    public int id;

    public int tournament_id;

    public int user_id;

    public String title;

    public String start_date;

    public RealmList<Friend> contacts = new RealmList<Friend>();

    public int is_admin;

    public static Group fromJson(JsonObject json){
        Group entity = new Group();
        entity.id = json.get("id").getAsInt();
        entity.tournament_id = json.get("tournament_id").getAsInt();
        entity.user_id = json.get("user_id").getAsInt();
        entity.title = json.get("title").getAsString();

        return entity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTournamentId() {
        return tournament_id;
    }

    public void setTournamentId(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return start_date;
    }

    public void setStartDate(String start_date) {
        this.start_date = start_date;
    }

    public RealmList<Friend> getContacts() {
        return contacts;
    }

    public void setContacts(RealmList<Friend> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return title;
    }
}

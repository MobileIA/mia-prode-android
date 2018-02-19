package com.mobileia.prode.entity;

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

    @Override
    public String toString() {
        return title;
    }
}

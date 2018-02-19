package com.mobileia.prode.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by matiascamiletti on 25/1/18.
 */

public class Friend extends RealmObject {

    @PrimaryKey
    public long id;

    public int group_id;

    public int user_id;

    public String email;

    public String firstname;

    public String lastname;

    public String photo;

    public String phone;
}

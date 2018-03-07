package com.mobileia.prode.entity;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by matiascamiletti on 25/1/18.
 */

public class Friend extends RealmObject implements Parcelable {

    @PrimaryKey
    public long id;

    public int group_id;

    public int user_id;

    public String email;
    /**
     * Representa el nombre de la agenda del usuario, este se utiliza cuando el usuario todavia no tiene cuenta en ShowProde
     */
    public String username;

    public String firstname;

    public String lastname;

    public String photo;

    public String phone;

    public String facebook_id;

    public Friend(){}

    public Friend(Parcel in){
        id = in.readLong();
        group_id = in.readInt();
        user_id = in.readInt();
        email = in.readString();
        username = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        photo = in.readString();
        phone = in.readString();
        facebook_id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeInt(group_id);
        parcel.writeInt(user_id);
        parcel.writeString(email);
        parcel.writeString(username);
        parcel.writeString(firstname);
        parcel.writeString(lastname);
        parcel.writeString(photo);
        parcel.writeString(phone);
        parcel.writeString(facebook_id);
    }

    public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {

        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}

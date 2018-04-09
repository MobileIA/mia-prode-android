package com.mobileia.prode.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonParser;

import java.util.Date;
import java.util.Map;
import com.google.gson.JsonObject;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by matiascamiletti on 20/12/16.
 */

public class Match extends RealmObject implements Parcelable {

    public static final int STATUS_PENDING = 0;
    public static final int STATUS_IN_PROGRESS = 1;
    public static final int STATUS_ENDED = 2;
    public static final int STATUS_IN_PENALTY = 3;

    @PrimaryKey
    public int id;

    public int stage_id;

    public Date day;

    public String title_one;

    public String title_short_one;

    public String photo_one;

    public int team_one_id;

    public int result_one;

    public int penalty_one;

    public int predicted_one = -1;

    public int predicted_penalty_one = -1;

    public String title_two;

    public String title_short_two;

    public String photo_two;

    public int team_two_id;

    public int result_two;

    public int penalty_two;

    public int predicted_two = -1;

    public int predicted_penalty_two = -1;

    public int points = -1;

    public int status;

    public int has_penalty = 0;

    public Match(){}

    public Match(Parcel in){
        id = in.readInt();
        stage_id = in.readInt();
        day = new Date(in.readLong());
        title_one = in.readString();
        title_short_one = in.readString();
        photo_one = in.readString();
        team_one_id = in.readInt();
        result_one = in.readInt();
        predicted_one = in.readInt();
        predicted_penalty_one = in.readInt();
        title_two = in.readString();
        title_short_two = in.readString();
        photo_two = in.readString();
        team_two_id = in.readInt();
        result_two = in.readInt();
        predicted_two = in.readInt();
        predicted_penalty_two = in.readInt();
        points = in.readInt();
        status = in.readInt();
        has_penalty = in.readInt();
        penalty_one = in.readInt();
        penalty_two = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(stage_id);
        parcel.writeLong(day.getTime());
        parcel.writeString(title_one);
        parcel.writeString(title_short_one);
        parcel.writeString(photo_one);
        parcel.writeInt(team_one_id);
        parcel.writeInt(result_one);
        parcel.writeInt(predicted_one);
        parcel.writeInt(predicted_penalty_one);
        parcel.writeString(title_two);
        parcel.writeString(title_short_two);
        parcel.writeString(photo_two);
        parcel.writeInt(team_two_id);
        parcel.writeInt(result_two);
        parcel.writeInt(predicted_two);
        parcel.writeInt(predicted_penalty_two);
        parcel.writeInt(points);
        parcel.writeInt(status);
        parcel.writeInt(has_penalty);
        parcel.writeInt(penalty_one);
        parcel.writeInt(penalty_two);
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {

        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    public static Match fromMap(Map<String, String> data){
        Match entity = new Match();
        entity.id = Integer.valueOf(data.get("id"));
        entity.stage_id = Integer.valueOf(data.get("stage_id"));
        //entity.day = DateHelper.stringMysqlToDate(data.get("day"), true);
        entity.team_one_id = Integer.valueOf(data.get("team_one_id"));
        entity.result_one = Integer.valueOf(data.get("result_one"));
        entity.team_two_id = Integer.valueOf(data.get("team_two_id"));
        entity.result_two = Integer.valueOf(data.get("result_two"));
        entity.status = Integer.valueOf(data.get("status"));

        return entity;
    }
    
    public static Match fromJson(JsonObject json){
        Match entity = new Match();
        entity.id = json.get("id").getAsInt();
        entity.stage_id = json.get("stage_id").getAsInt();
        //entity.day = DateHelper.stringMysqlToDate(json.get("day").getAsString(), true);
        entity.team_one_id = json.get("team_one_id").getAsInt();
        try {
            entity.result_one = json.get("result_one").getAsInt();
        }catch (Exception ex){
            entity.result_one = 0;
        }
        entity.team_two_id = json.get("team_two_id").getAsInt();
        try {
            entity.result_two = json.get("result_two").getAsInt();
        }catch (Exception ex){
            entity.result_two = 0;
        }
        try {
            entity.penalty_one = json.get("penalty_one").getAsInt();
        }catch (Exception ex){
            entity.penalty_one = 0;
        }
        try {
            entity.penalty_two = json.get("penalty_two").getAsInt();
        }catch (Exception ex){
            entity.penalty_two = 0;
        }
        entity.status = json.get("status").getAsInt();

        return entity;
    }

}
package com.mobileia.prode.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by matiascamiletti on 1/3/18.
 */

public class Config extends RealmObject {
    @PrimaryKey
    public int id = 1;

    public boolean notification_matches_now = true;

    public boolean notification_prediction_correct = true;

    public int first_onboarding_home = 1;

    public int first_onboarding_trivia = 1;

    public int first_onboarding_prode = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNotificationMatchesNow() {
        return notification_matches_now;
    }

    public void setNotificationMatchesNow(boolean notification_matches_now) {
        this.notification_matches_now = notification_matches_now;
    }

    public boolean isNotificationPredictionCorrect() {
        return notification_prediction_correct;
    }

    public void setNotificationPredictionCorrect(boolean notification_prediction_correct) {
        this.notification_prediction_correct = notification_prediction_correct;
    }

    public int getFirstOnboardingHome(){ return this.first_onboarding_home; }

    public int getFirstOnboardingTrivia(){ return this.first_onboarding_trivia; }

    public int getFirstOnboardingProde(){ return this.first_onboarding_prode; }
}

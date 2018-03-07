package com.mobileia.prode.firebase;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by matiascamiletti on 6/3/18.
 */

public class ProdeRegisterFirebase {

    public static void registerLive(){
        FirebaseMessaging.getInstance().subscribeToTopic("updateMatch");
    }

    public static void registerMatchesNow(){
        FirebaseMessaging.getInstance().subscribeToTopic("matchesNow");
    }

    public static void registerPredictionCorrect(){
        FirebaseMessaging.getInstance().subscribeToTopic("PredictionCorrect");
    }

    public static void unregisterLive(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic("updateMatch");
    }

    public static void unregisterMatchesNow(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic("matchesNow");
    }

    public static void unregisterPredictionCorrect(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic("PredictionCorrect");
    }

    public static void registerAllUser(){
        FirebaseMessaging.getInstance().subscribeToTopic("allusers");
    }

    public static void unregisterAllUser(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic("allusers");
    }
}

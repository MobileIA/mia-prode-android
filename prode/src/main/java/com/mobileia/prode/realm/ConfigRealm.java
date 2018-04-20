package com.mobileia.prode.realm;

import com.mobileia.prode.entity.Config;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by matiascamiletti on 1/3/18.
 */

public class ConfigRealm extends BaseRealm {

    public void activeNotificationMatchesNow() {
        changeNotificationMatchesNow(true);
    }

    public void inactiveNotificationMatchesNow() {
        changeNotificationMatchesNow(false);
    }

    public void changeFirstOnboardingHome(int state){
        // Obtenemos condig
        Config config = fetchConfig();
        // Obtenemos Realm
        Realm realm = getInstance();
        // Iniciamos transaccion
        realm.beginTransaction();
        // Actualizamos
        config.first_onboarding_home = state;
        // Comitiamos transaccion
        realm.commitTransaction();
    }

    public void changeFirstOnboardingTrivia(int state){
        // Obtenemos condig
        Config config = fetchConfig();
        // Obtenemos Realm
        Realm realm = getInstance();
        // Iniciamos transaccion
        realm.beginTransaction();
        // Actualizamos
        config.first_onboarding_trivia = state;
        // Comitiamos transaccion
        realm.commitTransaction();
    }

    public void changeFirstOnboardingProde(int state){
        // Obtenemos condig
        Config config = fetchConfig();
        // Obtenemos Realm
        Realm realm = getInstance();
        // Iniciamos transaccion
        realm.beginTransaction();
        // Actualizamos
        config.first_onboarding_prode = state;
        // Comitiamos transaccion
        realm.commitTransaction();
    }

    public void changeNotificationMatchesNow(boolean state){
        // Obtenemos condig
        Config config = fetchConfig();
        // Obtenemos Realm
        Realm realm = getInstance();
        // Iniciamos transaccion
        realm.beginTransaction();
        // Actualizamos
        config.notification_matches_now = state;
        // Comitiamos transaccion
        realm.commitTransaction();
    }

    public boolean isExist(){
        // Obtenemos Realm
        Realm realm = getInstance();
        // Creamos Query
        RealmQuery<Config> query = realm.where(Config.class);
        // Obtenemos todos los registros
        Config config = query.findFirst();
        // Verificamos si es nulo
        if(config == null){
            return false;
        }
        // Devolvemos config
        return true;
    }

    /**
     * Obtiene el objeto de configuracion
     * @return
     */
    public Config fetchConfig(){
        // Obtenemos Realm
        Realm realm = getInstance();
        // Creamos Query
        RealmQuery<Config> query = realm.where(Config.class);
        // Obtenemos todos los registros
        Config config = query.findFirst();
        // Verificamos si es nulo
        if(config == null){
            return create();
        }
        // Devolvemos config
        return config;
    }

    /**
     * Crea el unico objeto requerido
     * @return
     */
    public Config create(){
        // Obtenemos condig
        Config config = new Config();
        // Obtenemos Realm
        Realm realm = getInstance();
        // Iniciamos transaccion
        realm.beginTransaction();
        // Actualizamos
        realm.copyToRealmOrUpdate(config);
        // Comitiamos transaccion
        realm.commitTransaction();
        // Devolvemos config
        return config;
    }
}

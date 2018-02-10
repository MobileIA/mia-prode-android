package com.mobileia.prode.realm;

import com.mobileia.core.Mobileia;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Created by matiascamiletti on 22/11/17.
 */

public class BaseRealm {
    /**
     * Obtiene instancia de realm con la configuracion
     * @return
     */
    public Realm getInstance(){
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("prode.realm." + Mobileia.getInstance().getAppId())
                .modules(new ProdeModule())
                .build();
        return Realm.getInstance(config);
    }

    /**
     * Guardamos un item en la db interna
     * @param entity
     */
    public void save(RealmObject entity){
        // Obtenemos Realm
        Realm realm = getInstance();
        // Iniciamos transaccion
        realm.beginTransaction();
        // Actualizamos
        realm.copyToRealmOrUpdate(entity);
        // Comitiamos transaccion
        realm.commitTransaction();
    }

    /**
     * Eliminar todos los datos
     */
    public void deleteAll(){
        // Obtain a Realm instance
        Realm realm = getInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    /**
     * Elimina un objeto de la db interna
     * @param entity
     */
    /*public void delete(RealmObject entity){
        // Obtain a Realm instance
        Realm realm = getInstance();
        // Iniciamos transaccion
        realm.beginTransaction();
        // Eliminamos el objeto
        entity.deleteFromRealm();
        // Guardamos los cambios
        realm.commitTransaction();
    }*/
}

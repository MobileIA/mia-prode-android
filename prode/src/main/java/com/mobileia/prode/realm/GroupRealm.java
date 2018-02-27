package com.mobileia.prode.realm;

import com.mobileia.prode.entity.Friend;
import com.mobileia.prode.entity.Group;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by matiascamiletti on 25/1/18.
 */

public class GroupRealm extends BaseRealm {
    /**
     * Devuelve todos los grupos creados
     * @return
     */
    public RealmResults<Group> fetchAll(){
        // Obtenemos Realm
        Realm realm = getInstance();
        // Creamos Query
        return realm.where(Group.class).findAll();
    }

    /**
     * Devuelve todos los grupos guardados sincronizados
     * @return
     */
    public RealmResults<Group> fetchAllAsync(){
        // Obtenemos Realm
        Realm realm = getInstance();
        // Creamos Query
        return realm.where(Group.class).findAllAsync();
    }

    /**
     * Devuelve el grupo a traves de su IDs
     * @param groupId
     * @return
     */
    public Group fetchById(int groupId){
        // Obtenemos Realm
        Realm realm = getInstance();
        // Creamos Query
        return realm.where(Group.class).equalTo("id", groupId).findFirst();
    }

    /**
     * Elimina un grupo a traves de su ID
     * @param groupId
     */
    public void removeById(int groupId){
        // Obtenemos Realm
        Realm realm = getInstance();
        // Obtenemos grupo
        Group group = realm.where(Group.class).equalTo("id", groupId).findFirst();
        // Ejecurtamos transaccion
        realm.beginTransaction();
        // Eliminamos
        group.deleteFromRealm();
        // terminamos transaccion
        realm.commitTransaction();
    }

    public RealmResults<Friend> fetchAllUsers(int groupId){
        // Obtenemos Realm
        Realm realm = getInstance();
        // Creamos Query
        return realm.where(Friend.class).equalTo("group_id", groupId).findAll();
    }

    /**
     * Guarda el array de los grupos
     * @param list
     */
    public void saveAll(final ArrayList<Group> list){
        // Obtenemos Realm
        Realm realm = getInstance();
        // Ejecutamos transaccion
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(list);
            }
        });
    }
}

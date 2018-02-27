package com.mobileia.prode.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonParser;
import com.mobileia.core.helper.NotificationHelper;
import com.mobileia.prode.R;
import com.mobileia.prode.entity.Group;
import com.mobileia.prode.entity.Match;
import com.mobileia.prode.helper.ProdeLocalBroadcastHelper;
import com.mobileia.prode.realm.GroupRealm;
import com.mobileia.prode.rest.GroupRest;

import java.util.ArrayList;

/**
 * Created by matiascamiletti on 27/2/18.
 */

public class ProdeMessagingService extends FirebaseMessagingService {

    public static final int TYPE_CHANGE_MATCH = 1;
    public static final int TYPE_MATCHES_NOW = 2;
    public static final int TYPE_PREDICTION_CORRECT = 3;
    public static final int TYPE_NEW_GROUP = 4;
    public static final int TYPE_REMOVED_GROUP = 5;
    public static final int TYPE_LEAVE_GROUP = 6;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Verificamos que se haya enviado informacion
        if (remoteMessage.getData().size() == 0) {
            return;
        }

        int pushType = Integer.valueOf(remoteMessage.getData().get("push_type"));

        switch (pushType){
            case TYPE_CHANGE_MATCH:
                onUpdateMatch(remoteMessage);
                break;
            case TYPE_MATCHES_NOW:
                onMatchesNow(remoteMessage);
                break;
            case TYPE_PREDICTION_CORRECT:
                onPredictionCorrect(remoteMessage);
                break;
            case TYPE_NEW_GROUP:
                onNewGroup(remoteMessage);
                break;
            case TYPE_REMOVED_GROUP:
                onRemoveGroup(remoteMessage);
                break;
            case TYPE_LEAVE_GROUP:
                onLeaveGroup(remoteMessage);
                break;
        }
    }

    protected void onUpdateMatch(RemoteMessage remoteMessage){
        // Convertir en Entidad
        Match match = Match.fromJson(new JsonParser().parse(remoteMessage.getData().get("match")).getAsJsonObject());
        // Enviamos por servicio
        ProdeLocalBroadcastHelper.sendUpdateMatch(this, match);
    }

    protected void onMatchesNow(RemoteMessage remoteMessage){
        // Creamos notificacion
        new NotificationHelper(this).send(R.string.notification_matches_now_title, R.string.notification_matches_now_message);
    }

    protected void onPredictionCorrect(RemoteMessage remoteMessage){
        // Creamos notificacion
        new NotificationHelper(this).send(R.string.notification_prediction_correct_title, R.string.notification_prediction_correct_message);
    }

    protected void onNewGroup(RemoteMessage remoteMessage){
        // Creamos notificacion
        new NotificationHelper(this).send(R.string.notification_new_group_title, R.string.notification_new_group_message);
        // Sincronizar grupos
        new GroupRest(getApplicationContext()).syncGroups(new GroupRest.OnSyncComplete() {
            @Override
            public void onSuccess(ArrayList<Group> list) {
                // Informar que se creo un nuevo grupo
                ProdeLocalBroadcastHelper.sendNewGroup(ProdeMessagingService.this);
                // Guardamos ID del nuevo grupo
                //new ConfigRealm().updateCurrentGroup(list.get(list.size()-1).id);
            }
        });
    }

    protected void onRemoveGroup(RemoteMessage remoteMessage){
        // Obtener ID del grupo
        int groupId = Integer.valueOf(remoteMessage.getData().get("group_id"));
        // Creamos notificacion
        new NotificationHelper(this).send(R.string.notification_remove_group_title, R.string.notification_remove_group_message);
        // Eliminar grupo de la DB
        new GroupRealm().removeById(groupId);
    }

    protected void onLeaveGroup(RemoteMessage remoteMessage){
        // Convertir en Entidad
        Group group = Group.fromJson(new JsonParser().parse(remoteMessage.getData().get("group")).getAsJsonObject());
        // Configurar mensaje de la notificacion
        String content = getApplicationContext().getResources().getString(R.string.notification_leave_group_message);
        content = content.replace("--user--", remoteMessage.getData().get("firstname"));
        content = content.replace("--group--", group.title);
        // Creamos notificacion
        new NotificationHelper(this).send(getApplicationContext().getResources().getString(R.string.notification_leave_group_title), content);
        // Sincronizar grupos
        new GroupRest(getApplicationContext()).syncGroups(new GroupRest.OnSyncComplete() {
            @Override
            public void onSuccess(ArrayList<Group> list) {
            }
        });
    }

}

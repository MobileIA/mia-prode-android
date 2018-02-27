package com.mobileia.prode.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mobileia.core.helper.LocalBroadcastHelper;
import com.mobileia.prode.entity.Match;

/**
 * Created by matiascamiletti on 27/2/18.
 */

public class ProdeLocalBroadcastHelper extends LocalBroadcastHelper {
    /**
     * Action para informar que un partido se actualizo
     */
    public static final String ACTION_UPDATE_MATCH = "update_match";
    /**
     * Action para informar de un nuevo grupo creado o invitado
     */
    public static final String ACTION_NEW_GROUP = "new_group";

    /**
     * Informa que se actualizo un partido
     * @param context
     * @param match
     */
    public static void sendUpdateMatch(Context context, Match match){
        Intent intent = new Intent(ACTION_UPDATE_MATCH);
        intent.putExtra("match", match);
        send(context, intent);
    }

    /**
     * Se registra para recibir cuando se actualiza un partido
     * @param context
     * @param broadcastReceiver
     */
    public static void startUpdateMatch(Context context, BroadcastReceiver broadcastReceiver){
        start(context, broadcastReceiver, ACTION_UPDATE_MATCH);
    }

    /**
     * Informa que el usuario fue invitado a un nuevo grupo
     * @param context
     */
    public static void sendNewGroup(Context context){
        Intent intent = new Intent(ACTION_NEW_GROUP);
        send(context, intent);
    }

    /**
     * Se registra para recibir cuando se ha invitado a un nuevo grupo
     * @param context
     * @param broadcastReceiver
     */
    public static void startNewGroup(Context context, BroadcastReceiver broadcastReceiver){
        start(context, broadcastReceiver, ACTION_NEW_GROUP);
    }

}

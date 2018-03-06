package com.mobileia.prode.view.holder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mobileia.core.dialog.ConfirmDialog;
import com.mobileia.prode.R;
import com.mobileia.prode.activity.GroupSettingsActivity;
import com.mobileia.prode.entity.Group;
import com.mobileia.prode.realm.ConfigRealm;
import com.mobileia.prode.realm.GroupRealm;
import com.mobileia.prode.rest.GroupRest;

/**
 * Created by matiascamiletti on 6/3/18.
 */

public class LeaveButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected Activity mActivity;

    /**
     * Almacena el grupo que se estan mostrando la configuracion
     */
    protected Group mGroup;

    protected OnLeaveClickListener mListener;

    public LeaveButtonViewHolder(View itemView, Activity activity, Group group, OnLeaveClickListener listener) {
        super(itemView);
        mActivity = activity;
        mGroup = group;
        mListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Abrir modal para confirmar accion
        new ConfirmDialog(mActivity, R.string.group_setting_dialog_confirm_leave_title, R.string.group_setting_dialog_confirm_leave_content).show(new ConfirmDialog.OnConfirmDialog() {
            @Override
            public void onPositive() {
                new GroupRest(mActivity).leave(mGroup.id, new GroupRest.OnRemoveComplete() {
                    @Override
                    public void onSuccess(boolean success) {
                        if(success){
                            // Eliminar grupo
                            new GroupRealm().removeById(mGroup.id);
                            // Eliminar seleccion
                            //new ConfigRealm().updateCurrentGroup(-1);
                            // Llamamos al listener
                            if(mListener != null){
                                mListener.onLeaveGroup();
                            }
                        }
                    }
                });
            }
        });
    }

    public interface OnLeaveClickListener{
        void onLeaveGroup();
    }
}

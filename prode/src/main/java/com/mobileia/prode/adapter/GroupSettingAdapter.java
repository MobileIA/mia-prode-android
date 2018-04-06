package com.mobileia.prode.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mobileia.prode.R;
import com.mobileia.prode.entity.Friend;
import com.mobileia.prode.entity.Group;
import com.mobileia.prode.realm.GroupRealm;
import com.mobileia.prode.rest.GroupRest;
import com.mobileia.prode.view.holder.AddFriendViewHolder;
import com.mobileia.prode.view.holder.GroupUserViewHolder;
import com.mobileia.prode.view.holder.LeaveButtonViewHolder;
import com.mobileia.prode.view.holder.TotalFriendsViewHolder;

import java.util.ArrayList;

/**
 * Created by matiascamiletti on 6/3/18.
 */

public class GroupSettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AddFriendViewHolder.OnAddFriendClickListener, LeaveButtonViewHolder.OnLeaveClickListener, GroupUserViewHolder.OnOptionsGroup {

    public static final int TYPE_TOTAL_FRIENDS = 0;
    public static final int TYPE_ADD_FRIEND = 1;
    public static final int TYPE_FRIEND = 2;
    public static final int TYPE_LEAVE_BUTTON = 3;
    /**
     * Almacena actividad
     */
    protected Activity mActivity;
    /**
     * Almacena el listado de contactos
     */
    protected ArrayList mFriends = new ArrayList<>();
    /**
     * Listener para ejecutar acciones requeridas
     */
    protected OnGroupSettingAdapterListener mListener;
    /**
     * Almacena el grupo que se estan mostrando la configuracion
     */
    protected Group mGroup;
    /**
     * Valida si puede agregar participantes o no.
     */
    protected boolean mHasPermissionAdd = true;

    /**
     * constructor
     * @param activity
     */
    public GroupSettingAdapter(Activity activity, Group group){
        mActivity = activity;
        mGroup = group;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TOTAL_FRIENDS:
                return new TotalFriendsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_total_friends, parent, false));
            case TYPE_ADD_FRIEND:
                return new AddFriendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_friend, parent, false), this);
            case TYPE_FRIEND:
                return new GroupUserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_group_contact, parent, false), mGroup, this);
            case TYPE_LEAVE_BUTTON:
                return new LeaveButtonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave_button, parent, false), mActivity, mGroup, this);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_TOTAL_FRIENDS:
                onBindTotalFriendViewHolder((TotalFriendsViewHolder)holder);
                break;
            case TYPE_FRIEND:
                int positionUser = 0;
                if(mHasPermissionAdd){
                    positionUser = position-2;
                }else{
                    positionUser = position-1;
                }
                onBindContactViewHolder((GroupUserViewHolder)holder, positionUser);
                break;
        }
    }

    protected void onBindTotalFriendViewHolder(TotalFriendsViewHolder holder){
        holder.bind(mFriends.size());
    }

    protected void onBindContactViewHolder(GroupUserViewHolder holder, int position){
        Object item = mFriends.get(position);
        if(item instanceof Friend){
            holder.bind((Friend)item);
        }
    }

    @Override
    public int getItemCount() {
        if(mHasPermissionAdd){
            return mFriends.size() + 3;
        }else{
            return mFriends.size() + 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int maxFriends = (mFriends.size() + 2);
        if(!mHasPermissionAdd){
            maxFriends--;
        }

        if(position == 0){
            return TYPE_TOTAL_FRIENDS;
        }else if(position == 1 && mHasPermissionAdd){
            return TYPE_ADD_FRIEND;
        }else if(position > 0 && position < maxFriends){
            return TYPE_FRIEND;
        }
        return TYPE_LEAVE_BUTTON;
    }

    @Override
    public void onAddFriendClick() {
        if(mListener != null){
            mListener.onAddFriendClick();
        }
    }

    @Override
    public void onLeaveGroup() {
        if(mListener != null){
            mListener.onLeaveGroup();
        }
    }

    /**
     * Funcion que setea los contactos
     * @param persons
     */
    public void setContacts(ArrayList<Friend> persons){
        mFriends = persons;
        // Actualizamos listado
        notifyDataSetChanged();
    }

    public void setOnListener(OnGroupSettingAdapterListener listener){
        mListener = listener;
    }

    @Override
    public void onRemoveUser(final Friend friend) {
        // Eliminar usuario del servidor
        GroupRest.OnRemoveComplete callback = new GroupRest.OnRemoveComplete() {
            @Override
            public void onSuccess(boolean success) {
                // Recorremos los amigos
                for (Object o : mFriends) {
                    Friend f = (Friend)o;
                    if(f.id == friend.id){
                        mFriends.remove(o);
                        break;
                    }
                }
                // Eliminar de la DB
                new GroupRealm().delete(friend);
                // Refrescar usuarios
                notifyDataSetChanged();
            }
        };
        if(friend.user_id == 0){
            new GroupRest(mActivity).removeUserById(mGroup.id, friend.id, callback);
        }else{
            new GroupRest(mActivity).removeUser(mGroup.id, friend.user_id, callback);
        }

    }

    public void disableAddInvitation(){ this.mHasPermissionAdd = false; }

    public interface OnGroupSettingAdapterListener{
        void onAddFriendClick();
        void onLeaveGroup();
    }
}

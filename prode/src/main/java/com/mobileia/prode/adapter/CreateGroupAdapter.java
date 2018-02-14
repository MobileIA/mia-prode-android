package com.mobileia.prode.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileia.contacts.entity.Person;
import com.mobileia.facebook.entity.Profile;
import com.mobileia.prode.R;
import com.mobileia.prode.view.holder.CreateGroupContactViewHolder;
import com.mobileia.prode.view.holder.CreateGroupTitleViewHolder;

import java.util.ArrayList;

/**
 * Created by matiascamiletti on 27/1/18.
 */

public class CreateGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_CONTACT = 2;

    protected Activity mActivity;
    /**
     * Almacena el listado de contactos
     */
    protected ArrayList mContactsItems = new ArrayList<>();
    /**
     * Almacena el titulo que se esta ingresando
     */
    protected String mTitle = "";
    /**
     * Almacena el ID del torneo seleccionado
     */
    protected int mTournamentId = 0;
    /**
     * Verifica si ya se realizo la comprobacion de los contactos
     */
    protected boolean mLoadingInvite = false;

    /**
     * constructor
     * @param activity
     */
    public CreateGroupAdapter(Activity activity){
        mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TITLE:
                return new CreateGroupTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_group_title, parent, false), this);
            case TYPE_CONTACT:
                return new CreateGroupContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_group_contact, parent, false), this);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_TITLE:
                onBindTitleViewHolder((CreateGroupTitleViewHolder)holder);
                break;
            case TYPE_CONTACT:
                onBindContactViewHolder((CreateGroupContactViewHolder)holder, position-1);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mContactsItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_TITLE;
        }
        return TYPE_CONTACT;
    }

    protected void onBindTitleViewHolder(CreateGroupTitleViewHolder holder){
        holder.editTitle.setText(mTitle);
    }

    protected void onBindContactViewHolder(CreateGroupContactViewHolder holder, int position){
        Object item = mContactsItems.get(position);
        if(item instanceof Person){
            holder.bindPerson((Person)item, mLoadingInvite);
        }else if(item instanceof Profile){
            holder.bindPerson((Profile)item, mLoadingInvite);
        }

    }

    /**
     * Funcion que guarda el titulo del grupo
     * @param title
     */
    public void setTitle(String title){
        mTitle = title;
    }

    /**
     * Devuelve el titulo ingresado
     * @return
     */
    public String getTitle(){ return mTitle; }

    /**
     * Funcion para guardar el ID del torneo
     * @param tournamentId
     */
    public void setTournament(int tournamentId){
        mTournamentId = tournamentId;
    }

    /**
     * Funcion que setea los contactos
     * @param persons
     */
    public void setContacts(ArrayList<Person> persons){
        mContactsItems = persons;
        // Actualizamos listado
        notifyDataSetChanged();
    }
    /**
     * Funcion que setea los contactos
     * @param persons
     */
    public void setContactsFacebook(ArrayList<Profile> persons){
        mContactsItems = persons;
        // Actualizamos listado
        notifyDataSetChanged();
    }

    /**
     * Devuelve el Activity
     * @return
     */
    public Activity getActivity(){
        return mActivity;
    }
}

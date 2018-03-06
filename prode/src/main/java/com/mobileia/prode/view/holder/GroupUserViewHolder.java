package com.mobileia.prode.view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mobileia.core.dialog.ConfirmDialog;
import com.mobileia.prode.R;
import com.mobileia.prode.entity.Friend;
import com.mobileia.prode.entity.Group;
import com.mobileia.recyclerview.holder.BaseViewHolder;

/**
 * Created by matiascamiletti on 6/3/18.
 */

public class GroupUserViewHolder extends BaseViewHolder<Friend> implements View.OnClickListener, MaterialDialog.ListCallback {

    /**
     * Instancia del elemento que muestra el nombre del contact
     */
    public final TextView title;
    /**
     * Instancia del elemento de subtitulo
     */
    public final TextView subtitle;
    /**
     * Instancia de la imagen del contacto
     */
    public final ImageView image;
    /**
     * Almacena el grupo que se estan mostrando la configuracion
     */
    protected Group mGroup;
    /**
     * Almacena el usuario que se esta mostrando
     */
    protected Friend mFriend;
    /**
     * Almacena el listener de las opciones
     */
    protected OnOptionsGroup mListener;

    /**
     * Constructor base
     *
     * @param itemView
     */
    public GroupUserViewHolder(View itemView, Group group, OnOptionsGroup listener) {
        super(itemView);
        mGroup = group;
        mListener = listener;
        itemView.setOnClickListener(this);
        // Buscamos elementos de la vista
        title = itemView.findViewById(R.id.text_name);
        subtitle = itemView.findViewById(R.id.text_data);
        image = itemView.findViewById(R.id.image);
    }

    @Override
    public void bind(Friend object) {
        mFriend = object;
        // Verificar si es un usuario registrado
        if (object.user_id == 0) {
            // Cargar nombre
            title.setText(object.username);
        } else {
            // Cargar nombre
            title.setText(object.firstname);
        }
        // Cargar telefono
        subtitle.setText(object.email);
        // Cargar imagen
        String photo = object.photo;
        if (photo != null && photo.length() > 0) {
            Glide.with(image).load(photo).apply(RequestOptions.circleCropTransform()).into(image);
        } else {
            Glide.with(image).load(R.drawable.avatar_three_gray).apply(RequestOptions.circleCropTransform()).into(image);
        }
    }

    @Override
    public void onClick(View view) {
        // Verificar si es el administrador
        if(mGroup.is_admin == 0 || mGroup.user_id == mFriend.user_id){
            return;
        }
        // Abrimos dialogo
        openDialog();
    }

    /**
     * Funcion que se ejecuta al seleccionar una opcion del modal
     * @param dialog
     * @param itemView
     * @param position
     * @param text
     */
    @Override
    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
        // Desea eliminar el usuario
        if(position == 0){
            new ConfirmDialog(itemView.getContext(), R.string.group_setting_dialog_confirm_title, R.string.group_setting_dialog_confirm_content).show(new ConfirmDialog.OnConfirmDialog() {
                @Override
                public void onPositive() {
                    if(mListener != null){
                        mListener.onRemoveUser(mFriend);
                    }
                }
            });
        }
    }

    /**
     * Abrir el modal de opciones
     */
    protected void openDialog(){
        new MaterialDialog.Builder(itemView.getContext())
                .title(R.string.group_setting_dialog_user_option_title)
                .items(R.array.group_setting_dialog_user_options)
                .itemsCallback(this)
                .show();
    }

    /**
     * Setea el listener
     * @param listener
     */
    public void setListener(OnOptionsGroup listener){ mListener = listener; }

    /**
     * Interfaz para comunicar las opciones
     */
    public interface OnOptionsGroup{
        void onRemoveUser(Friend friend);
    }
}
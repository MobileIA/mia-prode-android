package com.mobileia.prode.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileia.contacts.entity.Person;
import com.mobileia.facebook.entity.Profile;
import com.mobileia.prode.R;
import com.mobileia.prode.adapter.CreateGroupAdapter;

/**
 * Created by matiascamiletti on 27/1/18.
 */

public class CreateGroupContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageView;
    public TextView nameView;
    public TextView phoneView;
    public ImageButton inviteBtn;

    protected CreateGroupAdapter mAdapter;

    public Person person;
    public Profile profile;

    public CreateGroupContactViewHolder(View itemView, CreateGroupAdapter adapter) {
        super(itemView);
        // Obtenemos elementos
        imageView = itemView.findViewById(R.id.image);
        nameView = itemView.findViewById(R.id.text_name);
        phoneView = itemView.findViewById(R.id.text_data);
        inviteBtn = itemView.findViewById(R.id.btn_invite);
        inviteBtn.setOnClickListener(this);
        // Guardamos adapter
        mAdapter = adapter;
    }

    public void bindPerson(Person p, boolean verified){
        person = p;
        nameView.setText(p.fullname);
        phoneView.setText(p.phone);

        if(verified && p.has_account == 0){
            inviteBtn.setVisibility(View.VISIBLE);
        }else{
            inviteBtn.setVisibility(View.GONE);
        }
    }

    public void bindPerson(Profile p, boolean verified){
        profile = p;
        nameView.setText(p.fullname);
        phoneView.setText(p.id);
    }

    @Override
    public void onClick(View view) {
        // Enviar SMS
        //PhoneUtil.sendSMS(mAdapter.getActivity(), person.phone, mAdapter.getActivity().getResources().getString(R.string.create_group_text_sms));
    }
}

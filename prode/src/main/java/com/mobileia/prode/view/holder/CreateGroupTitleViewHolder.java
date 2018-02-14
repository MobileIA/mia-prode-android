package com.mobileia.prode.view.holder;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.mobileia.prode.R;
import com.mobileia.prode.adapter.CreateGroupAdapter;

/**
 * Created by matiascamiletti on 27/1/18.
 */

public class CreateGroupTitleViewHolder extends RecyclerView.ViewHolder implements TextWatcher {

    public EditText editTitle;

    protected CreateGroupAdapter mAdapter;

    public CreateGroupTitleViewHolder(View itemView, CreateGroupAdapter adapter) {
        super(itemView);
        // Obtenemos elementos
        editTitle = itemView.findViewById(R.id.edit_title);
        editTitle.addTextChangedListener(this);
        // Guardamos adapter
        mAdapter = adapter;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mAdapter.setTitle(editable.toString());
    }
}

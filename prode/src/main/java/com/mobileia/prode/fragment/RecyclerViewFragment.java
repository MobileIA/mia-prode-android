package com.mobileia.prode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileia.prode.R;
import com.mobileia.recyclerview.MobileiaRecyclerView;

/**
 * Created by matiascamiletti on 21/10/17.
 */

abstract public class RecyclerViewFragment extends Fragment {
    /**
     * constante que representa el ID del grupo a mostrar
     */
    public static final String ARGUMENT_GROUP_ID = "ARGUMENT_GROUP_ID";

    /**
     * Almacena la instancia del listado
     */
    protected MobileiaRecyclerView mRecyclerView;
    /**
     * Almacena el ID del grupo seleccionado
     */
    protected int mGroupId = -1;
    /**
     * Constructor vacio
     */
    public RecyclerViewFragment() {}

    /**
     * Funcion que se ejecuta al crear el fragment para buscar los paraemtros requeridos
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtenemos los argumentos del fragment
        if(getArguments() != null){
            mGroupId = getArguments().getInt(ARGUMENT_GROUP_ID);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Creamos vista
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        // Configurar listado
        onCreateRecyclerView(view);
        // Devolvemos vista
        return view;
    }

    /**
     * Configura toda la vista del listado
     * @param view
     */
    protected void onCreateRecyclerView(View view){
        // Obtener listado
        mRecyclerView = view.findViewById(R.id.list);
        // Configurar listado
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Asignar view de loading
        mRecyclerView.setLoadingView(R.layout.partial_loading);
    }
}

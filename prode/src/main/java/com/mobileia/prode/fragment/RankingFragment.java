package com.mobileia.prode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileia.prode.R;
import com.mobileia.prode.adapter.RankingAdapter;
import com.mobileia.prode.entity.Ranking;
import com.mobileia.prode.rest.RankingRest;
import com.mobileia.recyclerview.MobileiaRecyclerView;

import java.util.ArrayList;

/**
 * Created by matiascamiletti on 13/2/18.
 */

public class RankingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String ARGUMENT_GROUP_ID = "ARGUMENT_GROUP_ID";

    /**
     * Adaptador del listado
     */
    protected RankingAdapter mAdapter;
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
    public RankingFragment() {}

    /**
     * Constructor publico
     * @param groupId
     * @return
     */
    @SuppressWarnings("unused")
    public static RankingFragment newInstance(int groupId) {
        RankingFragment fragment = new RankingFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_GROUP_ID, groupId);
        fragment.setArguments(args);
        return fragment;
    }

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

    /**
     * Funcion que se ejecuta para crear la vista del fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Creamos vista
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        // Configurar listado
        onCreateRecyclerView(view);
        // Devolvemos vista
        return view;
    }

    /**
     * Funcion que se ejecuta cuando se quiere actualizar manualmente
     */
    @Override
    public void onRefresh() {
        // Cargar de nuevo el listado
        loadRanking();
    }

    /**
     * Consulta el servidor en busca de los datos
     */
    protected void loadRanking(){
        // Mostrar loading
        mRecyclerView.startLoading();
        // Llamar al servidor
        new RankingRest(getActivity()).fetchList(mGroupId, new RankingRest.OnFetchComplete() {
            @Override
            public void onSuccess(ArrayList<Ranking> list) {
                // Asignamos el listado
                mAdapter.setValues(list);
                // Ocultar refresh
                mRecyclerView.stopRefreshing();
                // Ocultar cargando
                mRecyclerView.stopLoading();
            }

            @Override
            public void onError() {
                // Ocultar cargando
                mRecyclerView.stopLoading();
                // Ocultar refresh
                mRecyclerView.stopRefreshing();
            }
        });
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
        // Crear adapter para el ranking
        mAdapter = new RankingAdapter();
        // Asignar adapter
        mRecyclerView.setAdapter(mAdapter);
        // Asignar view de loading
        //mRecyclerView.setLoadingView(R.layout.partial_loading);
        // Poder refrescar la vista
        mRecyclerView.setOnRefreshListener(this);
        // Cargar ranking
        loadRanking();
    }
}

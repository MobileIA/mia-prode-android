package com.mobileia.prode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
    public static final String ARGUMENT_VISIBLE_ADD_BUTTON = "ARGUMENT_VISIBLE_ADD_BUTTON";

    public static View.OnClickListener sAddButtonClick = null;

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
     * Alamcena si se muestra el boton para agregar
     */
    protected boolean mIsVisibleAddButton = false;
    /**
     * Almacena el boton para agregar participantes
     */
    protected FloatingActionButton mAddButton;

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
        args.putBoolean(ARGUMENT_VISIBLE_ADD_BUTTON, false);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *
     * @param groupId
     * @param visibleAddButton
     * @return
     */
    public static RankingFragment newInstance(int groupId, boolean visibleAddButton) {
        RankingFragment fragment = new RankingFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_GROUP_ID, groupId);
        args.putBoolean(ARGUMENT_VISIBLE_ADD_BUTTON, visibleAddButton);
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
            mIsVisibleAddButton = getArguments().getBoolean(ARGUMENT_VISIBLE_ADD_BUTTON);
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
        // Configurar boton
        onCreateAddButton(view);
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
     * Obtiene el boton para agregar
     * @return
     */
    public FloatingActionButton getAddButton(){ return mAddButton; }

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

    protected void onCreateAddButton(View view){
        // Obtener boton
        mAddButton = view.findViewById(R.id.add_button);
        // Configurar visibilidad
        if(mIsVisibleAddButton){
            mAddButton.setVisibility(View.VISIBLE);
        }else{
            mAddButton.setVisibility(View.GONE);
        }
        // Configurar click
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sAddButtonClick != null){
                    sAddButtonClick.onClick(view);
                }
            }
        });
    }
}

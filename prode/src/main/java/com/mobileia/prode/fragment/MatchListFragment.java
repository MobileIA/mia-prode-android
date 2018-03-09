package com.mobileia.prode.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mobileia.prode.adapter.MatchAdapter;
import com.mobileia.prode.entity.Match;
import com.mobileia.prode.firebase.ProdeRegisterFirebase;
import com.mobileia.prode.helper.ProdeLocalBroadcastHelper;
import com.mobileia.prode.rest.MatchRest;
import com.mobileia.recyclerview.scroll.EndScrollListener;
import com.mobileia.recyclerview.scroll.StartScrollListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by matiascamiletti on 31/8/17.
 */

public class MatchListFragment extends RecyclerViewFragment implements OnRefreshMatchFragment {

    /**
     * Adaptador del listado
     */
    protected MatchAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchListFragment() {}

    /**
     * Constructor publico
     * @param groupId
     * @return
     */
    @SuppressWarnings("unused")
    public static MatchListFragment newInstance(int groupId) {
        MatchListFragment fragment = new MatchListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_GROUP_ID, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreateRecyclerView(View view) {
        // Configuramos listado
        super.onCreateRecyclerView(view);
        // Crear adapter para el ranking
        mAdapter = new MatchAdapter((MatchAdapter.OnClickMatchAdapter)getActivity());
        // Asignar adapter
        mRecyclerView.setAdapter(mAdapter);
        // Funcionalidad para cargar mas partidos
        mRecyclerView.setOnEndScrollListener(mEndScrollListener);
        // Funcionalidad para cargar los partidos anteriores
        mRecyclerView.setOnStartScrollListener(mStartScrollListener);
        // Cargar partidos
        loadMatches();
        // Iniciar servicio para recibir informacion en vivo
        ProdeRegisterFirebase.registerLive();
        ProdeLocalBroadcastHelper.startUpdateMatch(getActivity(), mLiveBroadcast);
    }

    /**
     * Funcion para refrescar un partido que se asigno una prediccion
     * @param match
     */
    @Override
    public void refreshMatch(Match match) {
        // Obtenemos los partidos
        ArrayList<Match> matches = mAdapter.getValues();
        // Recorremos los partidos
        for(Match m : matches){
            if(m.id == match.id){
                m.predicted_one = match.predicted_one;
                m.predicted_two = match.predicted_two;
                mAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancelar servicio para recibir en vivo
        ProdeLocalBroadcastHelper.stop(getActivity(), mLiveBroadcast);
        ProdeRegisterFirebase.unregisterLive();
    }

    /**
     * Funcion que se ejecuta al recibir nuevos datos de partidos en vivo
     */
    protected BroadcastReceiver mLiveBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Obtenemos el partido a actualizar
            Match match = intent.getParcelableExtra("match");
            // Actualizamos el partido con los nuevos datos
            mAdapter.updateMatch(match);
        }
    };

    /**
     * Funcionalidad para que se carguen los partidos anteriores
     */
    protected StartScrollListener mStartScrollListener = new StartScrollListener() {
        @Override
        public void onScrolledToStart() {
            // Obtenemos el primer partido
            Match match = mAdapter.getFirstMatch();
            // Verificar si existe
            if(match == null){
                return;
            }
            // No permitir que busque hasta que se complete la llamada
            stopLoad();
            // Llamar al server en busca mas partidos
            new MatchRest(getActivity()).fetchListPrevious(mGroupId, match.id, mAdapter.getIdsFirsts(), new MatchRest.OnFetchComplete() {
                @Override
                public void onSuccess(ArrayList<Match> list) {
                    // Verificar si hay mas partidos
                    if(list.size() == 0){
                        // Cancelamos para que siga buscando
                        mRecyclerView.stopStartScroll();
                    }
                    // Cargamos los nuevos partidos al listado
                    mAdapter.addPreviousMatches(list);
                    // Volver a activar para que busque
                    startLoad();
                }

                @Override
                public void onError() {
                    // Cancelamos para que siga buscando
                    mRecyclerView.stopStartScroll();
                    // Volver a activar para que busque
                    startLoad();
                }
            });
        }
    };
    /**
     * Funcion que se ejecuta al llegar al final de la lista
     */
    protected EndScrollListener mEndScrollListener = new EndScrollListener() {
        @Override
        public void onScrolledToEnd() {
            // Obtenemos ultimo partido
            Match match = mAdapter.getLastMatch();
            // Verificar si existe
            if(match == null){
                return;
            }
            // No permitir que busque hasta que se complete la llamada
            stopLoad();
            // Llamar al server en busca mas partidos
            new MatchRest(getActivity()).fetchListNext(mGroupId, match.id, mAdapter.getIdsLasts(), new MatchRest.OnFetchComplete() {
                @Override
                public void onSuccess(ArrayList<Match> list) {
                    // Verificar si hay mas partidos
                    if(list.size() == 0){
                        // Cancelamos para que siga buscando
                        mRecyclerView.stopEndScroll();
                    }
                    // Cargamos los nuevos partidos al listado
                    mAdapter.addNextMatches(list);
                    // Volver a activar para que busque
                    startLoad();
                }

                @Override
                public void onError() {
                    // Cancelamos para que siga buscando
                    mRecyclerView.stopEndScroll();
                    // Volver a activar para que busque
                    startLoad();
                }
            });
        }
    };

    /**
     * FUncion que se conecta al servidor en busca de los datos
     */
    protected void loadMatches(){
        // Mostrar loading
        mRecyclerView.startLoading();
        // Llamar al servidor
        new MatchRest(getActivity()).fetchList(mGroupId, new MatchRest.OnFetchComplete() {
            @Override
            public void onSuccess(ArrayList<Match> list) {
                // Asignamos el listado
                mAdapter.setValues(list);
                // Ocultar cargando
                mRecyclerView.stopLoading();
                // Mostrar el partido de la fecha actual
                searchDate();
            }

            @Override
            public void onError() {
                // Ocultar cargando
                mRecyclerView.stopLoading();
            }
        });
    }

    /**
     * Setea el scroll del listado en el partido del dia de hoy
     */
    protected void searchDate(){
        // Vasriable del dia de hoy
        Date now = new Date();
        // recorremos los partidos
        for(int i = 0; i < mAdapter.getItemCount(); i++){
            // Obtenemos el partido
            Match match = mAdapter.get(i);
            // Verificar si es del dia de hoy o posterior
            if(match.day.getTime() >= now.getTime()){
                mRecyclerView.scrollToPosition(i);
                break;
            }
        }
    }
}

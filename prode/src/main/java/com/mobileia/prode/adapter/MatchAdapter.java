package com.mobileia.prode.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mobileia.prode.R;
import com.mobileia.prode.entity.Match;
import com.mobileia.prode.view.holder.MatchViewHolder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by matiascamiletti on 31/8/17.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchViewHolder> {
    /**
     * Listado de partidos
     */
    protected ArrayList<Match> mValues = new ArrayList<>();

    protected OnClickMatchAdapter mOnClickListener;

    public MatchAdapter(){}

    public MatchAdapter(OnClickMatchAdapter listener){
        mOnClickListener = listener;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MatchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false), mOnClickListener);
    }

    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        holder.bind(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setValues(ArrayList<Match> list){
        mValues = list;
        notifyDataSetChanged();
    }

    public ArrayList<Match> getValues(){ return mValues; }

    public Match get(int position){
        return mValues.get(position);
    }

    /**
     * Funcion que se encarga de actualizar los datos del partido ingresado
     * @param newMatch
     */
    public void updateMatch(Match newMatch){
        // Recorremos los partidos
        for(Match m : mValues){
            if(m.id == newMatch.id){
                m.status = newMatch.status;
                m.result_one = newMatch.result_one;
                m.result_two = newMatch.result_two;
                m.penalty_one = newMatch.penalty_one;
                m.penalty_two = newMatch.penalty_two;
                notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * Obtiene el primer partido del listado
     * @return
     */
    public Match getFirstMatch(){
        if(mValues.size() > 0){
            return mValues.get(0);
        }
        return null;
    }
    /**
     * Obtiene el ultimo partido de la lista
     * @return
     */
    public Match getLastMatch(){
        if(mValues.size() > 0){
            return mValues.get(mValues.size()-1);
        }
        return null;
    }

    public String getIdsFirsts(){
        ArrayList<String> ids = new ArrayList<>();
        int count = 0;
        for(int i = 0; i < mValues.size(); i++){
            ids.add(mValues.get(i).id + "");
            count++;
            if(count >= 4){
                break;
            }
        }
        return TextUtils.join(",", ids);
    }

    /**
     * Devuelve los IDs de los ultimos partidos cargados para excluir
     * @return
     */
    public String getIdsLasts(){
        ArrayList<String> ids = new ArrayList<>();
        int count = 0;
        for(int i = mValues.size()-1; i > 0; i--){
            ids.add(mValues.get(i).id + "");
            count++;
            if(count >= 4){
                break;
            }
        }
        return TextUtils.join(",", ids);
    }

    /**
     * Funcion para cargar mas partidos al listado
     * @param list
     */
    public void addNextMatches(ArrayList<Match> list){
        mValues.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * Funcion para cargar partidos al inicio de la lista
     * @param list
     */
    public void addPreviousMatches(ArrayList<Match> list){
        for(Match m : list){
            mValues.add(0, m);
        }
        notifyItemRangeInserted(0, list.size());
    }

    /**
     * Interface para agregar funcionalidad al click del partido
     */
    public interface OnClickMatchAdapter{
        void onClick(Match match);
    }
}

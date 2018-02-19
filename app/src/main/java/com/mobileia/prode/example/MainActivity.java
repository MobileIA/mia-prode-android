package com.mobileia.prode.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.mobileia.core.Mobileia;
import com.mobileia.prode.MobileiaProde;
import com.mobileia.prode.entity.Group;
import com.mobileia.prode.example.R;
import com.mobileia.prode.realm.GroupRealm;
import com.mobileia.prode.rest.GroupRest;
import com.mobileia.prode.view.builder.RankingPodiumViewControllerBuilder;
import com.mobileia.prode.view.controller.RankingPodiumViewController;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    protected RankingPodiumViewController mRankingPodium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar Mobileia Lab
        Mobileia.getInstance().setAppId(15);
        // Configuración del Prode
        MobileiaProde.init("http://cooperacionprode.mobileia.com/");
        // Iniciar realm
        Realm.init(this);
    }

    public void onClickGroupSaved(View v){
        RealmResults<Group> groups = new GroupRealm().fetchAll();
        for(Group g : groups){
            System.out.println("Titulo grupo realm: " + g.toString());
        }
    }

    public void onClickSyncgroup(View v){
        new GroupRest(this).syncGroups(new GroupRest.OnSyncComplete() {
            @Override
            public void onSuccess(ArrayList<Group> list) {
                for(Group g : list){
                    System.out.println("Titulo grupo: " + g.toString());
                }
            }
        });
    }

    public void onClickRanking(View view){
        mRankingPodium = (RankingPodiumViewController)new RankingPodiumViewControllerBuilder()
                .withContainer((ViewGroup) findViewById(R.id.container_scroll))
                .build();
        // Cargar el ranking de un grupo
        mRankingPodium.loadByGroup(1);
    }
}

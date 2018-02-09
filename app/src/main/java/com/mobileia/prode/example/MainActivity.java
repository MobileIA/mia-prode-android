package com.mobileia.prode.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.mobileia.prode.example.R;
import com.mobileia.prode.view.builder.RankingPodiumViewControllerBuilder;
import com.mobileia.prode.view.controller.RankingPodiumViewController;

public class MainActivity extends AppCompatActivity {

    protected RankingPodiumViewController mRankingPodium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRankingPodium = (RankingPodiumViewController)new RankingPodiumViewControllerBuilder()
                .withContainer((ViewGroup) findViewById(R.id.container_scroll))
                .build();
        // Cargar el ranking de un grupo
        mRankingPodium.loadByGroup(1);
    }
}

package com.mobileia.prode.view.builder;

import com.mobileia.prode.R;
import com.mobileia.prode.view.controller.RankingPodiumViewController;
import com.mobileia.recyclerview.controller.ViewControllerBuilder;

/**
 * Created by matiascamiletti on 9/2/18.
 */

public class RankingPodiumViewControllerBuilder extends ViewControllerBuilder {

    public RankingPodiumViewControllerBuilder(){
        mLayoutView = R.layout.item_ranking_podium;
        mViewController = RankingPodiumViewController.class;
    }
}

package com.mobileia.prode.view.builder;

import com.mobileia.prode.R;
import com.mobileia.prode.view.controller.MatchViewController;
import com.mobileia.recyclerview.controller.ViewControllerBuilder;

/**
 * Created by matiascamiletti on 20/2/18.
 */

public class MatchViewControllerBuilder extends ViewControllerBuilder {

    public MatchViewControllerBuilder(){
        mLayoutView = R.layout.item_match;
        mViewController = MatchViewController.class;
    }
}

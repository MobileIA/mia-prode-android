package com.mobileia.prode.view.builder;

import com.mobileia.prode.R;
import com.mobileia.prode.adapter.MatchAdapter;
import com.mobileia.prode.view.controller.MatchViewController;
import com.mobileia.recyclerview.controller.BaseViewController;
import com.mobileia.recyclerview.controller.ViewControllerBuilder;

/**
 * Created by matiascamiletti on 20/2/18.
 */

public class MatchViewControllerBuilder extends ViewControllerBuilder {

    protected MatchAdapter.OnClickMatchAdapter mListener;

    public MatchViewControllerBuilder(){
        mLayoutView = R.layout.item_match;
        mViewController = MatchViewController.class;
    }

    public MatchViewControllerBuilder withListener(MatchAdapter.OnClickMatchAdapter listener){
        mListener = listener;
        return this;
    }

    @Override
    public BaseViewController build() {
        MatchViewController controller = (MatchViewController)super.build();
        controller.setOnClickMatchListener(mListener);
        return controller;
    }
}

package com.mobileia.prode.realm;

import com.mobileia.prode.entity.Ranking;

import io.realm.annotations.RealmModule;

/**
 * Created by matiascamiletti on 22/11/17.
 */
@RealmModule(library = false, allClasses = false, classes = { Ranking.class })
public class ProdeModule {
}

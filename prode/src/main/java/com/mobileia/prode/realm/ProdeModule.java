package com.mobileia.prode.realm;

import com.mobileia.prode.entity.Friend;
import com.mobileia.prode.entity.Group;
import com.mobileia.prode.entity.Ranking;

import io.realm.annotations.RealmModule;

/**
 * Created by matiascamiletti on 22/11/17.
 */
@RealmModule(library = true, allClasses = false, classes = { Ranking.class, Friend.class, Group.class, })
public class ProdeModule {
}

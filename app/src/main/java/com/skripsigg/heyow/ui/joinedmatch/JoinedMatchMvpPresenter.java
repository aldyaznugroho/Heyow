package com.skripsigg.heyow.ui.joinedmatch;

import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 5/16/2017.
 */

public interface JoinedMatchMvpPresenter<V extends JoinedMatchMvpView> extends MvpPresenter<V> {
    void loadUserJoinedMatchApiService();
}

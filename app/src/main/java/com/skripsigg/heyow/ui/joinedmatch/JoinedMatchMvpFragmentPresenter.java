package com.skripsigg.heyow.ui.joinedmatch;

import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 5/22/2017.
 */

public interface JoinedMatchMvpFragmentPresenter<V extends JoinedMatchMvpFragmentView>
        extends MvpPresenter<V> {
    void loadUserJoinedMatchApiService();
}
